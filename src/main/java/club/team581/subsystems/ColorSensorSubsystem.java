/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import club.team581.Constants.Ports;
import club.team581.Robot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensorSubsystem extends SubsystemBase {
  public final ColorSensorV3 sensor = new ColorSensorV3(Ports.COLOR_SENSOR);

  public final ColorMatch colorMatcher = new ColorMatch();
  public final static Color RED = new Color(new Color8Bit(255, 0, 0));
  public final static Color GREEN = new Color(new Color8Bit(0, 255, 0));
  public final static Color BLUE = new Color(new Color8Bit(0, 0, 255));
  public final static Color YELLOW = new Color(new Color8Bit(255, 255, 0));

  public enum ControlPanelColors {
    blue(0), red(1), green(2), yellow(3);

    public final int value;

    ControlPanelColors(int value) {
      this.value = value;
    }
  }

  private static final ControlPanelColors[] colorWheel = { ControlPanelColors.red, ControlPanelColors.yellow,
      ControlPanelColors.blue, ControlPanelColors.green };

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
    this.colorMatcher.setConfidenceThreshold(0.85);
  }

  @Override
  public void periodic() {
    final Color detectedColor = this.sensor.getColor();
    final ColorMatchResult match = this.colorMatcher.matchClosestColor(detectedColor);

    String colorString;

    // This should be a switch statement but Java is stupid and won't let us
    if (match.color == BLUE) {
      colorString = "Blue";
    } else if (match.color == RED) {
      colorString = "Red";
    } else if (match.color == GREEN) {
      colorString = "Green";
    } else if (match.color == YELLOW) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    Robot.shuffleboard.recognizedColor.setString(colorString);
    Robot.shuffleboard.colorConfidence.setDouble(match.confidence);
  }

  public static int rotationsToDesiredColor(ControlPanelColors current, ControlPanelColors desired) {
    return current.value - desired.value;
  }
}
