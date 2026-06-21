package frc.robot.subsystems.Shooter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Shooter.ShooterConstants.LookupTables;
import frc.robot.subsystems.Shooter.AutoAlign;
import frc.robot.subsystems.Shooter.ShootOnetheMove;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.RobotContainer.drivetrain;

import static frc.robot.subsystems.Shooter.ShooterConstants.*;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.HOOD_BACK_ANGLE;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.HOOD_HUB_ANGLE;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.HOOD_LOOKUP_TABLE;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.HOOD_TOWER_ANGLE;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.HOOD_TRENCH_ANGLE;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.SHOOTER_BACK_SPEED;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.SHOOTER_HUB_SPEED;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.SHOOTER_LOOKUP_TABLE;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.SHOOTER_TOWER_SPEED;
import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.SHOOTER_TRENCH_SPEED;

public class Shooter extends SubsystemBase{

    public Pose2d currentPose = drivetrain.getCachedState().Pose;
    public Translation2d current = drivetrain.getCachedState().Pose.getTranslation();
    public Angle offSetAngle = Rotations.of(0.0);
    public AngularVelocity offSetVelocity = RotationsPerSecond.of(0.0);

    private ShooterIO io;

    public Shooter(ShooterIO io){
        this.io = io;
    }

    public void updatePose2d(Pose2d currentPose2d){
        currentPose = currentPose2d;
    }

    // Setting the Hood Angle from the LookUpTables
    public Angle setHoodAngle(){
        double dist = AutoAlign.getHubPos().getDistance(current);
        return Rotations.of(HOOD_LOOKUP_TABLE.get(dist));
    }

    //Setting the ShooterSpeed from the LookUpTables
    public AngularVelocity setShooterSpeed(){
        double dist = AutoAlign.getHubPos().getDistance(current);
        return RotationsPerSecond.of(SHOOTER_LOOKUP_TABLE.get(dist));
    }

    //Default Shooting, nothing special 
    public Command Shoot(AngularVelocity shooterSpeed, Angle hoodAngle){
        return runEnd( () -> {
            io.setHoodAngle(hoodAngle.plus(offSetAngle));
            io.setShooterSpeed(shooterSpeed.plus(offSetVelocity));
        }, () -> {
            io.stop();
        });
    }

    public Command ShootfromEveryWhere(){
        double dist = AutoAlign.getHubPos().getDistance(current);
        return Shoot(
            RotationsPerSecond.of(SHOOTER_LOOKUP_TABLE.get(dist)), 
            Rotations.of(HOOD_LOOKUP_TABLE.get(dist)));
    }

    public Command hubShot(){
        return Shoot(SHOOTER_HUB_SPEED, HOOD_HUB_ANGLE);
    }

    public Command towerShot(){
        return Shoot(SHOOTER_TOWER_SPEED, HOOD_TOWER_ANGLE);
    }

    public Command trenchShot(){
        return Shoot(SHOOTER_TRENCH_SPEED, HOOD_TRENCH_ANGLE);
    }

    public Command backShot(){
        return Shoot(SHOOTER_BACK_SPEED, HOOD_BACK_ANGLE);
    }
    
    public Runnable trimHoodUp(){
        return () -> offSetAngle = offSetAngle.plus(TIRM_INCREMENT_ANGLE);
    }

    public Runnable trimHoodDown(){
        return () -> offSetAngle = offSetAngle.minus(TIRM_INCREMENT_ANGLE);
    }

    public Runnable trimSpeedUp(){
        return () -> offSetVelocity = offSetVelocity.plus(TRIM_INCREMENT_SPEED);
    }
    
    public Runnable trimSpeedDown(){
        return () -> offSetVelocity = offSetVelocity.minus(TRIM_INCREMENT_SPEED);
    }

    public Runnable resetTrims(){
        return () -> {
            offSetAngle = Rotations.of(0.0);
            offSetVelocity = RotationsPerSecond.of(0.0);
        };
    }

    public Command stop(){
        return run(() -> io.stop());
    }

    @Override
    public void periodic(){
        updatePose2d(drivetrain.getCachedState().Pose);

        SmartDashboard.putNumber("Right Shooter Speed (rps): ", io.getRightSpeed().in(RotationsPerSecond));
        SmartDashboard.putNumber("Left Shooter Speed (rps): ", io.getLeftSpeed().in(RotationsPerSecond));
        SmartDashboard.putNumber("Hood Angle (rot): ", io.getHoodAngle().in(Rotations));

        SmartDashboard.putNumber("Hood Offset (rot): ", offSetAngle.in(Rotations));
        SmartDashboard.putNumber("Shooter Offset (rps): " , offSetVelocity.in(RotationsPerSecond));

        SmartDashboard.putNumber("Bot to Hub Distance (meters): ", AutoAlign.getHubPos().getDistance(current));
        SmartDashboard.putNumber("Current Bot Angle (degrees): ", drivetrain.getCachedState().Pose.getRotation().getDegrees());
    }



}

