/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.revrobotics.ColorSensorV3;

import club.team581.Constants.PORTS;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensorSubsystem extends SubsystemBase {
  private ColorSensorV3 sensor;

  /**
   * Creates a new ColorSensorSubsystem.
   */
  public ColorSensorSubsystem() {
    this.sensor = new ColorSensorV3(PORTS.COLOR_SENSOR);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
