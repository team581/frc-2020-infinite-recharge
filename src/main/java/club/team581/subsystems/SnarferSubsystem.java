/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SnarferSubsystem extends SubsystemBase {
  public final WPI_VictorSPX intakeMotor = new WPI_VictorSPX(SnarferConstants.snarferMotorPort);
  public SnarferIntakeDirection preferredDirection = SnarferIntakeDirection.STOPPED;
  private SnarferIntakeDirection previousDirection;

  public SnarferSubsystem() {
    this.intakeMotor.setInverted(false);
  }

  public static enum SnarferIntakeDirection {
    IN(SnarferConstants.intakeMotorSpeed), STOPPED(0), OUT(-SnarferConstants.intakeMotorSpeed);

    public final double value;

    SnarferIntakeDirection(final double speed) {
      this.value = speed;
    }
  }

  public static final class SnarferConstants {
    /** How fast the intake motor should move. Should be from [-1, 1]. */
    public static final double intakeMotorSpeed = 0.4;

    /** Motor port for moving the wheels that touch the power cells. */
    public static final int snarferMotorPort = 20;
  }

  @Override
  public void periodic() {
    if (this.preferredDirection != this.previousDirection) {
      this.intakeMotor.set(this.preferredDirection.value);
      this.previousDirection = this.preferredDirection;
    }
  }
}
