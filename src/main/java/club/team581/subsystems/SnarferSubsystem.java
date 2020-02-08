/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import club.team581.Constants;
import club.team581.Constants.Ports.Motors.Snarfer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SnarferSubsystem extends SubsystemBase {
  public final WPI_TalonSRX deployMotor = new WPI_TalonSRX(Snarfer.DEPLOY);
  public final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(Snarfer.INTAKE);
  public final DigitalInput upperLimitSwitch = new DigitalInput(Constants.Ports.LimitSwitches.SNARFER_UPPER);
  public final DigitalInput lowerLimitSwitch = new DigitalInput(Constants.Ports.LimitSwitches.SNARFER_LOWER);
  public SnarferDeploymentPosition preferredPosition = SnarferDeploymentPosition.UP;
  public SnarferIntakeDirection preferredSpeed = SnarferIntakeDirection.STOPPED;

  /**
   * Represents the states the snarfer deployment can be.
   */
  public static enum SnarferDeploymentPosition {
    /**
     * The snarfer is vertical and unable to pick up power cells. This is the
     * starting position.
     */
    UP,
    /** The snarfer is level with the ground and is ready to pick up power cells. */
    DOWN,
    /** The snarfer is moving between the up and down down states. */
    MOVING
  }

  public static enum SnarferIntakeDirection {
    IN(SnarferConstants.intakeMotorSpeed),
    STOPPED(0),
    OUT(-SnarferConstants.intakeMotorSpeed);

    public final double value;

    SnarferIntakeDirection(final double speed) {
      this.value = speed;
    }
  }

  public final SnarferDeploymentPosition getSnarferPosition() {
    if (upperLimitSwitch.get() && !lowerLimitSwitch.get()) {
      return SnarferDeploymentPosition.UP;
    } else if (!upperLimitSwitch.get() && lowerLimitSwitch.get()) {
      return SnarferDeploymentPosition.DOWN;
    } else if (!upperLimitSwitch.get() && !lowerLimitSwitch.get()) {
      return SnarferDeploymentPosition.MOVING;
    }

    throw new Error("Both snarfer limit switches are pressed down, unable to accurately determine snarfer deployment position.");
  }

  public static final class SnarferConstants {
    /** How fast the deployment motor should move. Should be from [-1, 1]. */
    public static final double deploymentMotorSpeed = 0.25;
    /** How fast the intake motor should move. Should be from [-1, 1]. */
    public static final double intakeMotorSpeed = 0.25;
  }
}
