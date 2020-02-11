package club.team581.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import club.team581.RobotContainer;
import club.team581.util.limelight.Limelight;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/// Visualization of the Shuffleboard layout         \\\
/// Please update this when the layout changes       \\\
/// This is a 10x6 grid, upper left corner is (0, 0) \\\
/// Dimensions can change depending on screen size   \\\
//  0123456789
// +----------+
// |XMMMMMLLLL|
// |YMMMMMLLLL|
// |ZSDTGGLLLL|
// |CSDTGGLLLL|
// |NSDT  LLLL|
// +----------+
// Key:
// C: Recognized color
// N: Recognized color confidence
// M: Mecanum drive visualization
// S: Strafing PID Loop
// D: Driving PID Loop
// T: Strafing PID Loop
// G: PID Output Graph
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
  private final NetworkTableEntry pidGraph = tab.add("PID Graph", new double[] { 0, 0, 0 }).withPosition(4, 2)
      .withSize(2, 2).withWidget(BuiltInWidgets.kGraph).getEntry();

  public final NetworkTableEntry recognizedColor = tab.add("Recognized color", "(nothing yet)").withPosition(0, 3)
      .withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
  public final NetworkTableEntry colorConfidence = tab.add("Color sensor confidence", 0).withPosition(0, 4)
      .withSize(1, 1).withWidget(BuiltInWidgets.kNumberBar).getEntry();

  public final ComplexWidget mecanumDrive = tab.add("Mecanum Drive", RobotContainer.driveSubsystem.mecanumDrive)
      .withPosition(1, 0).withSize(4, 2).withWidget(BuiltInWidgets.kMecanumDrive);
  public final ComplexWidget limelightCamera = tab.add("Limelight Camera", RobotContainer.limelightCamera)
      .withPosition(6, 0).withSize(5, 5).withWidget(BuiltInWidgets.kCameraStream)
      .withProperties(Map.of("Show crosshair", false, "Show controls", false));
  public final ComplexWidget strafePID = tab.add("Strafe PID", Limelight.strafeController).withPosition(1, 2)
      .withSize(1, 1).withWidget(BuiltInWidgets.kPIDController);
  public final ComplexWidget distancePID = tab.add("Distance PID", Limelight.distanceController).withPosition(2, 2)
      .withSize(1, 1).withWidget(BuiltInWidgets.kPIDController);
  public final ComplexWidget rotationPID = tab.add("Rotation PID", Limelight.rotationController).withPosition(3, 2)
      .withSize(1, 1).withWidget(BuiltInWidgets.kPIDController);

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

  public final void logPIDValues(final double strafe, final double distance, final double rotation) {

    this.pidGraph.setDoubleArray(new double[] { strafe, distance, rotation });
  }
}
