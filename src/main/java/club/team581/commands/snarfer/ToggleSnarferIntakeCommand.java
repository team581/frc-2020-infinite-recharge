/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.snarfer;

import club.team581.RobotContainer;
import club.team581.subsystems.SnarferSubsystem.SnarferIntakeDirection;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * Toggles snarfer intake from `stopped` <-> `in` based on preferred snarfer
 * direction.
 */
public class ToggleSnarferIntakeCommand extends InstantCommand {
  public ToggleSnarferIntakeCommand() {
    addRequirements(RobotContainer.snarferSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // The reverse OUT mode is not used because it's only around for emergencies
    if (RobotContainer.snarferSubsystem.preferredSpeed == SnarferIntakeDirection.IN) {
      RobotContainer.snarferSubsystem.preferredSpeed = SnarferIntakeDirection.STOPPED;
    } else {
      // The snarfer is not grabbing power cells, so start the intake
      RobotContainer.snarferSubsystem.preferredSpeed = SnarferIntakeDirection.IN;
    }
  }
}
