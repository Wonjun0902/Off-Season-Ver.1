package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotation;

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
import static frc.robot.subsystems.Shooter.ShooterConstants.AngleLockConfig.*;

import frc.robot.DriverControls;

/**
 * A class that manages the bot's autoalign logic for shooting at any position
 */ 
public class AutoAlign {

    //Creates a new Swerve Request for AngleLock with PID values
    private static final SwerveRequest.FieldCentricFacingAngle angleLock = new SwerveRequest.FieldCentricFacingAngle()
        .withHeadingPID(ANGLELOCK_P, ANGLELOCK_I, ANGLELOCK_D) // Needs to be Tuned
        .withMaxAbsRotationalRate(MAX_ROT_ANGLELOCK)// Needs to be Tuned
    
    //Creates Allign Command 
    public Command align(Angle setPoint){
        //Creates Target
        Rotation2d target = new Rotation2d(setPoint);

        return drivetrain.applyRequest(() -> 
            angleLock
                .withTargetDirection(target)
                .withForwardPerspective(ForwardPerspectiveValue.OperatorPerspective)
                .withVelocityX(0)
                .withVelocityY(0)
        )
        .beforeStarting(() -> angleLock.HeadingController.reset());
    }

    public Command alignForPassing(){
        //Pass Angle = 180 degrees from your perpective     
        Rotation2d PassAngle = new Rotation2d(Math.PI);

        return drivetrain.applyRequest(
            angleLock
                .withTargetDirection(PassAngle)
                .withForwardPerspective(ForwardPerspectiveValue.OperatorPerspective)
                .withVelocityX(DriverControls.defaultDriveControls.)
        )
    }
}
