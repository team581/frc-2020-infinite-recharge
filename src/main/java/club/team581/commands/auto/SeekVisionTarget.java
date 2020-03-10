/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.auto;

import club.team581.RobotContainer;
import club.team581.commands.auto.SeekVisionTarget.Constants.StartPosition;
import club.team581.util.limelight.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Spins in a circle until a Limelight target is identified.
 */
public class SeekVisionTarget extends CommandBase {
  private final StartPosition startPosition;

  /**
   * Creates a new SeekVisionTarget.
   */
  public SeekVisionTarget(StartPosition robotStartPosition) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.driveSubsystem);

    this.startPosition = robotStartPosition;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(0, 0,
        this.startPosition == StartPosition.LEFT ? Constants.ROTATION_SPEED : -Constants.ROTATION_SPEED);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Limelight.NetworkTables.targetsExist();
  }

  public static final class Constants {
    public static final double ROTATION_SPEED = 0.5;

    /** The positions your robot could start in when in autonomous mode. */
    public static enum StartPosition {
      LEFT, MIDDLE, RIGHT
    }
  }
}
