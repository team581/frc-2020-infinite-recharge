/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.controlPanel;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import club.team581.RobotContainer;
import club.team581.subsystems.ColorSensorSubsystem.ControlPanelColor;
import club.team581.subsystems.ControlPanelManipulatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotationCommand extends CommandBase {
  private final float desiredRotations;

  /**
   * A list of the order that colors were detected. No element is equal to the
   * element before or after it ([red, red, blue] would never happen)
   */
  private ArrayList<ControlPanelColor> recognizedColors = new ArrayList<ControlPanelColor>();

  /**
   * Creates a new FullRotationCommand.
   */
  public RotationCommand(ControlPanelColor initialColor, float desiredRotations) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.colorSensorSubsystem, RobotContainer.controlPanelManipulatorSubsystem);

    this.desiredRotations = desiredRotations;

    recognizedColors.add(initialColor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // TODO: Implement an algorithm to find the ideal direction (makes speed
    // TODO: negative/positive) to rotate
    RobotContainer.controlPanelManipulatorSubsystem.spinner.set(VictorSPXControlMode.PercentOutput,
        ControlPanelManipulatorSubsystem.speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (RobotContainer.colorSensorSubsystem.recognizedColor == null) {
      // No recognized color, so exit
      return;
    }

    // Array is initialized with initialColor in the constructor
    if (recognizedColors.get(recognizedColors.size() - 1) != RobotContainer.colorSensorSubsystem.recognizedColor) {
      // The latest color we detected is different than this one
      recognizedColors.add(RobotContainer.colorSensorSubsystem.recognizedColor);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.controlPanelManipulatorSubsystem.spinner.set(VictorSPXControlMode.PercentOutput, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (recognizedColors.size() / 8) >= desiredRotations;
  }
}
