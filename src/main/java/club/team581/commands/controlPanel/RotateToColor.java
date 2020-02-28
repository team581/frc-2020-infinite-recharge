/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.controlPanel;

import com.ctre.phoenix.motorcontrol.ControlMode;

import club.team581.RobotContainer;
import club.team581.subsystems.ColorSensorSubsystem.ControlPanelColor;
import club.team581.subsystems.ControlPanelManipulatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Rotate the control panel to a specified color.
 */
public class RotateToColor extends CommandBase {
  private final ControlPanelColor desiredColor;

  /**
   * Creates a new RotateToColor.
   */
  public RotateToColor(final ControlPanelColor color) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.colorSensorSubsystem, RobotContainer.controlPanelManipulatorSubsystem);

    desiredColor = color;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ControlPanelColor recognizedColor = RobotContainer.colorSensorSubsystem.recognizedColor;

    int rotationAmount = recognizedColor.value - 1;
    int multiplier = rotationAmount == -1 ? -1 : 1;

    RobotContainer.controlPanelManipulatorSubsystem.spinner.set(ControlMode.PercentOutput,
        ControlPanelManipulatorSubsystem.speed * multiplier);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    RobotContainer.controlPanelManipulatorSubsystem.spinner.set(ControlMode.PercentOutput, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.colorSensorSubsystem.recognizedColor == desiredColor;
  }
}
