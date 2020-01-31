package club.team581.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import club.team581.RobotContainer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.cscore.MjpegServer;

/// Visualization of the Shuffleboard layout         \\\
/// Please update this when the layout changes       \\\
/// This is a 10x6 grid, upper left corner is (0, 0) \\\
/// Dimensions can change depending on screen size   \\\
//  0123456789
// +----------+
// |XDDDDLLLLL|
// |YDDDDLLLLL|
// |ZDDDDLLLLL|
// |C    LLLLL|
// |N    LLLLL|
// +----------+
// Key:
// C: Recognized color
// N: Recognized color confidence
// D: Mecanum drive visualization
// L: Limelight camera output
// X: Joystick X output
// Y: Joystick Y output
// Z: Joystick Z output
public final class ShuffleboardLogger {
  /** Current time used to help distinguish the name of the Shuffleboard tab. */
  private static final String currentTime = DateTimeFormatter.ofPattern("hh:mm:ss").format(ZonedDateTime.now())
      .toString();

  /** String used to identify this Shuffleboard tab from duplicates. */
  private static final String tabTitle = currentTime;

  /** Shuffleboard tab used for logging. */
  public static final ShuffleboardTab tab = Shuffleboard.getTab(tabTitle);

  private final NetworkTableEntry joyX = tab.add("Joystick X", 0).withPosition(0, 0).withSize(1, 1)
      .withWidget(BuiltInWidgets.kNumberBar).getEntry();
  private final NetworkTableEntry joyY = tab.add("Joystick Y", 0).withPosition(0, 1).withSize(1, 1)
      .withWidget(BuiltInWidgets.kNumberBar).getEntry();
  private final NetworkTableEntry joyZ = tab.add("Joystick Z", 0).withPosition(0, 2).withSize(1, 1)
      .withWidget(BuiltInWidgets.kNumberBar).getEntry();

  public final NetworkTableEntry recognizedColor = tab.add("Recognized color", "(nothing yet)").withPosition(0, 3)
      .withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
  public final NetworkTableEntry colorConfidence = tab.add("Color sensor confidence", 0).withPosition(0, 4)
      .withSize(1, 1).withWidget(BuiltInWidgets.kNumberBar).getEntry();

  public final ComplexWidget mecanumDrive = tab.add("Mecanum Drive", RobotContainer.driveSubsystem.mecanumDrive)
      .withPosition(1, 0).withSize(4, 3).withWidget(BuiltInWidgets.kMecanumDrive);
  public final ComplexWidget limelightCamera = tab.add("Limelight Camera", RobotContainer.limelightCamera)
  .withPosition(5, 0).withSize(6, 5).withWidget(BuiltInWidgets.kCameraStream).withProperties(Map.of("Show crosshair", false, "Show controls", false));

  /**
   * Log joystick values using a graph and number bars on Shuffleboard.
   *
   * @param x X-axis value to log
   * @param y Y-axis value to log
   * @param z Z-axis value to log
   */
  public final void logJoystickValues(final double x, final double y, final double z) {
    this.joyX.setDouble(x);
    this.joyY.setDouble(y);
    this.joyZ.setDouble(z);
  }
}
