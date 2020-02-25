/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581;

import club.team581.commands.auto.SeekVisionTarget.Constants.StartPosition;
import club.team581.util.DoubleSolenoidChannels;
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
  public final static class Ports {
    final static public Port COLOR_SENSOR = Port.kOnboard;
    final static public DoubleSolenoidChannels DUMPER_SOLENOID = new DoubleSolenoidChannels(55, 56);

    final static public int CONTROLLER = 0;

    public final static class Motors {
      final static public int FRONT_RIGHT =  11;
      final static public int FRONT_LEFT = 10;
      final static public int REAR_RIGHT = 13;
      final static public int REAR_LEFT = 12;

      final static public int CONTROL_PANEL = 36;

      final static public int ARM_MOTOR1 = 0;
      final static public int ARM_ENCODER1 = 0;
      final static public int ARM_MOTOR2 = 0;
      final static public int ARM_ENCODER2 = 0;

      final static public int WINCH_MOTOR1 = 0;
      final static public int WINCH_MOTOR2 = 1;

      public final static class Snarfer {
        /** Motor port for moving the wheels that touch the power cells. */
        final static public int INTAKE = 20;
      }
    }
  }

  public final static class Field {
    /** This is in feet */
    // Sourced from page 30 of the 2020 FRC manual
    final static public double POWER_PORT_HEIGHT = 8.1875;
  }

  public final static class Autonomous {
    final static public String NETWORK_TABLES_TABLE = "limelight";
    final static public StartPosition START_POSITION = StartPosition.LEFT;

    public final static class Measurements {
      final static public double LIMELIGHT_HEIGHT_FROM_FLOOR = 5.5;
      final static public double LIMELIGHT_ANGLE_OF_ELEVATION = 0;
    }

    public final static class Movement {
      /** Strength of turning. */
      final static public double STEER_SPEED = 0.05;
      /** Strength of driving forwards and backwards. */
      final static public double DRIVE_SPEED = 0.25;
      /** Speed limit so we don't drive too fast. */
      final static public double MAX_DRIVE_SPEED = 0.35;

      final static public double INCHES_PER_ROTATION = 1;
    }

    public final static class Targets {
      final static public VisionTarget LoadingBay = new VisionTarget(15, 11, 0);
      final static public VisionTarget PowerPort = new VisionTarget(60, 81.25, 1);
    }
  }
}
