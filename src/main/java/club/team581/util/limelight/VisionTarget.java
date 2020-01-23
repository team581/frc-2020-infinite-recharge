/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.util.limelight;

public class VisionTarget {
  public final int desiredDistance;
  public final int pipelineIndex;
  public final double height;

  public VisionTarget(int desiredDistance, double height, int pipelineIndex) {
    this.desiredDistance = desiredDistance;
    this.height = height;
    this.pipelineIndex = pipelineIndex;
  }
}
