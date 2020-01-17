/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import club.team581.Constants;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  public final WPI_TalonFX frontRightMotor = new WPI_TalonFX(Constants.PORTS.MOTORS.FRONT_RIGHT);
  public final WPI_TalonFX frontLeftMotor = new WPI_TalonFX(Constants.PORTS.MOTORS.FRONT_LEFT);
  public final WPI_TalonFX rearRightMotor = new WPI_TalonFX(Constants.PORTS.MOTORS.REAR_RIGHT);
  public final WPI_TalonFX rearLeftMotor = new WPI_TalonFX(Constants.PORTS.MOTORS.REAR_LEFT);
  public final MecanumDrive mecanumDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor,
      rearRightMotor);
}
