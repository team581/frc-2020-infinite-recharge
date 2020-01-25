/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands;

import club.team581.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class MoveArmCommand extends ParallelCommandGroup {
  double speed = 0;

  /**
   * Creates a new MoveArm.
   */
  public MoveArmCommand(double armSpeed) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new MoveMotor(ArmSubsystem.armMotor1, armSpeed), new MoveMotor(ArmSubsystem.armMotor2, -armSpeed));
  }
}
