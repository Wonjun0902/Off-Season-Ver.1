package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.Indexer.IndexerConstants.Configurations.*;
import frc.robot.subsystems.Indexer.IndexerConstants.Configurations.*;
import frc.robot.subsystems.Indexer.IndexerConstants.Configurations.Canranges;

import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.configs.FovParamsConfigs;
import com.ctre.phoenix6.configs.ProximityParamsConfigs;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;

public class IndexerIOReal implements IndexerIO{

    public LazyTalon indexerLeft;
    public LazyTalon indexerRight;
    public CANrange leftCanrange, rightCanrange;
    
    public IndexerIOReal(){
        indexerLeft = new LazyTalonBuilder(
            Spindexer.Left.MOTOR_ID, 
            CANBUS,
            Spindexer.Left.SENSOR_TO_MECH_RATIO, 
            InvertedValue.CounterClockwise_Positive, 
            Spindexer.Left.STATOR_LIMIT.in(Amps),
            Spindexer.Left.SUPPLY_LIMIT.in(Amps))
            .withPIDFConfiguration(
                Spindexer.Left.kP,
                Spindexer.Left.kI,
                Spindexer.Left.kD,
                Spindexer.Left.kS,
                Spindexer.Left.kG,
                Spindexer.Left.kV,
                Spindexer.Left.kA,
                GravityTypeValue.Elevator_Static,
                StaticFeedforwardSignValue.UseClosedLoopSign,
                0)
            .build(); 
        indexerLeft.setCoast(); // Remember this was set to Coast mode, not brake mode, 
                                //if break mode it will try to stop the motor when it is told ot

        indexerRight = new LazyTalonBuilder(
            Spindexer.Right.MOTOR_ID, 
            CANBUS,
            Spindexer.Right.SENSOR_TO_MECH_RATIO, 
            InvertedValue.CounterClockwise_Positive, 
            Spindexer.Right.STATOR_LIMIT.in(Amps),
            Spindexer.Right.SUPPLY_LIMIT.in(Amps))
            .withPIDFConfiguration(
                Spindexer.Right.kP,
                Spindexer.Right.kI,
                Spindexer.Right.kD,
                Spindexer.Right.kS,
                Spindexer.Right.kG,
                Spindexer.Right.kV,
                Spindexer.Right.kA,
                GravityTypeValue.Elevator_Static,
                StaticFeedforwardSignValue.UseClosedLoopSign,
                0)
            .build();

        leftCanrange = new CANrange(Canranges.LEFT_CANRANGE_ID, CANBUS);
        rightCanrange = new CANrange(Canranges.RIGHT_CANRANGE_ID, CANBUS);

        CANrangeConfiguration canConfig = new CANrangeConfiguration()
                .withFovParams(
                    new FovParamsConfigs()
                    .withFOVCenterX(0.0) // Placeholder, will need to be tuned
                    .withFOVCenterY(0.0) // Placeholder, will need to be tuned
                    .withFOVRangeX(0.0) // Placeholder, will need to be tuned
                    .withFOVRangeY(0.0) // Placeholder, will need to be tuned
                )
                .withProximityParams(
                    new ProximityParamsConfigs()
                    .withMinSignalStrengthForValidMeasurement(0.0) // Placeholder, will need to be tuned
                );
                rightCanrange.getConfigurator().apply(canConfig);
                leftCanrange.getConfigurator().apply(canConfig);
        }
    
        @Override
        public void spinSpeedLeft(AngularVelocity speed){
            indexerLeft.setMMVelocityTarget(speed, 0); //Change if the motor is not MotionMagic
        }
    
        @Override
        public void spinSpeedRight(AngularVelocity speed){
            indexerRight.setMMVelocityTarget(speed, 0); //Change if the motor is not MotionMagic
        }
    
        @Override
        public void stop(){
            indexerLeft.stop();
            indexerRight.stop();
        }
    
        @Override
        public void runDutyCycleRight(double dutyCycle){
            indexerRight.setDutyCycle(dutyCycle);
        }
    
        @Override
        public void runDutyCycleLeft(double dutyCycle){
            indexerLeft.setDutyCycle(dutyCycle);
        }
    
        @Override
        public AngularVelocity getLeftSpeed(){
            return indexerLeft.getVelocity();
        }
    
        @Override
        public AngularVelocity getRightSpeed(){
            return indexerRight.getVelocity();
        }
    
        @Override
        public Current getLeftCurrent(){
            return indexerLeft.getMotor().getStatorCurrent().getValue();
        }
    
        @Override
        public Current getRightCurrent(){
            return indexerRight.getMotor().getStatorCurrent().getValue();
        }

        @Override
        public Distance getLeftRange(){
            return leftCanrange.getDistance().getValue();
        }
    
        @Override
        public Distance getRightRange(){
            return rightCanrange.getDistance().getValue();
        }
    
        @Override
        public void periodic(){
        }
    }
