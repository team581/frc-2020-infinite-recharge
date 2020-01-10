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
  public final ColorSensorV3 sensor;

  /**
   * Creates a new ColorSensorSubsystem.
   */
  public ColorSensorSubsystem() {
    this.sensor = new ColorSensorV3(PORTS.COLOR_SENSOR);
  }

  @Override
  public void periodic() {
    System.out.println("[ColorSensorSubsystem] \u001b[31mRed " + Integer.toString(this.sensor.getRed()) + "\u001b[0m");
    System.out.println("[ColorSensorSubsystem] \u001b[32mGreen " + Integer.toString(this.sensor.getGreen()) + "\u001b[0m");
    System.out.println("[ColorSensorSubsystem] \u001b[34mBlue " + Integer.toString(this.sensor.getBlue()) + "\u001b[0m");
  }
}
