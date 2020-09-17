/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package club.team581.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import club.team581.Constants.Ports.Motors;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  public final WPI_TalonFX frontRightMotor = new WPI_TalonFX(Motors.FRONT_RIGHT);
  public final WPI_TalonFX frontLeftMotor = new WPI_TalonFX(Motors.FRONT_LEFT);
  public final WPI_TalonFX rearRightMotor = new WPI_TalonFX(Motors.REAR_RIGHT);
  public final WPI_TalonFX rearLeftMotor = new WPI_TalonFX(Motors.REAR_LEFT);
  public final TalonFX[] allMotors = { frontRightMotor, frontLeftMotor, rearRightMotor, rearLeftMotor };
  public final MecanumDrive mecanumDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor,
      rearRightMotor);
  public final Orchestra orchestra;

  public DriveSubsystem() {
    final TalonFXConfiguration configs = new TalonFXConfiguration();

    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* A list of TalonFX's that are to be used as instruments */
    final List<TalonFX> instruments = new ArrayList<>();

    /* Initialize the TalonFX's to be used */
    for (int i = 0; i < allMotors.length; ++i) {
      instruments.add(allMotors[i]);
      // Add settings for Falcons
      allMotors[i].configAllSettings(configs);
    }

    orchestra = new Orchestra(instruments);

    orchestra.loadMusic("song.chrp");
  }
}
