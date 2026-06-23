package frc.robot;

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import java.util.Optional;

import com.ctre.phoenix6.swerve.jni.SwerveJNI.DriveState;

import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Deploy.Deploy;
import frc.robot.subsystems.Deploy.DeployIOReal;
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
import frc.robot.subsystems.Shooter.ShootOnetheMove;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterIOReal;
import frc.robot.subsystems.Shooter.ShooterIOSim;
import frc.robot.subsystems.StageManager.MrPatel;
import frc.robot.subsystems.Swerve.Swerve;
import frc.robot.subsystems.Swerve.Telemetry;
import frc.robot.subsystems.Swerve.TunerConstants;
import frc.robot.lib.Pathfinder;
import frc.robot.subsystems.LimeLight.LimeLight;
import frc.robot.lib.LimelightHelpers;

public class RobotContainer {

    public static MrPatel mrPatel;

    public static Intake intake;
    public static Deploy deploy;
    public static Shooter shooter;
    public static Indexer indexer;
    public static Throat throat;
    public static AutoAlign autoAlign;
    public static ShootOnetheMove shootOnetheMove;

    public static final Swerve drivetrain = TunerConstants.createDrivetrain();
    public static final Pathfinder PATHFINDER = new Pathfinder(drivetrain);

    public static final LimeLight limelightFRONT = new LimeLight("limelight-four", drivetrain);
    public static final LimeLight limelightBACK = new LimeLight("limelight-five", drivetrain);

    private final Telemetry logger = new Telemetry(TunerConstants.MaxSpeed);

    private SendableChooser<Command> autoSideSelector = new SendableChooser<>();
    private SendableChooser<Command> autoSelector = new SendableChooser<>();

    private static Optional<Alliance> alliance = Optional.empty();

    public static Boolean right = false;

    public RobotContainer() {

        if(RobotBase.isReal()){
            drivetrain.registerTelemetry(logger::telemetrize);
            intake = new Intake(new IntakeIOReal());
            deploy = new Deploy(new DeployIOReal());
            shooter = new Shooter(new ShooterIOReal());
            indexer = new Indexer(new IndexerIOReal());
            throat = new Throat(new ThroatIOReal());
            autoAlign = new AutoAlign();
            mrPatel = new MrPatel(deploy, indexer, intake, shooter, throat);
        }
        else{
            drivetrain.registerTelemetry(logger::telemetrize);
            intake = new Intake(new IntakeIOReal());
            deploy = new Deploy(new DeployIOReal());
            shooter = new Shooter(new ShooterIOReal());
            indexer = new Indexer(new IndexerIOReal());
            throat = new Throat(new ThroatIOReal());
            autoAlign = new AutoAlign();
            mrPatel = new MrPatel(deploy, indexer, intake, shooter, throat);
        }

        Optional<Alliance> alliance = DriverStation.getAlliance();
        Boolean blue = alliance.get().equals(Alliance.Blue);
    }

    public static Optional<Alliance> getCachedAlliance(){
        if(alliance == null) alliance = DriverStation.getAlliance();
        return alliance;
    }

    public static Command sideSelect(boolean r){
        return Commands.runOnce(() -> right = r);
    }

    /**
   * Start with mode 1 on the limelight, which uses MT1 for heading estimation
   * with a very low alpha.
   */
    public void limelightCalibration() {
    LimelightHelpers.SetIMUAssistAlpha(limelightFRONT.getLimelightName(), 0.001);
    LimelightHelpers.SetIMUMode(limelightFRONT.getLimelightName(), 1);
    LimelightHelpers.SetIMUAssistAlpha(limelightBACK.getLimelightName(), 0.001);
    LimelightHelpers.SetIMUMode(limelightBACK.getLimelightName(), 1);

    // note to self, instead of TV use trust.
    }

    public void setLimelightUseMegaTag2(boolean use) {
    limelightFRONT.setUseMegaTag2(use);
	  limelightBACK.setUseMegaTag2(use);
    }

    /**
    * Called during disabled periodic to let limelights attempt a full pose + heading
    * correction when confident. Also ensures limelights are in IMU mode 1.
    */
    public void disabledPeriodicVisionUpdate() {
    if (limelightFRONT.estimate != null && limelightFRONT.estimate.tagCount >= 2)
    {
      limelightFRONT.disabledUpdate();
    } else {
      limelightBACK.disabledUpdate();
    }
    }

    public void setLimelightIMUMode(String name, int mode) {
    LimelightHelpers.SetIMUMode(name, mode);
    }

    // private static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    //           .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
    //           .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    // private static final CommandXboxController DRIVER_CONTROLLER = new CommandXboxController(0);
    // private static final SlewRateLimiter slewRateX = new SlewRateLimiter(SLEW_RATE_TRANSLATION.magnitude());
    // private static final SlewRateLimiter slewRateY = new SlewRateLimiter(SLEW_RATE_TRANSLATION.magnitude());

    private void configureBindings() {
    // TestControls.configureKingstonBindings();
    // TestControls.configureLimelightBindings();
    DriverControls.ConfigureBindings();
    OperatorControls.ConfigureBindings();
    }

    public Command getAutonomousCommand() {
      return autoSelector.getSelected();
    }
}