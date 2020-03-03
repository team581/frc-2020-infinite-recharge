package club.team581.util;

/**
 * Util functions for joysticks.
 */
public class ControllerUtil {
  private final static float deadZone = 0.04f;
  private final static float fastThreshold = 0.98f;
  private final static float divisor = 3;
  private final static float min = 0.08f;

  /**
   * Scale a joystick value.
   */
  public static double joystickScale(double x) {
    return Math.copySign(positiveJoystickScale(Math.abs(x)), x);
  }

  /**
   * Scales a joystick value that is a positive number.
   */
  private static double positiveJoystickScale(double x) {
    if (x < deadZone) {
      // Deadzone
      return 0;
    } else if (x < fastThreshold) {
      // Regular usage
      return Math.max(x / divisor, min);
    } else {
      // Go really fast if you are flooring the joystick
      return 1;
    }
  }
}
