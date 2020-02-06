/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import club.team581.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  public final WPI_VictorSPX armMotor1 = new WPI_VictorSPX(Constants.Ports.Motors.ARM_MOTOR1);
  public final WPI_VictorSPX armMotor2 = new WPI_VictorSPX(Constants.Ports.Motors.ARM_MOTOR2);

  public final WPI_TalonSRX winchMotor = new WPI_TalonSRX(Constants.Ports.Motors.WINCH_MOTOR1);

  public ArmSubsystem() {
    armMotor2.follow(armMotor1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
