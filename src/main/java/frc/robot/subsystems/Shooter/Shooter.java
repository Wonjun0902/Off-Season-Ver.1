package frc.robot.subsystems.Shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.RobotContainer.drivetrain;

import static frc.robot.subsystems.Shooter.ShooterConstants.*;

public class Shooter extends SubsystemBase{
    ShooterIO io;

    public Shooter(ShooterIO io){
        this.io = io;
    }

    
}
