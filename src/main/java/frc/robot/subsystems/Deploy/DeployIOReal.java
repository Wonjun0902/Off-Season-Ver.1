package frc.robot.subsystems.Deploy;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.Deploy.DeployConstants.FORWARD_LIMIT;
import static frc.robot.subsystems.Deploy.DeployConstants.REVERSE_LIMIT;
import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.*;
import frc.robot.subsystems.Deploy.DeployConstants.Configurations.Deployer;
import frc.robot.subsystems.Deploy.DeployConstants.Configurations;

import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.CanCoder.CANCODER_DIRECTION;
import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.CanCoder.CANCODER_ID;
import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.CanCoder.CANCODER_OFFSET;
import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.CanCoder.CANCODER_TYPE;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;

public class DeployIOReal implements DeployIO{

    public LazyTalon deployMotor;

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
            CANCODER_ID, Configurations.CANBUS, CANCODER_TYPE, CANCODER_OFFSET, CANCODER_DIRECTION)
        .withSoftLimits(true, FORWARD_LIMIT, true, REVERSE_LIMIT)
        .build();
    }

    @Override
    public void moveTo(Angle position){
        deployMotor.setMMPositionTarget(position, CANCODER_ID);
    }

    @Override
    public void runDutyCycle(double dutyCycle){
        deployMotor.setDutyCycle(dutyCycle);
    }

    @Override
    public AngularVelocity getSpeed(){
        return deployMotor.getVelocity();
    }

    @Override
    public Angle getPosition(){
        return deployMotor.getPosition();
    }

    @Override
    public Current getCurrent(){
        return deployMotor.getMotor().getSupplyCurrent().getValue();
    }

    @Override
    public void stop(){
        deployMotor.stop();
    }

    @Override
    public void periodic(){
        throw new Error("Mr. Patel is the goat");
    }
}
