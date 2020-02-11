/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.util.limelight;

import java.util.Arrays;

import club.team581.Constants;
import club.team581.Constants.Limelight.Measurements;
import club.team581.util.limelight.Limelight.NetworkTables.LimelightConstants.CameraMode;
import club.team581.util.limelight.Limelight.NetworkTables.LimelightConstants.CornerCoords;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * @see https://docs.limelightvision.io
 */
public final class Limelight {
  // new PIDController(Kp, Ki, Kd)
  // TODO: Move these constants to a dedicated subclass
  public final static PIDController strafeController = new PIDController(0.03, 0, 0.85);
  // \/ Changing Kd here seems to do *nothing* \/
  public final static PIDController distanceController = new PIDController(0.03, 0, 999999999);
  public final static PIDController rotationController = new PIDController(0.175, 0, 10000);

  public final static double distanceToTarget(final double limelightAngleOfElevation, final VisionTarget visionTarget) {
    if (!NetworkTables.targetsExist()) {
      return -1;
    }

    return (visionTarget.height - Measurements.LIMELIGHT_HEIGHT_FROM_FLOOR)
        / Math.tan((limelightAngleOfElevation + NetworkTables.verticalOffset()) * (Math.PI / 180));
  }

  public final static LimelightMotion getDriveCommand(final double limelightAngle, final VisionTarget visionTarget) {
    if (!NetworkTables.targetsExist()) {
      // Move nowhere if there isn't a target
      return new LimelightMotion(0, 0, 0);
    }

    // #region strafing
    final double horizontalOffset = NetworkTables.horizontalOffset();
    // TODO: Move the desired alignment here to a dedicated constants subclass
    final double strafingAdjust = MathUtil.clamp(strafeController.calculate(horizontalOffset, 0), -1, 1);
    // #endregion

    // #region distance
    final double distanceToTarget = distanceToTarget(limelightAngle, visionTarget);
    // TODO: Move the desired distance here to a dedicated constants subclass
    final double distanceAdjust = MathUtil.clamp(distanceController.calculate(distanceToTarget, 15), -1, 1);
    // #endregion

    // #region rotation
    // This gets the mean of the height differences, since just using one set can be inaccurate
    final double sideHeightDifference = ((NetworkTables.targetCoords(CornerCoords.BOTTOM_RIGHT_Y)
        - NetworkTables.targetCoords(CornerCoords.BOTTOM_LEFT_Y))
        + (NetworkTables.targetCoords(CornerCoords.TOP_RIGHT_Y) - NetworkTables.targetCoords(CornerCoords.TOP_LEFT_Y)))
        / 2;
    // TODO: Move the desired rotation here to a dedicated constants subclass
    final double rotationAdjust = MathUtil.clamp(rotationController.calculate(sideHeightDifference, 0), -1, 1);
    // #endregion

    System.out.println("strafe: " + strafingAdjust + " distance: " + distanceAdjust + " rotate: " + rotationAdjust);
    return new LimelightMotion(strafingAdjust, distanceAdjust, rotationAdjust);
  }

  public final static class LimelightMotion {
    public final double xAxisTranslation;
    public final double yAxisTranslation;
    public final double zAxisRotation;

    public LimelightMotion(final double xAxisTranslation, final double yAxisTranslation, final double zAxisRotation) {
      this.xAxisTranslation = xAxisTranslation;
      this.yAxisTranslation = yAxisTranslation;
      this.zAxisRotation = zAxisRotation;
    }
  }

  /**
   * @see https://docs.limelightvision.io/en/latest/networktables_api.html
   */
  public final static class NetworkTables {
    private final static NetworkTable ntTable = NetworkTableInstance.getDefault()
        .getTable(Constants.Limelight.NETWORK_TABLES_TABLE);

    // public final static boolean targetsExist =
    // ntTable.getEntry("tv").getDouble(0) == 1;
    public final static boolean targetsExist() {
      return ntTable.getEntry("tv").getDouble(0) == 1;
    };

    /** `-29.8` to `29.8` degrees. */
    public final static double horizontalOffset() {
      return ntTable.getEntry("tx").getDouble(0);
    };

    /** `-24.85` to `24.85` degrees. */
    public final static double verticalOffset() {
      return ntTable.getEntry("ty").getDouble(0);
    };

    /** 0% of image to 100% of image. */
    public final static double targetArea() {
      return ntTable.getEntry("ta").getDouble(0);
    };

    /** Skew or rotation (-90 degrees to 0 degrees) */
    public final static double skew() {
      return ntTable.getEntry("ts").getDouble(0);
    };

    public final static double targetCoords(final CornerCoords cornerValue) {
      final double[] resetValues = { 0, 0, 0, 0, 0, 0, 0, 0 };
      final double[] rawCoordValues = ntTable.getEntry("tcornxy").getDoubleArray(resetValues);
      // Pad the array with zeroes if it is missing values
      // Note from Jonah: I have observed this array sometimes having 6 values in it,
      // not sure why. If you don't pad the array you can get an out-of-bounds
      // exception and crash the whole ass program
      final double[] coordValues = Arrays.copyOf(rawCoordValues, resetValues.length);

      return coordValues[cornerValue.value];
    };

    /**
     * The pipeline’s latency contribution (ms) Add at least 11ms for image capture
     * latency.
     */
    public final static double latency() {
      return ntTable.getEntry("tl").getDouble(-1);
    };

    /** Sidelength of shortest side of the fitted bounding box (pixels) */
    public final static double shortestSideLength() {
      return ntTable.getEntry("tshort").getDouble(-1);
    };

    /** Sidelength of longest side of the fitted bounding box (pixels) */
    public final static double longestSideLength() {
      return ntTable.getEntry("tlong").getDouble(-1);
    };

    /** Horizontal sidelength of the rough bounding box (0 - 320 pixels) */
    public final static double horizontalSideLength() {
      return ntTable.getEntry("thor").getDouble(-1);
    };

    /** Vertical sidelength of the rough bounding box (0 - 320 pixels) */
    public final static double verticalSideLength() {
      return ntTable.getEntry("tvert").getDouble(-1);
    };

    /** True active pipeline index of the camera (0 .. 9) */
    public final static double currentPipelineIndex() {
      return ntTable.getEntry("getpipe").getDouble(0);
    };

    public final static boolean setPipelineIndex(final int index) {
      return ntTable.getEntry("pipeline").setNumber(index);
    }

    public final static CameraMode imageProcessingMode() {
      final int currentCameraModeEnum = ntTable.getEntry("camMode").getNumber(0).intValue();

      if (currentCameraModeEnum == CameraMode.RAW.value) {
        return CameraMode.RAW;
      } else {
        return CameraMode.VISION_PROCESSOR;
      }
    }

    public final static boolean setImageProcessingMode(final CameraMode mode) {
      final NetworkTableEntry camMode = ntTable.getEntry("camMode");

      return camMode.setNumber(mode.value);
    }

    public final static class LimelightConstants {
      /**
       * Sets limelight’s LED state.
       */
      public static enum LEDMode {
        /** Use the LED Mode set in the current pipeline */
        USE_CURRENT_PIPELINE(0),
        /** Force off */
        OFF(1),
        /** Force blink */
        BLINK(2),
        /** Force on */
        ON(3);

        public final int value;

        LEDMode(final int value) {
          this.value = value;
        }
      }

      /**
       * Sets limelight’s operation mode.
       */
      public static enum CameraMode {
        VISION_PROCESSOR(0),
        /** Driver Camera (Increases exposure, disables vision processing) */
        RAW(1);

        public final int value;

        CameraMode(final int value) {
          this.value = value;
        }
      }

      /**
       * Sets limelight’s streaming mode.
       */
      public static enum Stream {
        /** Standard - Side-by-side streams if a webcam is attached to Limelight */
        STANDARD(0),
        /**
         * PiP Main - The secondary camera stream is placed in the lower-right corner of
         * the primary camera stream
         */
        PICTURE_IN_PICTURE_MAIN(1),
        /**
         * PiP Secondary - The primary camera stream is placed in the lower-right corner
         * of the secondary camera stream
         */
        PICTURE_IN_PICTURE_SECONDARY(2);

        public final int value;

        Stream(final int value) {
          this.value = value;
        }
      }

      /**
       * Allows users to take snapshots during a match.
       */
      public static enum Snapshot {
        /** Stop taking snapshots. */
        STOP(0),
        /** Take two snapshots per second. */
        TWO_PER_SECOND(1);

        public final int value;

        Snapshot(final int value) {
          this.value = value;
        }
      }

      public static enum CornerCoords {
        /** Top left corner of the vision target */
        TOP_LEFT_X(0),
        /** Top left corner of the vision target */
        TOP_LEFT_Y(1),
        /** Top right corner of the vision target */
        TOP_RIGHT_X(2),
        /** Top right corner of the vision target */
        TOP_RIGHT_Y(3),
        /** Bottom right corner of the vision target */
        BOTTOM_RIGHT_X(4),
        /** Bottom right corner of the vision target */
        BOTTOM_RIGHT_Y(5),
        /** Bottom left corner of the vision target */
        BOTTOM_LEFT_X(6),
        /** Bottom left corner of the vision target */
        BOTTOM_LEFT_Y(7);

        public final int value;

        CornerCoords(final int value) {
          this.value = value;
        }
      }
    }
  }
}
