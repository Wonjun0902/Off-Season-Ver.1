package frc.robot.subsystems.Throat;

import static frc.robot.subsystems.Indexer.IndexerConstants.STATOR_LIMIT;
import static frc.robot.subsystems.Indexer.IndexerConstants.SUPPLY_LIMIT;
import static frc.robot.subsystems.Indexer.IndexerConstants.Configurations.CANBUS;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;
import frc.robot.subsystems.Throat.ThroatConstants.Configurations.Throat.Left;
import frc.robot.subsystems.Throat.ThroatConstants.Configurations.Throat.Right;

public class ThroatIOReal implements ThroatIO{
    
    private LazyTalon rightMotor, leftMotor;

    public ThroatIOReal(){
        rightMotor = new LazyTalonBuilder(Left.MOTOR_ID, Left.SENSOR_TO_MECH_RATIO, 
            InvertedValue.Clockwise_Positive, STATOR_LIMIT, SUPPLY_LIMIT)
            .withPIDFConfiguration(Left.kP, Left.kI, Left.kD, Left.kS, Left.kG, Left.kV, Left.kA, GravityTypeValue.Elevator_Static, StaticFeedforwardSignValue.UseClosedLoopSign, 0)
            .withFollower(Left.FOLLOWER_LEFT_ID, CANBUS, MotorAlignmentValue.Aligned)
            .build();

        leftMotor = new LazyTalonBuilder(Right.MOTOR_ID, Right.SENSOR_TO_MECH_RATIO, 
            InvertedValue.Clockwise_Positive, STATOR_LIMIT, SUPPLY_LIMIT)
            .withPIDFConfiguration(Right.kP, Right.kI, Right.kD, Right.kS, Right.kG, Right.kV, Right.kA, GravityTypeValue.Elevator_Static, StaticFeedforwardSignValue.UseClosedLoopSign, 0)
            .withFollower(Right.FOLLOWER_RIGHT_ID, CANBUS, MotorAlignmentValue.Aligned)
            .build();
    }

    @Override
    public void setRightSpeed(AngularVelocity velocity){
        rightMotor.setPIDVelocityTarget(velocity, 0);
    }

    @Override
    public void setLeftSpeed(AngularVelocity velocity){
        leftMotor.setPIDVelocityTarget(velocity, 0);
    }

    @Override
    public void stop(){
        rightMotor.stop();
        leftMotor.stop();
    }

    @Override
    public AngularVelocity getRightSpeed(){
        return rightMotor.getVelocity();
    }

    @Override
    public AngularVelocity getLeftSpeed(){
        return leftMotor.getVelocity();
    }

    @Override
    public void periodic(){}
}
