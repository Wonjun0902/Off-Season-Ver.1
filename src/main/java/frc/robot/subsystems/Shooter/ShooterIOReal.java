package frc.robot.subsystems.Shooter;

import frc.robot.lib.LazyFXS;
import frc.robot.lib.LazyFXSBuilder;
import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;
import frc.robot.subsystems.Indexer.IndexerConstants.Configurations;
import frc.robot.subsystems.Shooter.ShooterConstants.*;
import frc.robot.subsystems.Shooter.ShooterConstants.Shooter;

import com.ctre.phoenix6.signals.ExternalFeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.MotorArrangementValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Seconds;
import static frc.robot.subsystems.Indexer.IndexerConstants.Configurations.CANBUS;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public class ShooterIOReal implements ShooterIO{

    private LazyTalon rightMotor, leftMotor;
    private LazyFXS hoodMotor;

    public ShooterIOReal(){
        leftMotor = new LazyTalonBuilder(
            Shooter.Left.MOTOR_ID,
            Configurations.CANBUS,
            ShooterConstants.Shooter.SENSOR_TO_MECH_RATIO,
            InvertedValue.Clockwise_Positive,
            Shooter.STATOR_CURRENT_LIMIT.magnitude(),
            Shooter.SUPPLY_CURRENT_LIMIT.magnitude(),
            Shooter.SUPPLY_LOWER_LIMIT.in(Amps),
            Shooter.SUPPLY_LOWER_TIME.in(Seconds)
        )
        .withPIDFConfiguration(Shooter.Left.kP, Shooter.Left.kI, Shooter.Left.kD, Shooter.Left.kS, Shooter.Left.kG, Shooter.Left.kV, Shooter.Left.kA, 
            GravityTypeValue.Elevator_Static, StaticFeedforwardSignValue.UseClosedLoopSign, 0)
        .withFollower(Shooter.Left.FOLLOWER_LEFT_ID, Configurations.CANBUS, MotorAlignmentValue.Aligned).build();
        leftMotor.setCoast(); 

        rightMotor = new LazyTalonBuilder(
            Shooter.Right.MOTOR_ID,
            Configurations.CANBUS,
            ShooterConstants.Shooter.SENSOR_TO_MECH_RATIO,
            InvertedValue.Clockwise_Positive,
            Shooter.STATOR_CURRENT_LIMIT.magnitude(),
            Shooter.SUPPLY_CURRENT_LIMIT.magnitude(),
            Shooter.SUPPLY_LOWER_LIMIT.in(Amps),
            Shooter.SUPPLY_LOWER_TIME.in(Seconds)
        )
        .withPIDFConfiguration(Shooter.Right.kP, Shooter.Right.kI, Shooter.Right.kD, Shooter.Right.kS, Shooter.Right.kG, Shooter.Right.kV, Shooter.Right.kA, 
            GravityTypeValue.Elevator_Static, StaticFeedforwardSignValue.UseClosedLoopSign, 0)
        .withFollower(Shooter.Right.FOLLOWER_RIGHT_ID, CANBUS, MotorAlignmentValue.Aligned)
        .build();
        rightMotor.setCoast();

        hoodMotor = new LazyFXSBuilder(
            Hood.MOTOR_ID,
            Configurations.CANBUS,
            MotorArrangementValue.NEO550_JST,
            Hood.SENSOR_MECH_RATIO,
            InvertedValue.Clockwise_Positive,
            Hood.STATOR_CURRENT_LIMIT.magnitude(),
            Hood.SUPPLY_CURRENT_LIMIT.magnitude()
        )
        .withRotorToSensorRatio(Hood.ROTOR_SENSOR_RATIO)
        .withSoftLimits(true, Hood.SOFT_HI.magnitude(), true, Hood.SOFT_LO.magnitude())
        .withCANCoder(Hood.CANCODER_ID, CANBUS, ExternalFeedbackSensorSourceValue.RemoteCANcoder, Hood.SENSOR_OFFSET, Hood.SENSOR_DIRECTION)
        .build();
    }

    @Override
    public void setShooterSpeed(AngularVelocity velocity){
        rightMotor.setTFOCVelocityTarget(velocity, Shooter.Right.CURRENT_FF, 0);
        leftMotor.setTFOCVelocityTarget(velocity, Shooter.Left.CURRENT_FF, 0);
    }

    @Override
    public void setHoodAngle(Angle angle){
        hoodMotor.setMMPositionTarget(angle, 0);
    }

    @Override
    public void stop(){
        rightMotor.stop();
        leftMotor.stop();
        hoodMotor.stop();
    }

    @Override
    public AngularVelocity getLeftSpeed(){
        return leftMotor.getVelocity();
    }

    @Override
    public AngularVelocity getRightSpeed(){
        return rightMotor.getVelocity();
    }

    @Override
    public Angle getHoodAngle(){
        return hoodMotor.getPosition();
    }

    @Override
    public void periodic(){}

}
