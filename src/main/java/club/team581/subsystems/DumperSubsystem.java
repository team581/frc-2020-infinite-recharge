/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import club.team581.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DumperSubsystem extends SubsystemBase {
  public final DoubleSolenoid dumper = new DoubleSolenoid(Constants.Ports.DUMPER_SOLENOID.forward, Constants.Ports.DUMPER_SOLENOID.reverse);

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
