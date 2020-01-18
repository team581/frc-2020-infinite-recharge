/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import club.team581.subsystems.DriveSubsystem;
import club.team581.util.limelight.Limelight;
import club.team581.Robot;
import club.team581.Constants.LIMELIGHT.MOVEMENT;

public class LimelightMovingCommand extends CommandBase {
  /**
   * Creates a new LimelightMovingCommand.
   */
  public LimelightMovingCommand(final DriveSubsystem drive) {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Start with the steering
    double steerCmd = Limelight.NetworkTables.horizontalOffset() * MOVEMENT.STEER_SPEED;
    Robot.LimelightSteerCommand = steerCmd;

    // Try to drive towards the target
    double driveCmd = (MOVEMENT.TARGET_AREA_SIZE - Limelight.NetworkTables.targetArea()) * MOVEMENT.DRIVE_SPEED;

    // If it goes too quick, slow down drive speed
    if (driveCmd > MOVEMENT.MAX_DRIVE_SPEED) {
      driveCmd = MOVEMENT.MAX_DRIVE_SPEED;
    }
    Robot.LimelightDriveCommand = driveCmd;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
