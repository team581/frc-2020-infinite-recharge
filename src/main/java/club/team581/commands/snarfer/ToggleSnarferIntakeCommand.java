/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.snarfer;

import club.team581.RobotContainer;
import club.team581.subsystems.SnarferSubsystem.SnarferDeploymentPosition;
import club.team581.subsystems.SnarferSubsystem.SnarferIntakeDirection;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
* Toggles snarfer intake from `stopped` <-> `in` based on preferred snarfer
* position.
*/
public class ToggleSnarferIntakeCommand extends InstantCommand {
  public ToggleSnarferIntakeCommand() {
    addRequirements(RobotContainer.snarferSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (RobotContainer.snarferSubsystem.preferredPosition == SnarferDeploymentPosition.DOWN) {
      RobotContainer.snarferSubsystem.preferredSpeed = SnarferIntakeDirection.IN;
    } else {
      // The snarfer is not down so stop the intake
      RobotContainer.snarferSubsystem.preferredSpeed = SnarferIntakeDirection.STOPPED;
    }

    RobotContainer.snarferSubsystem.deployMotor.set(RobotContainer.snarferSubsystem.preferredSpeed.value);
  }
}
