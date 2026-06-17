package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.DriverControls;
import frc.robot.subsystems.Swerve.TunerConstants;

import static frc.robot.RobotContainer.drivetrain;
import static frc.robot.RobotContainer.getCachedAlliance;

import java.util.function.Supplier;


import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.ForwardPerspectiveValue;

/**
 * A class that manages the bot's autoalign logic for shooting at any position
 * */ 
public class AutoAlign {

}
