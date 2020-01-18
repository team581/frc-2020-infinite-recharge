/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.util.limelight;

import club.team581.Constants.LIMELIGHT;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * @see https://docs.limelightvision.io/en/latest/networktables_api.html
 */
public final class Limelight {
  /**
   * Ports on the RoboRIO and on the computer.
   */
  public final static class NetworkTables {
    private final static NetworkTable ntTable = NetworkTableInstance.getDefault()
        .getTable(LIMELIGHT.NETWORK_TABLES_TABLE);

    // public final static boolean targetsExist =
    // ntTable.getEntry("tv").getDouble(0) == 1;
    public final static boolean targetsExist() {
      return ntTable.getEntry("tv").getDouble(0) == 1;
    };

    /** `-29.8` to `29.8` degrees. */
    public final static double horizontalOffset() {
      return ntTable.getEntry("tv").getDouble(0);
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
      return ntTable.getEntry("tl").getDouble(0);
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
      return ntTable.getEntry("tshort").getDouble(-1);
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

    public final static class Constants {
      /**
       * Sets limelight’s LED state.
       */
      public static enum LEDMode {
        /** Use the LED Mode set in the current pipeline */
        USE_CURRENT_PIPELINE,
        /** Force off */
        OFF,
        /** Force blink */
        BLINK,
        /** Force on */
        ON
      }

      /**
       * Sets limelight’s operation mode.
       */
      public static enum CameraMode {
        VISION_PROCESSOR,
        /** Driver Camera (Increases exposure, disables vision processing) */
        RAW
      }

      /**
       * Sets limelight’s streaming mode.
       */
      public static enum Stream {
        /** Standard - Side-by-side streams if a webcam is attached to Limelight */
        STANDARD,
        /**
         * PiP Main - The secondary camera stream is placed in the lower-right corner of
         * the primary camera stream
         */
        PICTURE_IN_PICTURE_MAIN,
        /**
         * PiP Secondary - The primary camera stream is placed in the lower-right corner
         * of the secondary camera stream
         */
        PICTURE_IN_PICTURE_SECONDARY
      }

      /**
       * Allows users to take snapshots during a match.
       */
      public static enum Snapshot {
        /** Stop taking snapshots. */
        STOP,
        /** Take two snapshots per second. */
        TWO_PER_SECOND
      }
    }
  }
}
