package club.team581.util;

/**
 * Data structure for the forward and reverse channels on a solenoid.
 */
public class DoubleSolenoidChannels {

  /**
   * @param forwardChannel The channel number for the forward channel of the double solenoid
   * @param reverseChannel The channel number for the forward channel of the double solenoid
   */
  public DoubleSolenoidChannels(int forwardChannel, int reverseChannel) {
    this.forward = forwardChannel;
    this.reverse = reverseChannel;
  }

  /** Channel number for the forward channel of the double solenoid. */
  public int forward = -1;

  /** Channel number for the reverse channel of the double solenoid. */
  public int reverse = -1;
}
