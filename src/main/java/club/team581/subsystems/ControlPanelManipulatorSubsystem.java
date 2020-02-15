/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import club.team581.Constants.Ports.Motors;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanelManipulatorSubsystem extends SubsystemBase {
  public final static double speed = 0.5;
  final public VictorSPX spinner = new VictorSPX(Motors.CONTROL_PANEL);

  /**
   * Creates a new ControlPanelManipulatorSubsystem.
   */
  public ControlPanelManipulatorSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
