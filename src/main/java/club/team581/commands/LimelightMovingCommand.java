/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands;

import club.team581.Constants.Limelight.Movement;
import club.team581.Robot;
import club.team581.util.limelight.Limelight;
import club.team581.util.limelight.VisionTarget;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimelightMovingCommand extends CommandBase {
  private final VisionTarget visionTarget;
  private final double limelightAngleOfElevation;
  private boolean finished = false;

  /**
   * Creates a new LimelightMovingCommand.
   */
  public LimelightMovingCommand(final double limelightAngleOfElevation, final VisionTarget visionTarget) {
    this.visionTarget = visionTarget;
    this.limelightAngleOfElevation = limelightAngleOfElevation;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  private double limitToMaxSpeed(double value, double absoluteMaxValue) {
    return Math.max(-absoluteMaxValue, Math.min(value, absoluteMaxValue));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Start with the steering
    final double steerCmd = Limelight.NetworkTables.horizontalOffset() * Movement.STEER_SPEED;
    Robot.LimelightSteerCommand = limitToMaxSpeed(steerCmd, 0.25);

    final double distance = Limelight.distanceToTarget(this.limelightAngleOfElevation, visionTarget);

    if (distance == -1) {
      this.finished = true;

      this.interrupt();
      return;
    }

    // Try to drive towards the target
    double driveCmd = (visionTarget.desiredDistance - distance) * Movement.DRIVE_SPEED;

    Robot.LimelightDriveCommand = limitToMaxSpeed(driveCmd, Movement.MAX_DRIVE_SPEED);
    System.out.println("distance: " + distance + " drive: " + String.valueOf(Robot.LimelightDriveCommand) + " steer:"
        + String.valueOf(Robot.LimelightSteerCommand));
    this.finished = false;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    if (interrupted) {
      this.interrupt();
    }
  }

  /** Disable the drive commands and stop the robot. */
  private void interrupt() {
    Robot.LimelightDriveCommand = 0;
    Robot.LimelightSteerCommand = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
