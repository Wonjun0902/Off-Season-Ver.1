package frc.robot.subsystems.Shooter;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.DriverControls;
import static frc.robot.RobotContainer.drivetrain;
import static frc.robot.RobotContainer.getCachedAlliance;

import java.util.function.Supplier;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.ForwardPerspectiveValue;
import static frc.robot.subsystems.Shooter.ShooterConstants.AngleLockConfig.*;


/**
 * A class that manages the bot's autoalign logic for shooting at any position
 */ 
public class AutoAlign {

    //Creates a new Swerve Request for AngleLock with PID values
    private static final SwerveRequest.FieldCentricFacingAngle angleLock = new SwerveRequest.FieldCentricFacingAngle()
        .withHeadingPID(ANGLELOCK_P, ANGLELOCK_I, ANGLELOCK_D) // Needs to be Tuned
        .withMaxAbsRotationalRate(MAX_ROT_ANGLELOCK);// Needs to be Tuned
    
    //Creates Allign Command 
    public Command align(Angle setPoint){
        //Creates Target
        Rotation2d target = new Rotation2d(setPoint);

        return drivetrain.applyRequest(() -> {
            return angleLock
                .withTargetDirection(target)
                .withForwardPerspective(ForwardPerspectiveValue.OperatorPerspective)
                .withVelocityX(0)
                .withVelocityY(0);
        }
        );
    }

    public Command alignForPassing(Supplier<Double> vx, Supplier<Double> vy){
        //Pass Angle = 180 degrees from your perpective     
        Rotation2d PassAngle = new Rotation2d(Math.PI);
        return drivetrain.applyRequest(() -> {
            return angleLock
                .withTargetDirection(PassAngle)
                .withForwardPerspective(ForwardPerspectiveValue.OperatorPerspective)
                .withVelocityX(vx.get())
                .withVelocityY(vy.get());
            }
        );
    }

    public Command alignForPass(Supplier<Double> vx, Supplier<Double> vy){
        Rotation2d target = new Rotation2d(Math.PI);
        return drivetrain.applyRequest(() -> {
            return angleLock
                .withTargetDirection(target)
                .withForwardPerspective(ForwardPerspectiveValue.OperatorPerspective)
                .withVelocityX(vx.get())
                .withVelocityY(vy.get());
            });
    }

    public static Rotation2d getAlignAngle(){
        //Define the Angle of the Hub first
        Translation2d robotPos = drivetrain.getCachedState().Pose.getTranslation();
        //Get the Hub Pos and Get the Subtracted Vector
        Translation2d hubPos = getHubPos();
        //Subtract the hub from the robot to get the Rotation 
        Translation2d subtractedVector = hubPos.minus(robotPos);
        //Get the rotation2d of the subtracted Vector
        Rotation2d vectorAngle = subtractedVector.getAngle();

        return vectorAngle;
    }

    public static Translation2d getHubPos(){
        return getCachedAlliance().get().equals(Alliance.Blue) ? ShooterConstants.Field.BLUE_HUB_POSE : ShooterConstants.Field.RED_HUB_POSE;
    }

    public Command alignToHub(){
        return drivetrain.applyRequest(() -> {
        //Apply the request
        return angleLock
            .withTargetDirection(getAlignAngle())
            .withForwardPerspective(ForwardPerspectiveValue.BlueAlliance) 
            .withVelocityX(0)
            .withVelocityY(0);
    })
    .beforeStarting(() -> angleLock.HeadingController.reset())
    .withTimeout(1)
    .until(() -> {
        Rotation2d vectorAngle = getAlignAngle();
        Rotation2d currentAngle = drivetrain.getCachedState().Pose.getRotation();

        double error = vectorAngle.minus(currentAngle).getDegrees();

        return (Math.abs(error) <= 2);
    })
    .andThen(drivetrain.applyRequest(() -> DriverControls.brake));
    }
}
