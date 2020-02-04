/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.util.limelight;

import club.team581.Constants;
import club.team581.Constants.Limelight.Measurements;
import club.team581.util.limelight.Limelight.NetworkTables.LimelightConstants.CameraMode;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * @see https://docs.limelightvision.io
 */
public final class Limelight {
  public final static double distanceToTarget(final double limelightAngleOfElevation, final VisionTarget visionTarget) {
    if (!NetworkTables.targetsExist()) {
      return -1;
    }

    return (visionTarget.height - Measurements.LIMELIGHT_HEIGHT_FROM_FLOOR)
        / Math.tan((limelightAngleOfElevation + NetworkTables.verticalOffset()) * (Math.PI / 180));
  }

  public final static LimelightMotion getDriveCommand() {
    final double KpAim = -0.1;
    final double KpDistance = -0.1;
    final double min_aim_command = 0.05;

    final double headingError = -NetworkTables.horizontalOffset();
    final double distanceError = -NetworkTables.verticalOffset();
    double steeringAdjust = 0;

    if (NetworkTables.horizontalOffset() > 1.0) {
      steeringAdjust = KpAim * headingError - min_aim_command;
    } else if (NetworkTables.horizontalOffset() < 1.0) {
      steeringAdjust = KpAim * headingError - min_aim_command;
    }

    final double distanceAdjust = KpDistance * distanceError;
    return new LimelightMotion(distanceAdjust, steeringAdjust);
  }

  public final static class LimelightMotion {
    public final double yAxisTranslation;
    public final double zAxisRotation;

    public LimelightMotion(final double yAxisTranslation, final double zAxisRotation) {
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
    }
  }
}
