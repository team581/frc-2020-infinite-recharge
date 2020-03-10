package club.team581.util;

/**
 * Util functions for joysticks.
 */
public class ControllerUtil {
  private static final float DEAD_ZONE = 0.04f;
  private static final float FAST_THRESHOLD = 0.98f;
  private static final float DIVISOR = 3;
  private static final float MIN = 0.08f;

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
    if (x < DEAD_ZONE) {
      // Deadzone
      return 0;
    } else if (x < FAST_THRESHOLD) {
      // Regular usage
      return Math.max(x / DIVISOR, MIN);
    } else {
      // Go really fast if you are flooring the joystick
      return 1;
    }
  }
}
