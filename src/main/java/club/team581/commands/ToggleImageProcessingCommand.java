/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.commands;

import club.team581.util.limelight.Limelight;
import club.team581.util.limelight.Limelight.NetworkTables.Constants.CameraMode;
import edu.wpi.first.wpilibj2.command.InstantCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ToggleImageProcessingCommand extends InstantCommand {
  public ToggleImageProcessingCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    final CameraMode currentCameraMode = Limelight.NetworkTables.imageProcessingMode();

    // Toggle the camera modes from vision processing <-> raw camera output
    if (currentCameraMode == CameraMode.VISION_PROCESSOR) {
      Limelight.NetworkTables.setPipelineIndex(9);
      Limelight.NetworkTables.setImageProcessingMode(CameraMode.RAW);
    } else {
      // TODO: This pipeline index should not be hardcoded
      Limelight.NetworkTables.setPipelineIndex(0);
      Limelight.NetworkTables.setImageProcessingMode(CameraMode.VISION_PROCESSOR);
    }
  }
}
