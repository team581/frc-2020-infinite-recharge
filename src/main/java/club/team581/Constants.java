/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581;

import club.team581.util.limelight.VisionTarget;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  /**
   * Ports on the RoboRIO and on the computer.
   */
  public final static class PORTS {
    final static public Port COLOR_SENSOR = Port.kOnboard;

    final static public int CONTROLLER = 0;

    public final static class MOTORS {
      final static public int FRONT_RIGHT = 3;
      final static public int FRONT_LEFT = 1;
      final static public int REAR_RIGHT = 4;
      final static public int REAR_LEFT = 2;
    }
  }

  public final static class FIELD {
    /** This is in feet */
    // Sourced from page 30 of the 2020 FRC manual
    final static public double POWER_PORT_HEIGHT = 8.1875;
  }

  public final static class LIMELIGHT {
    final static public String NETWORK_TABLES_TABLE = "limelight";

    public final static class MEASUREMENTS {
      final static public double LIMELIGHT_HEIGHT_FROM_FLOOR = 9;
      final static public double LIMELIGHT_ANGLE_OF_ELEVATION = 0;
    }

    public final static class MOVEMENT {
      /** Strength of turning. */
      final static public double STEER_SPEED = 0.05;
      /** Strength of driving forwards and backwards. */
      final static public double DRIVE_SPEED = 0.25;
      /** Speed limit so we don't drive too fast. */
      final static public double MAX_DRIVE_SPEED = 0.35;
    }

    public final static class TARGETS {
      final static public VisionTarget LoadingBay = new VisionTarget(24, 11, 0);
      final static public VisionTarget PowerPort = new VisionTarget(60, 81.25, 1);
    }
  }
}
