/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import club.team581.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  public final VictorSPX armMotor1 = new VictorSPX(Constants.Ports.Motors.ARM_MOTOR1);
  public final VictorSPX armMotor2 = new VictorSPX(Constants.Ports.Motors.ARM_MOTOR2);

  public final VictorSPX winchMotor1 = new VictorSPX(Constants.Ports.Motors.WINCH_MOTOR1);
  public final VictorSPX winchMotor2 = new VictorSPX(Constants.Ports.Motors.WINCH_MOTOR2);

  /** Whether or not the arm has been deployed. */
  public boolean armDeployed = false;

  public ArmSubsystem() {
    armMotor2.follow(armMotor1);
    this.armMotor2.setInverted(InvertType.OpposeMaster);

    winchMotor2.follow(winchMotor1);
    this.winchMotor2.setInverted(InvertType.OpposeMaster);

    winchMotor1.setNeutralMode(NeutralMode.Coast);
    winchMotor2.setNeutralMode(NeutralMode.Coast);
  }

  public final static class ArmSubsystemConstants {
    public final static double winchSpeed = 0.75;
    public final static double armSpeed = 0.75;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
