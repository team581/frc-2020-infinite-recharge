/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.auto;

import club.team581.Constants;
import club.team581.RobotContainer;
import club.team581.util.limelight.Limelight;
import club.team581.util.limelight.Limelight.LimelightMotion;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveWithLimelight extends CommandBase {
  final LimelightMotion limelightMotion = Limelight.getDriveCommand(
      Constants.Autonomous.Measurements.LIMELIGHT_ANGLE_OF_ELEVATION, Constants.Autonomous.Targets.LoadingBay);
  /**
   * Creates a new DriveWithLimelight.
   */
  public DriveWithLimelight() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(0, 0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(
      limelightMotion.yAxisTranslation, limelightMotion.xAxisTranslation, limelightMotion.zAxisRotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return limelightMotion.isFinished;
  }
}
