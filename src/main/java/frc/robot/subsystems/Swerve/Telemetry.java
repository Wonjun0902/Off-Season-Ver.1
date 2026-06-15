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

    /**
     * Struct is a type that allows us to group multiple variables together in a single name 
     * Struct as a type can hold integers, floats, characters, or even other structs 
     */

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

    //Direction and length changing ligament for speed representation 
    private final MechanismLigament2d[] m_moduleSpeed = new MechanismLigament2d[]{
        m_moduleMechanism[0].getRoot("Root Speed", 0.5, 0.5).append(new MechanismLigament2d("Speed", 0.5, 0.0)),            
        m_moduleMechanism[1].getRoot("Root Speed", 0.5, 0.5).append(new MechanismLigament2d("Speed", 0.5, 0.0)),
        m_moduleMechanism[2].getRoot("Root Speed", 0.5, 0.5).append(new MechanismLigament2d("Speed", 0.5, 0.0)),
        m_moduleMechanism[3].getRoot("Root Speed", 0.5, 0.5).append(new MechanismLigament2d("Speed", 0.5, 0.0)),
    };

    //Direction and length Changing ligament for Direction Representation 
    private final MechanismLigament2d[] m_moduleDirections = new MechanismLigament2d[]{
        m_moduleMechanism[0].getRoot("Root Direction", 0.5, 0.5)
            .append(new MechanismLigament2d("Direction", 0.1, 0.0, 0, new Color8Bit(Color.kWhite))),
        m_moduleMechanism[1].getRoot("Root Direction", 0.5, 0.5)
            .append(new MechanismLigament2d("Direction", 0.1, 0.0, 0, new Color8Bit(Color.kWhite))),
        m_moduleMechanism[2].getRoot("Root Direction", 0.5, 0.5)
            .append(new MechanismLigament2d("Direction", 0.1, 0.0, 0, new Color8Bit(Color.kWhite))),
        m_moduleMechanism[3].getRoot("Root Direction", 0.5, 0.5)
            .append(new MechanismLigament2d("Direction", 0.1, 0.0, 0, new Color8Bit(Color.kWhite)))    
    };

    private final double[] m_poseArray = new double[3];
    private final double[] m_moduleStatesArray = new double[8];
    private final double[] m_moduleTargetsArray = new double[8];

    //Accept the swerve drive state and telemetrize it to SmartDashboard and Signal Logger
    public void telemetrize(SwerveDriveState state){
    //Telemetrize the swerve drive state
    drivePose.set(state.Pose);
    driveSpeeds.set(state.Speeds);
    driveModuleStates.set(state.ModuleStates);
    driveModuleTargets.set(state.ModuleTargets);
    driveModulePositions.set(state.ModulePositions);
    driveTimeStamp.set(state.Timestamp);
    driveOdometryFrequency.set(1.0 / state.OdometryPeriod);

    //Also write the log file//
    m_poseArray[0] = state.Pose.getX();  
    m_poseArray[1] = state.Pose.getY();
    m_poseArray[2] = state.Pose.getRotation().getDegrees();
    for(int i = 0; i < 4; ++i){
        m_moduleStatesArray[i * 2 + 0] = state.ModuleStates[i].angle.getRadians();
        m_moduleStatesArray[i * 2 + 1] = state.ModuleStates[i].speedMetersPerSecond;
        m_moduleTargetsArray[i * 2 + 0] = state.ModuleTargets[i].angle.getRadians();
        m_moduleTargetsArray[i * 2 + 1] = state.ModuleTargets[i].speedMetersPerSecond;
    }

    SignalLogger.writeDoubleArray("DriveState/Pose", m_poseArray);
    SignalLogger.writeDoubleArray("Drive Module Status", m_moduleStatesArray);
    SignalLogger.writeDoubleArray("Module Targets", m_moduleTargetsArray);
    SignalLogger.writeDouble("Odometry Frequency", 1/state.OdometryPeriod, "Seconds");

    //Telemetrize the Pose to field 2D
    fieldTypePub.set("Field 2D");
    fieldPub.set(m_poseArray);

    //Telemetrize each module state to Mechanism 2D
    for(int i = 0; i < 4; ++i){
        m_moduleSpeed[i].setAngle(state.ModuleStates[i].angle);
        m_moduleDirections[i].setAngle(state.ModuleStates[i].angle);
        m_moduleSpeed[i].setLength(state.ModuleStates[i].speedMetersPerSecond / (2 * MaxSpeed));
    }
    }
}
