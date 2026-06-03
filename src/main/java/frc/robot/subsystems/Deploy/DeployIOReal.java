package frc.robot.subsystems.Deploy;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.*;
import frc.robot.subsystems.Deploy.DeployConstants.Configurations.Deployer;
import frc.robot.subsystems.Deploy.DeployConstants.Configurations.CanCoder;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.configs.FovParamsConfigs;
import com.ctre.phoenix6.configs.ProximityParamsConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;

public class DeployIOReal {

    public LazyTalon deployMotor;
    public CANcoder deployEncoder;

    public DeployIOReal(){

        deployMotor = new LazyTalonBuilder(
            Deployer.MOTOR_ID, 
            CANBUS,
            Deployer.GEAR_RATIO,
            InvertedValue.CounterClockwise_Positive,
            Deployer.STATOR_LIMIT.in(Amps),
            Deployer.SUPPLY_LIMIT.in(Amps)
        )
        .withPIDFConfiguration(
            Deployer.kP, 
            Deployer.kI, 
            Deployer.kD, 
            Deployer.kS, 
            Deployer.kG, 
            Deployer.kV, 
            Deployer.kA, 
            GravityTypeValue.Arm_Cosine, 
            StaticFeedforwardSignValue.UseClosedLoopSign, 
            0)
        .withMotionMagicConfiguration(
            Deployer.EXPO_V, 
            Deployer.EXPO_A, 
            Deployer.CRUISE_VELOCITY, 
            Deployer.MAX_ACCELERATION
        )
        .withCANCoder(
            CANCODER_ID, CANBUS, null, 0, null)
        .build();

        deployEncoder = new CANcoder(0, CANBUS);

        CANcoderConfiguration config = new CANcoderConfiguration()
            .withCustomParams(null)
            .withAbsoluteSensorRange(CANcoderConfiguration.AbsoluteSensorRange.Signed_PlusMinus180);
        deployEncoder.getConfigurator().apply(config);
    }

}
