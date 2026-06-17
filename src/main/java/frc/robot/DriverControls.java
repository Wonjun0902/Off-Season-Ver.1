package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Swerve.TunerConstants;

public class DriverControls {

    public static final CommandXboxController DRIVER_CONTROLLER = new CommandXboxController(0);

    public static final SlewRateLimiter slewRateX = new SlewRateLimiter(0.0);// JUST A PLACEHOLDER VALUE!!!
	public static final SlewRateLimiter slewRateY = new SlewRateLimiter(0.0);// JUST A PLACEHOLDER VALUE!!!
	/* Setting up bindings for necessary control of the swerve drive platform */
	public static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
			.withDeadband(0.0).withRotationalDeadband(0.0) // JUST A PLACEHOLDER VALUE!!!
			.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
	public static final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

	public static final SwerveRequest.FieldCentricFacingAngle testDrive = new SwerveRequest.FieldCentricFacingAngle() 
			.withDeadband(0.0).withRotationalDeadband(0.0)// JUST A PLACEHOLDER VALUE!!!
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

    public static AngularVelocity GetXVelocity(){
        return defaultDriveControls(999).apply(null, null)
    }

	private static Rotation2d m_desiredHeading = new Rotation2d();

	public static final SwerveRequest testDefaultDriveControls(double maxSpeed) {
		double thetaInput = -DRIVER_CONTROLLER.getRightX();

		if (Math.abs(thetaInput) > 0.1) {
			double angleChange = thetaInput * TunerConstants.MaxAngularRate * TunerConstants.rotationalSpeedLimiter * 0.02;
			m_desiredHeading = m_desiredHeading.plus(Rotation2d.fromRadians(angleChange));
		}

    }
}

