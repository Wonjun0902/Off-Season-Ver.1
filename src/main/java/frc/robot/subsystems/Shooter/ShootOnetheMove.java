package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotation;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.DriverControls;
import frc.robot.subsystems.Swerve.TunerConstants;
import static frc.robot.RobotContainer.drivetrain;
import static frc.robot.RobotContainer.getCachedAlliance;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.ForwardPerspectiveValue;
import static frc.robot.subsystems.Shooter.ShooterConstants.AngleLockConfig.*;

import static frc.robot.RobotContainer.autoAlign;

public class ShootOnetheMove {

/**
 * Plans to have TWO Methods for Shoot on the move
 * 
 * One is measuring the current speed of the bot and calculating the specific angle that has to be turned. 
 * The other is measuring the current speed of the bot and calculating the future Pose that the bot that will shoot the ball 
 */

//FIST LOGIC Calculating the Speed & Turn accordingly

//Calculate the Pose
public static Translation2d getCurrentPose(){
    Translation2d currentPose = drivetrain.getCachedState().Pose.getTranslation();

    return currentPose;
}

//Calculate the Angle
public static Rotation2d getCurrentAngle(){
    Translation2d currentPose = getCurrentPose();
    Rotation2d currentAngle = currentPose.getAngle();

    return currentAngle;
}

public static Translation2d getHubPose(){
    Translation2d hubPose = getCachedAlliance().get().equals(Alliance.Blue) ? ShooterConstants.Field.BLUE_HUB_POSE : ShooterConstants.Field.RED_HUB_POSE;

    return hubPose;
}

//Calculate the Vector to Hub
public static Translation2d getVectorToHub(){
    Translation2d currentPose = getCurrentPose();
    Translation2d hubPose = getHubPose();

    Translation2d vectorToHub = hubPose.minus(currentPose);

    return vectorToHub;
}

public static Rotation2d getAngleToHub(){
    Translation2d vectorToHub = getVectorToHub();

    Rotation2d angleToHub = vectorToHub.getAngle();

    return angleToHub;
}

public static Translation2d getBotSpeed(){
    Double xSpeed = drivetrain.getCachedState().Speeds.vxMetersPerSecond;
    Double ySpeed = drivetrain.getCachedState().Speeds.vyMetersPerSecond;

    return new Translation2d(xSpeed, ySpeed);
}




}
