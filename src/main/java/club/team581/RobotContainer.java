/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581;

import com.ctre.phoenix.motorcontrol.ControlMode;

import club.team581.subsystems.ArmSubsystem;
import club.team581.subsystems.ArmSubsystem.ArmSubsystemConstants;
import club.team581.subsystems.ColorSensorSubsystem;
import club.team581.subsystems.ControlPanelManipulatorSubsystem;
import club.team581.subsystems.DriveSubsystem;
import club.team581.subsystems.DumperSubsystem;
import club.team581.subsystems.SnarferSubsystem;
import club.team581.subsystems.SnarferSubsystem.SnarferIntakeDirection;
import club.team581.util.limelight.Limelight;
import club.team581.util.limelight.Limelight.NetworkTables.LimelightConstants.CameraMode;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static final ColorSensorSubsystem colorSensorSubsystem = new ColorSensorSubsystem();
  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static final ArmSubsystem armSubsystem = new ArmSubsystem();
  public static final DumperSubsystem dumperSubsystem = new DumperSubsystem();
  public static final SnarferSubsystem snarferSubsystem = new SnarferSubsystem();
  public static final ControlPanelManipulatorSubsystem controlPanelManipulatorSubsystem = new ControlPanelManipulatorSubsystem();
  public static final HttpCamera limelightCamera = new HttpCamera("limelight", "http://10.5.81.11:5800");

  public static final XboxController controller = new XboxController(Constants.Ports.CONTROLLER);
  public static final float TRIGGER_DEADZONE = 0.15f;

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
    final Trigger leftTrigger = new Trigger(() -> controller.getTriggerAxis(Hand.kLeft) >= TRIGGER_DEADZONE);
    final Trigger rightTrigger = new Trigger(() -> controller.getTriggerAxis(Hand.kRight) >= TRIGGER_DEADZONE);

    yButton.whenPressed(new ConditionalCommand(new InstantCommand(() -> {
      Limelight.NetworkTables.setPipelineIndex(9);
      Limelight.NetworkTables.setImageProcessingMode(CameraMode.RAW);
    }), new InstantCommand(() -> {
      // TODO: This pipeline index should not be hardcoded
      Limelight.NetworkTables.setPipelineIndex(0);
      Limelight.NetworkTables.setImageProcessingMode(CameraMode.VISION_PROCESSOR);
    }), () -> Limelight.NetworkTables.imageProcessingMode() == CameraMode.VISION_PROCESSOR));

    leftTrigger.whenActive(() -> {
      if (armSubsystem.armDeployed) {
        armSubsystem.winchMotor1.set(ControlMode.PercentOutput, ArmSubsystemConstants.WINCH_SPEED);
      }
    });

    leftBumper.whenActive(() -> {
      armSubsystem.armDeployed = true;
      armSubsystem.armMotor1.set(ControlMode.PercentOutput, ArmSubsystemConstants.ARM_SPEED);
    });
    rightBumper.whenActive(() -> {
      armSubsystem.armDeployed = false;
      armSubsystem.armMotor1.set(ControlMode.PercentOutput, -ArmSubsystemConstants.ARM_SPEED);
    });

    bButton.whenPressed(() -> {
      if (driveSubsystem.orchestra.isPlaying()) {
        driveSubsystem.orchestra.stop();
      } else {
        driveSubsystem.orchestra.play();
      }
    });

    rightTrigger.whenActive(() -> snarferSubsystem.intakeMotor.set(SnarferIntakeDirection.IN.value))
        .whenInactive(() -> snarferSubsystem.intakeMotor.set(SnarferIntakeDirection.STOPPED.value));
    rightTrigger.and(leftBumper).whenActive(() -> snarferSubsystem.intakeMotor.set(SnarferIntakeDirection.OUT.value * 2))
        .whenInactive(() -> snarferSubsystem.intakeMotor.set(SnarferIntakeDirection.STOPPED.value));
  }
}
