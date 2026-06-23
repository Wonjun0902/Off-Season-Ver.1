package frc.robot;

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import java.util.Optional;

import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.Indexer.IndexerIOReal;
import frc.robot.subsystems.Indexer.IndexerIOSim;
import frc.robot.subsystems.Throat.Throat;
import frc.robot.subsystems.Throat.ThroatIOReal;
import frc.robot.subsystems.Throat.ThroatIOSim;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeIOReal;
import frc.robot.subsystems.Intake.IntakeIOSim;
import frc.robot.subsystems.Shooter.AutoAlign;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterIOReal;
import frc.robot.subsystems.Shooter.ShooterIOSim;
import frc.robot.subsystems.StageManager.MrPatel;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.subsystems.swerve.Telemetry;
import frc.robot.subsystems.swerve.TunerConstants;
import frc.lib.Pathfinder;
import frc.robot.subsystems.limelight.Limelight;
import frc.lib.LimelightHelpers;

public class RobotContainer {

    public static final Swerve drivetrain = TunerConstants.createDrivetrain();

    public static AutoAlign autoAlign;
    public static ShootOnetheMove shootOnetheMove;

    // 1. Keep only one static alliance variable
    private static Optional<Alliance> alliance = Optional.empty();
    
    // 2. Moved the 'blue' boolean calculation logic or removed it from the raw class body

    // 3. Added the missing closing brace '}' at the end of this method
    public static Optional<Alliance> getCachedAlliance() {
        if (alliance.isEmpty()) { 
            alliance = DriverStation.getAlliance();
        }
        return alliance;
    } // <-- This brace was missing!

    // Now these static declarations are perfectly legal because they are directly in the class body
    public static final LimeLight limelightFRONT = new LimeLight("limelight-four", drivetrain);
    public static final LimeLight limelightBACK = new LimeLight("limelight-five", drivetrain);

    public RobotContainer() {
        // Handle your initialization or button bindings here
    }
}