/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581;

import club.team581.commands.ExampleCommand;
import club.team581.commands.LimelightMovingCommand;
import club.team581.commands.MoveArm;
import club.team581.subsystems.ColorSensorSubsystem;
import club.team581.subsystems.DriveSubsystem;
import club.team581.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand autoCommand = new ExampleCommand(exampleSubsystem);

  public final ColorSensorSubsystem colorSensorSubsystem = new ColorSensorSubsystem();
  public final static DriveSubsystem driveSubsystem = new DriveSubsystem();

  public final static XboxController controller = new XboxController(Constants.PORTS.CONTROLLER);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final JoystickButton aButton = new JoystickButton(controller, XboxController.Button.kA.value);
    final JoystickButton bButton = new JoystickButton(controller, XboxController.Button.kB.value);

    aButton.whenHeld(new LimelightMovingCommand(Constants.LIMELIGHT.MEASUREMENTS.LIMELIGHT_ANGLE_OF_ELEVATION,
        Constants.LIMELIGHT.TARGETS.LoadingBay));
    bButton.whenHeld(new LimelightMovingCommand(Constants.LIMELIGHT.MEASUREMENTS.LIMELIGHT_ANGLE_OF_ELEVATION,
        Constants.LIMELIGHT.TARGETS.PowerPort));

    double leftTriggerAxis = 0;
    if (controller.getTriggerAxis(Hand.kLeft) > 0){
      if (controller.getTriggerAxis(Hand.kLeft) >= 0.7){
        leftTriggerAxis = 0.7;
      } else {
        leftTriggerAxis = controller.getTriggerAxis(Hand.kLeft);
      }
      new MoveArm(leftTriggerAxis);
    } 
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoCommand;
  }
}
