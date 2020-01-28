/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import club.team581.Constants.PORTS.MOTORS;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  public final WPI_TalonFX flyWheelMotor = new WPI_TalonFX(MOTORS.FLYWHEEL_MOTOR);
}
