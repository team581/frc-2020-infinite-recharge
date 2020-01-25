/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import club.team581.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  public final WPI_VictorSPX armMotor1 = new WPI_VictorSPX(Constants.PORTS.MOTORS.ARM_MOTOR1);
  public final WPI_VictorSPX armMotor2 = new WPI_VictorSPX(Constants.PORTS.MOTORS.ARM_MOTOR2);

  public final VictorSPX winchMotor1 = new VictorSPX(Constants.PORTS.MOTORS.WINCH_MOTOR1);
  public final VictorSPX winchMotor2 = new VictorSPX(Constants.PORTS.MOTORS.WINCH_MOTOR2);

  public ArmSubsystem() {
    armMotor2.follow(armMotor1);
    this.armMotor2.setInverted(InvertType.OpposeMaster);

    winchMotor2.follow(winchMotor1);
    this.winchMotor2.setInverted(InvertType.OpposeMaster);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
