/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  public final WPI_TalonFX flyWheelMotor = new WPI_TalonFX(ShooterConstants.flyWheelMotorPort);

  public final static class ShooterConstants {
    public final static double flyWheelSpeed = 0.5;
    public final static int flyWheelMotorPort = 10;
  }
}
