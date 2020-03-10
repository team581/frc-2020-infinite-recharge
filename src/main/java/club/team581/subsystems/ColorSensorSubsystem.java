/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import java.util.Arrays;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import club.team581.Constants.Ports;
import club.team581.Robot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensorSubsystem extends SubsystemBase {
  public final ColorSensorV3 sensor = new ColorSensorV3(Ports.COLOR_SENSOR);

  public final ColorMatch colorMatcher = new ColorMatch();
  public static final Color RED = new Color(0.505126953125, 0.360107421875, 0.134765625);
  public static final Color GREEN = new Color(0.20166015625, 0.550537109375, 0.247802734375);
  public static final Color BLUE = new Color(0.148681640625, 0.451171875, 0.400390625);
  public static final Color YELLOW = new Color(0.295166015625, 0.525390625, 0.179443359375);
  public ControlPanelColor recognizedColor;
  private final ControlPanelColor[] colorWheelValues = { ControlPanelColor.RED, ControlPanelColor.YELLOW,
      ControlPanelColor.BLUE, ControlPanelColor.GREEN };

  public enum ControlPanelColor {
    BLUE(0), RED(1), GREEN(2), YELLOW(3);

    public final int value;

    ControlPanelColor(final int value) {
      this.value = value;
    }
  }

  public final ControlPanelColor[] colorWheel = Arrays.copyOf(colorWheelValues, colorWheelValues.length);

  public ColorSensorSubsystem() {
    // This needs to be changed to the readouts we get in optimal conditions from
    // our sensor
    this.colorMatcher.addColorMatch(RED);
    this.colorMatcher.addColorMatch(GREEN);
    this.colorMatcher.addColorMatch(BLUE);
    this.colorMatcher.addColorMatch(YELLOW);
    // Avoid reporting on values that are inaccurate (like if we are nowhere near
    // the control panel)
    // The default value is 0.95
    this.colorMatcher.setConfidenceThreshold(0.90);
  }

  @Override
  public void periodic() {
    final Color detectedColor = this.sensor.getColor();
    final ColorMatchResult match = this.colorMatcher.matchClosestColor(detectedColor);

    String colorString;

    // This should be a switch statement but Java is stupid and won't let us
    if (match.color == BLUE) {
      recognizedColor = ControlPanelColor.BLUE;
      colorString = "Blue";
    } else if (match.color == RED) {
      recognizedColor = ControlPanelColor.RED;
      colorString = "Red";
    } else if (match.color == GREEN) {
      recognizedColor = ControlPanelColor.GREEN;
      colorString = "Green";
    } else if (match.color == YELLOW) {
      recognizedColor = ControlPanelColor.YELLOW;
      colorString = "Yellow";
    } else {
      recognizedColor = null;
      colorString = "Unknown";
    }

    Robot.shuffleboard.recognizedColor.setString(colorString);
    Robot.shuffleboard.colorConfidence.setDouble(match.confidence);
  }

  public static int rotationsToDesiredColor(final ControlPanelColor current, final ControlPanelColor desired) {
    return current.value - desired.value;
  }
}
