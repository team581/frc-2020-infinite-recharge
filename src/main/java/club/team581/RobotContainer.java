/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581;

import com.ctre.phoenix.motorcontrol.ControlMode;

import club.team581.commands.ToggleImageProcessingCommand;
import club.team581.subsystems.ArmSubsystem;
import club.team581.subsystems.ArmSubsystem.ArmSubsystemConstants;
import club.team581.subsystems.ColorSensorSubsystem;
import club.team581.subsystems.ControlPanelManipulatorSubsystem;
import club.team581.subsystems.DriveSubsystem;
import club.team581.subsystems.DumperSubsystem;
import club.team581.subsystems.SnarferSubsystem;
import club.team581.subsystems.SnarferSubsystem.SnarferIntakeDirection;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public final static ColorSensorSubsystem colorSensorSubsystem = new ColorSensorSubsystem();
  public final static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public final static ArmSubsystem armSubsystem = new ArmSubsystem();
  public final static DumperSubsystem dumperSubsystem = new DumperSubsystem();
  public final static SnarferSubsystem snarferSubsystem = new SnarferSubsystem();
  public final static ControlPanelManipulatorSubsystem controlPanelManipulatorSubsystem = new ControlPanelManipulatorSubsystem();
  public final static HttpCamera limelightCamera = new HttpCamera("limelight", "http://10.5.81.11:5800");

  public final static XboxController controller = new XboxController(Constants.Ports.CONTROLLER);

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
    // Note: A button is reserved and in use for Limelight autonomous driving
    final JoystickButton bButton = new JoystickButton(controller, XboxController.Button.kB.value);
    final JoystickButton yButton = new JoystickButton(controller, XboxController.Button.kY.value);
    final JoystickButton leftBumper = new JoystickButton(controller, XboxController.Button.kBumperLeft.value);
    final JoystickButton rightBumper = new JoystickButton(controller, XboxController.Button.kBumperRight.value);
    final JoystickButton leftTrigger = new JoystickButton(controller, XboxController.Axis.kLeftTrigger.value);
    final JoystickButton rightTrigger = new JoystickButton(controller, XboxController.Axis.kRightTrigger.value);

    yButton.whenPressed(new ToggleImageProcessingCommand());

    leftTrigger.whenActive(() -> {
      if (armSubsystem.armDeployed) {
        armSubsystem.winchMotor1.set(ControlMode.PercentOutput, ArmSubsystemConstants.winchSpeed);
      }
    });

    leftBumper.whenActive(() -> {
      armSubsystem.armDeployed = true;
      armSubsystem.armMotor1.set(ControlMode.PercentOutput, ArmSubsystemConstants.armSpeed);
    });
    rightBumper.whenActive(() -> {
      armSubsystem.armDeployed = false;
      armSubsystem.armMotor1.set(ControlMode.PercentOutput, -ArmSubsystemConstants.armSpeed);
    });

    bButton.whenPressed(() -> {
      if (driveSubsystem.orchestra.isPlaying()) {
        driveSubsystem.orchestra.stop();
      } else {
        driveSubsystem.orchestra.play();
      }
    });

    rightTrigger.whenActive(() -> snarferSubsystem.intakeMotor.set(SnarferIntakeDirection.IN.value));
    rightTrigger.whenInactive(() -> snarferSubsystem.intakeMotor.set(SnarferIntakeDirection.STOPPED.value));
  }
}
