/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands;

import club.team581.RobotContainer;
import club.team581.subsystems.SnarferSubsystem.SnarferConstants;
import club.team581.subsystems.SnarferSubsystem.SnarferDeploymentPosition;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleSnarferDeploymentCommand extends CommandBase {
  /**
   * Creates a new ToggleSnarferDeployment.
   */
  public ToggleSnarferDeploymentCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.snarferSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double speed = SnarferConstants.deploymentMotorSpeed;
    if (RobotContainer.snarferSubsystem.preferredPosition == SnarferDeploymentPosition.UP) {
      speed *= -1;
      RobotContainer.snarferSubsystem.preferredPosition = SnarferDeploymentPosition.DOWN;
    } else {
      RobotContainer.snarferSubsystem.preferredPosition = SnarferDeploymentPosition.UP;
    }

    RobotContainer.snarferSubsystem.deployMotor.set(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.snarferSubsystem.deployMotor.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.snarferSubsystem.getSnarferPosition() == RobotContainer.snarferSubsystem.preferredPosition;
  }
}
