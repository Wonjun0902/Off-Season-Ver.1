package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.Intake.IntakeConstants.SUPPLY_LIMIT;
import static frc.robot.subsystems.Intake.IntakeConstants.Configurations.TopRoller.*;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;
import frc.robot.subsystems.Intake.IntakeConstants.Configurations.BottomRoller;
import frc.robot.subsystems.Intake.IntakeConstants.Configurations.TopRoller;

public class IntakeIOReal implements IntakeIO{

    public LazyTalon topRoller;
    public LazyTalon bottomRoller;

    public IntakeIOReal(){
        topRoller = new LazyTalonBuilder(
            IntakeConstants.Configurations.TopRoller.MOTOR_ID, 
            IntakeConstants.CANBUS,
            IntakeConstants.Configurations.TopRoller.SENSOR_TO_MECH_RATIO, 
            InvertedValue.Clockwise_Positive, 
            IntakeConstants.STATOR_LIMIT.in(Amps), SUPPLY_LIMIT.in(Amps))
            .withPIDFConfiguration(
                TopRoller.kP, 
                TopRoller.kI, 
                TopRoller.kD, 
                TopRoller.kS, 
                TopRoller.kG, 
                TopRoller.kV, 
                TopRoller.kA, 
                null, //Cause the intake itself is not effected by gravity as it just spins 
                StaticFeedforwardSignValue.UseVelocitySign, 0)
            .build();
            topRoller.setCoast();
        bottomRoller = new LazyTalonBuilder(
            IntakeConstants.Configurations.BottomRoller.MOTOR_ID, 
            IntakeConstants.CANBUS,
            IntakeConstants.Configurations.BottomRoller.SENSOR_TO_MECH_RATIO, 
            InvertedValue.Clockwise_Positive, 
            IntakeConstants.STATOR_LIMIT.in(Amps), SUPPLY_LIMIT.in(Amps))
            .withPIDFConfiguration(
                BottomRoller.kP, 
                BottomRoller.kI, 
                BottomRoller.kD, 
                BottomRoller.kS, 
                BottomRoller.kG, 
                BottomRoller.kV, 
                BottomRoller.kA, 
                null, //Cause the intake itself is not effected by gravity as it just spins 
                StaticFeedforwardSignValue.UseVelocitySign, 0)
            .build();
            bottomRoller.setCoast();
    }

    @Override
    public void spinTopRoller(AngularVelocity speed){
        topRoller.setMMVelocityTarget(speed, MOTOR_ID);
    }

    @Override
    public void spinBottomRoller(AngularVelocity speed){
        bottomRoller.setMMVelocityTarget(speed, MOTOR_ID);
    }

    @Override
    public AngularVelocity getTopSpeed(){
        return topRoller.getVelocity();
    }

    @Override
    public AngularVelocity getBottomSpeed(){
        return bottomRoller.getVelocity();
    }

    @Override
    public void Stop(){
        topRoller.stop();
        bottomRoller.stop();
    }

    @Override
    public void runDutyCycleTop(double dutyCycle){
        topRoller.setDutyCycle(dutyCycle);
    }

    @Override
    public void runDutyCycleBottom(double dutyCycle){
        bottomRoller.setDutyCycle(dutyCycle);
    }

    @Override
    public Current getTopCurrent(){
        return topRoller.getMotor().getStatorCurrent().getValue();
    }

    @Override
    public Current getBottomCurrent(){
        return bottomRoller.getMotor().getStatorCurrent().getValue();
    }

    @Override
    public void periodic(){}
}
