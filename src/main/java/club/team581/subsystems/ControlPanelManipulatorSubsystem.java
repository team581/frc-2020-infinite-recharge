/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import club.team581.Constants.Ports.Motors;
import club.team581.subsystems.ColorSensorSubsystem.ControlPanelColor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanelManipulatorSubsystem extends SubsystemBase {
  /**
   * The color we needd to move the control panel to in order tohave the field
   * scanner see the goal color.
   */
  public ControlPanelColor desiredColor;
  public final static double speed = 0.5;
  final public VictorSPX spinner = new VictorSPX(Motors.CONTROL_PANEL);

  /**
   * Creates a new ControlPanelManipulatorSubsystem.
   */
  public ControlPanelManipulatorSubsystem() {

  }

  @Override
  // This method will be called once per scheduler run
  public void periodic() {
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
        case 'B':
          // Blue case code
          desiredColor = ControlPanelColor.red;
          break;
        case 'G':
          // Green case code
          desiredColor = ControlPanelColor.yellow;
          break;
        case 'R':
          // Red case code
          desiredColor = ControlPanelColor.blue;
          break;
        case 'Y':
          // Yellow case code
          desiredColor = ControlPanelColor.green;
          break;
        default:
          // This is corrupt data
          desiredColor = null;
          break;
      }
    } else {
      desiredColor = null;
      // Code for no data received yet
    }
  }
}
