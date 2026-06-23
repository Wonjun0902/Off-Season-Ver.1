package frc.robot;

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Shooter.AutoAlign;
import frc.robot.subsystems.Shooter.ShootOnetheMove;
import frc.robot.subsystems.Swerve.Swerve;
import frc.robot.subsystems.Swerve.TunerConstants;

import frc.robot.subsystems.LimeLight.*;

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