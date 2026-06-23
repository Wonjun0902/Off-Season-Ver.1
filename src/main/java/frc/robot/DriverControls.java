package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import static frc.robot.RobotContainer.autoAlign;
import static frc.robot.RobotContainer.drivetrain;
import static frc.robot.RobotContainer.mrPatel;
import static frc.robot.RobotContainer.shootOnetheMove;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Shooter.AutoAlign;
import frc.robot.subsystems.Swerve.TunerConstants;

import static frc.robot.subsystems.Swerve.TunerConstants.*;

public class DriverControls {

    public static final CommandXboxController DRIVER_CONTROLLER = new CommandXboxController(0);

    public static final SlewRateLimiter slewRateX = new SlewRateLimiter(SLEW_RATE_TRANSLATION.magnitude());// JUST A PLACEHOLDER VALUE!!!
	public static final SlewRateLimiter slewRateY = new SlewRateLimiter(SLEW_RATE_TRANSLATION.magnitude());// JUST A PLACEHOLDER VALUE!!!
	/* Setting up bindings for necessary control of the swerve drive platform */
	public static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
			.withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
			.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	public static final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

	public static final SwerveRequest.FieldCentricFacingAngle testDrive = new SwerveRequest.FieldCentricFacingAngle() 
			.withDeadband(MaxSpeed*0.1).withRotationalDeadband(MaxAngularRate*0.1)// JUST A PLACEHOLDER VALUE!!!
			.withDriveRequestType(DriveRequestType.OpenLoopVoltage) // Use open-loop control for drive motors
			.withHeadingPID(5.0, 0, 0.0);
			
	// private static final SwerveRequest.PointWheelsAt point = new
	// SwerveRequest.PointWheelsAt();
	// private static final SwerveRequest.FieldCentricFacingAngle angleLock = new
	// SwerveRequest.FieldCentricFacingAngle()
	// .withHeadingPID(3.0, 0.0, 0.1);speedLimiter

	/**
	 * Default drive controls
	 * 
	 * @param maxSpeed value from 0 to 1
	 * @return
	 */
	public static final SwerveRequest defaultDriveControls(double maxSpeed) {
		return drive
				.withVelocityX(slewRateX.calculate(-DRIVER_CONTROLLER.getLeftY()
						* TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)
						* maxSpeed))
				.withVelocityY(slewRateY.calculate(-DRIVER_CONTROLLER.getLeftX()
						* TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)
						* maxSpeed))
				.withRotationalRate(-DRIVER_CONTROLLER.getRightX()
						* TunerConstants.MaxAngularRate
						* TunerConstants.rotationalSpeedLimiter);
	}

	// public static final double getXVelocity(){
	// 	double xVelocity = -DRIVER_CONTROLLER.getLeftY();
	// 	return xVelocity;
	// }

	//Remeber the method .calculate!!!
	//Output Speed = JoyStick Percentage * Max Possible Speed * Safety Limiter
	public static void ConfigureBindings(){

		SmartDashboard.putString("Drive Mode", "Normal");

		final LinearVelocity MaxSpeed12V = MetersPerSecond.of(0.0);

		final double speedLimiter = 0.0;

		//PASS
		DRIVER_CONTROLLER.a().whileTrue(
			autoAlign.alignForPassing(
				() -> slewRateX.calculate(-DRIVER_CONTROLLER.getLeftY()
						*MaxSpeed12V.in(MetersPerSecond)
						*speedLimiter),
				() -> slewRateY.calculate(-DRIVER_CONTROLLER.getLeftX()
						*MaxSpeed12V.in(MetersPerSecond)
						*speedLimiter)
			).alongWith(
				mrPatel.shootFromEverywhere()
			)
		);

		//SHOOT ON MOVE
		DRIVER_CONTROLLER.b().whileTrue(
			shootOnetheMove.lockForShootOnMove(
				() -> slewRateX.calculate(-DRIVER_CONTROLLER.getLeftY()
						*MaxSpeed12V.in(MetersPerSecond)
						*speedLimiter),
				() -> slewRateY.calculate(-DRIVER_CONTROLLER.getLeftX()
						*MaxSpeed12V.in(MetersPerSecond)
						*speedLimiter)
			).alongWith(mrPatel.shootFromEverywhere())
		);

		//OVERDRIVE!!!!!!!!!
		DRIVER_CONTROLLER.y().whileTrue(
			Commands.runOnce(() -> SmartDashboard.putString("Drive mode: ", "OVERDRIVE"))
				.alongWith(drivetrain.applyRequest(() -> defaultDriveControls(overdriveSpeedLimiter)))
		);
	}
}

