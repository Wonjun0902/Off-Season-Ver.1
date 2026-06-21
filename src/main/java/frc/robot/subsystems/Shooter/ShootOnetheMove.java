package frc.robot.subsystems.Shooter;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import static frc.robot.RobotContainer.drivetrain;
import static frc.robot.RobotContainer.getCachedAlliance;

import java.util.function.Supplier;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentricFacingAngle;
import com.ctre.phoenix6.swerve.SwerveRequest.ForwardPerspectiveValue;
import static frc.robot.subsystems.Shooter.ShooterConstants.AngleLockConfig.*;

import static frc.robot.subsystems.Shooter.ShooterConstants.LookupTables.DIST_TIME_MAP;

public class ShootOnetheMove {

//Calculate the Pose
public static Translation2d getCurrentPose(){
    Translation2d currentPose = drivetrain.getCachedState().Pose.getTranslation();
    return currentPose;
}

//Get the Hub Pose
public static Translation2d getHubPose(){
    Translation2d hubPose = getCachedAlliance().get().equals(Alliance.Blue) ? ShooterConstants.Field.BLUE_HUB_POSE : ShooterConstants.Field.RED_HUB_POSE;
    return hubPose;
}

public static Rotation2d getAngle(){
    Rotation2d currentAngle = drivetrain.getCachedState().Pose.getRotation();
    return currentAngle;
}

//Calculate the Vector to Hub
public static Translation2d getVectorToHub(){
    Translation2d currentPose = getCurrentPose();
    Translation2d hubPose = getHubPose();
    Translation2d vectorToHub = hubPose.minus(currentPose);
    return vectorToHub;
}

//Get the Angle To Hub
public static Rotation2d getAngleToHub(){
    Translation2d vectorToHub = getVectorToHub();
    Rotation2d angleToHub = vectorToHub.getAngle();
    return angleToHub;
}

public static Double getXSpeed(){
    Double xSpeed = drivetrain.getCachedState().Speeds.vxMetersPerSecond;
    return xSpeed;
}

public static Double getYSpeed(){
    Double ySpeed = drivetrain.getCachedState().Speeds.vyMetersPerSecond;
    return ySpeed;
}

public static Double getDistanceFromHub(){
    Translation2d vectorToHub = getVectorToHub();
    double distanceToHub = vectorToHub.getNorm();
    return distanceToHub;
}

public static Double getTimeOfFuel(){
    Double dist = getDistanceFromHub();
    Double timeofFuel = DIST_TIME_MAP.get(dist);
    return timeofFuel;
}

public static Translation2d getAccordingPose(){
    //Get the current Pose
    Translation2d current = getCurrentPose();

    //Get the time, speed and the distance
    double timeOfFuel = getTimeOfFuel();
    double xVelocity = getXSpeed();
    double yVelocity = getYSpeed();

    //Get the additional Trans2d
    Translation2d addingTranslation2d = new Translation2d(xVelocity, yVelocity).times(timeOfFuel);
    
    //Get the according Pose
    Translation2d accordingPose = current.plus(addingTranslation2d);

    return accordingPose;
}

public static Rotation2d getAlginAngle(){
    //Define the according bot pose 
    Translation2d accordingPose = getAccordingPose();
    //Get the Hub Pose
    Translation2d hubPose = getHubPose();
    //Get the subtracted Vector
    Translation2d subtractedVector = hubPose.minus(accordingPose);
    //Get the Roation2d of the subtracted Vector
    Rotation2d vectorRotation2d = subtractedVector.getAngle();

    return vectorRotation2d;
}

//Creates a new Swerve Request for AngleLock with PID values
private static final SwerveRequest.FieldCentricFacingAngle angleLock = new FieldCentricFacingAngle()
    .withHeadingPID(ANGLELOCK_P, ANGLELOCK_I, ANGLELOCK_D)
    .withMaxAbsRotationalRate(MAX_ROT_ANGLELOCK);

public Command ShootOnMove(Supplier<Double> vx, Supplier<Double> vy){

    return drivetrain.applyRequest(
        () -> {
            Rotation2d target = getAlginAngle();

            return angleLock
                .withTargetDirection(target)
                .withForwardPerspective(ForwardPerspectiveValue.OperatorPerspective)
                .withVelocityX(vx.get()) //Add to driver controls later 
                .withVelocityY(vy.get());
        }
    )
    .beforeStarting(() -> angleLock.HeadingController.reset());
}
}
