/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.util.auto;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import club.team581.Constants;
import club.team581.Robot;
import club.team581.RobotContainer;
import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Add your docs here.
 */
public class Autonomous {
    static TalonFX[] allMotors = RobotContainer.driveSubsystem.allMotors;
    static double initalAverageEncoderValue = 0;
    static double currentEncoderValue = 0;
    static double encoderDistance = 0;
    static double distanceToDrive = 0;
    static double drivingAdjust = 0;
    static double marginOfError = 0;
    static double divisor = 0;
    public final static PIDController driveController = new PIDController(0, 0, 0);

    public static final double getDriveCommand(final double initalEncoderValue, final double distanceToDrive) {
        /** Gets the current average encoder value of the motors */
        for (int i = 0; i < allMotors.length; i++) {
            currentEncoderValue = currentEncoderValue + allMotors[i].getSelectedSensorPosition();
        }
        currentEncoderValue = currentEncoderValue / allMotors.length;

        /** Finds the distance traveled after starting the command */
        encoderDistance = (currentEncoderValue - initalEncoderValue)
                / Constants.Autonomous.Movement.INCHES_PER_ROTATION;

        /** Calculates the output of the PID controller */
        drivingAdjust = driveController.calculate(encoderDistance, distanceToDrive) / divisor;

        return drivingAdjust;
    }

    public static final boolean isFinished() {
        /** Checks if the distance to the desired encoder value is within a margin of error */
        return distanceToDrive - marginOfError < encoderDistance && encoderDistance < distanceToDrive + marginOfError;

    }
}
