/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import club.team581.RobotContainer;
import club.team581.util.auto.Autonomous;

public class DriveWithEncoders extends CommandBase {
  TalonFX[] motors;
  double initialEncoderValue = 0;
  int distanceToDrive;

  /**
   * Drives forwards a certain distance using encoders.
   * @param distanceToDrive distance to drive (in inches)
   */
  public DriveWithEncoders(int distanceToDrive) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.driveSubsystem);

    this.distanceToDrive = distanceToDrive;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    motors = RobotContainer.driveSubsystem.allMotors;
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(0, 0, 0);
    /** Gets all of the encoder values of the  */
    for (int i = 0; i < motors.length; i++) {
      initialEncoderValue = initialEncoderValue + motors[i].getSelectedSensorPosition();
    }
    initialEncoderValue = initialEncoderValue / motors.length;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.driveSubsystem.mecanumDrive.driveCartesian(Autonomous.getDriveCommand(distanceToDrive, initialEncoderValue), 0, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Autonomous.isFinished();
  }
}
