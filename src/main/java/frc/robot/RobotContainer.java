// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Shooter.AutoAlign;
import frc.robot.subsystems.Shooter.ShootOnetheMove;
import frc.robot.subsystems.Swerve.Swerve;
import frc.robot.subsystems.Swerve.TunerConstants;

public class RobotContainer {

    public static final Swerve drivetrain = TunerConstants.createDrivetrain();

    public static AutoAlign autoAlign;
    public static ShootOnetheMove shootOnetheMove;

    private static Optional<Alliance> alliance = null;

    Optional<Alliance> alliance = DriverStation.getAlliance();
    Boolean blue = alliance.get().equals(Alliance.Blue);

    public static Optional<Alliance> getCachedAlliance() {
    if (alliance == null) alliance = DriverStation.getAlliance();
    return alliance;
  }
}