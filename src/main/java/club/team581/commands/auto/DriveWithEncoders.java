/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import club.team581.RobotContainer;
import club.team581.util.auto.Autonomous;

public class DriveWithEncoders extends CommandBase {
  TalonFX[] motors;
  double initalEncoderValue;

  /**
   * Drives forwards a certain amount using encoders.
   */
  public DriveWithEncoders() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    motors = RobotContainer.driveSubsystem.allMotors;
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(0, 0, 0);
    for (int i = 0; i < motors.length; i++) {
      initalEncoderValue = motors[i].getSelectedSensorPosition();
    }
    initalEncoderValue = initalEncoderValue / motors.length;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(Autonomous.getDriveCommand(24, initalEncoderValue), 0, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Autonomous.isFinished();
  }
}
