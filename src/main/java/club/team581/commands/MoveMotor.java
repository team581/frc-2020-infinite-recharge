/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import club.team581.RobotContainer;
import club.team581.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.Encoder;

public class MoveMotor extends CommandBase {
  /**
   * Creates a new MoveArm.
   */
  double speed = 0;
  WPI_VictorSPX motor;
  public MoveMotor(WPI_VictorSPX motorToMove, double armSpeed) {
   speed = armSpeed;
   motorToMove = motor;
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    motor.set(speed);
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}







