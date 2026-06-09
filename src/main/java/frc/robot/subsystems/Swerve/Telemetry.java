package frc.robot.subsystems.Swerve;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class Telemetry {

    public final double MaxSpeed;

    /**
     * Constructs a telemetry object, with a specified max speed of the bot
     * 
     * @param maxSpeed Maximum speed of the bot in meters per second
     * */ 
    public Telemetry(double maxSpeed){
        MaxSpeed = maxSpeed;
        SignalLogger.start();

        for(int i = 0; i < 4; ++i){
            SmartDashboard.putData("Module"+i, m_moduleMechanism[i]);
        }
    }

    //What to publish over network tables for telemetry 
    private final NetworkTableInstance inst = NetworkTableInstance.getDefault();

    //Robot Swerve DriveState
    private final NetworkTable driveStateTable = inst.getTable("DriveState Table");
    private final StructPublisher<Pose2d> drivePose = driveStateTable.getStructTopic("Pose", Pose2d.struct).publish();
    private final StructPublisher<ChassisSpeeds> driveSpeeds = driveStateTable.getStructTopic("Speeds", ChassisSpeeds.struct).publish();
    private final StructArrayPublisher<SwerveModuleState> driveModuleStates = driveStateTable
        .getStructArrayTopic("Module State", SwerveModuleState.struct).publish();
    private final StructArrayPublisher<SwerveModuleState> driveModuleTargets = driveStateTable
        .getStructArrayTopic("Module Target", SwerveModuleState.struct).publish();
    private final StructArrayPublisher<SwerveModulePosition> driveModulePositions = driveStateTable
        .getStructArrayTopic("Swerve Positions", SwerveModulePosition.struct).publish();
    private final DoublePublisher driveTimeStamp = driveStateTable.getDoubleTopic("Time Stamp").publish();
    private final DoublePublisher driveOdometryFrequency = driveStateTable.getDoubleTopic("Odometry Frequency").publish();

    //Robot Pose for field positioning 
    private final NetworkTable table = inst.getTable("Pose");
    private final DoubleArrayPublisher fieldPub = table.getDoubleArrayTopic("Robot Pose").publish();
    private final StringPublisher fieldTypePub = table.getStringTopic(".Type").publish();

    //Mechanisms to represent the swerve module states 
    private final Mechanism2d[] m_moduleMechanism = new Mechanism2d[]{
        new Mechanism2d(1, 1),
        new Mechanism2d(1,1),
        new Mechanism2d(1, 1),
        new Mechanism2d(1,1),
    };
}
