/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import club.team581.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class ArmSubsystem extends SubsystemBase {
  public static final WPI_VictorSPX armMotor1 = new WPI_VictorSPX(Constants.PORTS.MOTORS.ARM_MOTOR1);
  public static final WPI_VictorSPX armMotor2 = new WPI_VictorSPX(Constants.PORTS.MOTORS.ARM_MOTOR2);

  public static final WPI_VictorSPX winchMotor = new WPI_VictorSPX(Constants.PORTS.MOTORS.WINCH_MOTOR1);


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

