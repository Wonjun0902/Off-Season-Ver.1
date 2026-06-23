package frc.robot.subsystems.Indexer;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.Indexer.IndexerConstants.Configurations.Spindexer;

public class IndexerIOSim extends SubsystemBase implements IndexerIO{

    private TalonFX rightMotor, leftMotor;
    TalonFXSimState rightMotorSim;
    TalonFXSimState leftMotorSim; 

    private final DCMotorSim rightMotorModel = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60Foc(1), 0.08, Spindexer.Right.SENSOR_TO_MECH_RATIO),
        DCMotor.getKrakenX60Foc(1)
    );

    private final DCMotorSim leftMotorModel = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60Foc(1), 0.08, Spindexer.Left.SENSOR_TO_MECH_RATIO),
        DCMotor.getKrakenX60Foc(1)
    );

    public IndexerIOSim(){
        rightMotor = new TalonFX(Spindexer.Right.MOTOR_ID);
        leftMotor = new TalonFX(Spindexer.Left.MOTOR_ID);

        TalonFXConfiguration rightConfig = new TalonFXConfiguration();
        rightConfig.Slot0.kP = Spindexer.Right.kP;
        rightConfig.Slot0.kI = Spindexer.Right.kI;
        rightConfig.Slot0.kD = Spindexer.Right.kD;
        rightConfig.Feedback.SensorToMechanismRatio = Spindexer.Right.SENSOR_TO_MECH_RATIO;
        rightConfig.MotionMagic.MotionMagicAcceleration = Spindexer.Right.MAX_ACCELERATION;
        rightConfig.MotionMagic.MotionMagicCruiseVelocity = Spindexer.Right.CRUISE_VELOCITY;
        rightConfig.MotionMagic.MotionMagicExpo_kA = Spindexer.Right.EXPO_A;
        rightConfig.MotionMagic.MotionMagicExpo_kV = Spindexer.Right.EXPO_V;
        rightMotor.getConfigurator().apply(rightConfig);

        TalonFXConfiguration leftConfig = new TalonFXConfiguration();
        leftConfig.Slot0.kP = Spindexer.Left.kP;
        leftConfig.Slot0.kI = Spindexer.Left.kI;
        leftConfig.Slot0.kD = Spindexer.Left.kD;
        leftConfig.Feedback.SensorToMechanismRatio = Spindexer.Left.SENSOR_TO_MECH_RATIO;
        leftConfig.MotionMagic.MotionMagicAcceleration = Spindexer.Left.MAX_ACCELERATION;
        leftConfig.MotionMagic.MotionMagicCruiseVelocity = Spindexer.Left.CRUISE_VELOCITY;
        leftConfig.MotionMagic.MotionMagicExpo_kA = Spindexer.Left.EXPO_A;
        leftConfig.MotionMagic.MotionMagicExpo_kV = Spindexer.Left.EXPO_V;
        rightMotor.getConfigurator().apply(leftConfig);

        rightMotorSim = rightMotor.getSimState();
        leftMotorSim = leftMotor.getSimState();

        rightMotorSim.setSupplyVoltage(12.0);
        leftMotorSim.setSupplyVoltage(12.0);
    }

    @Override
    public void spinSpeedLeft(AngularVelocity speed){
        leftMotor.setControl(new MotionMagicVelocityVoltage(speed));
    }

    @Override 
    public void spinSpeedRight(AngularVelocity speed){
        rightMotor.setControl(new MotionMagicVelocityVoltage(speed));
    }
    

    @Override
    public void stop(){
        leftMotor.stopMotor();
        rightMotor.stopMotor();
    }

    @Override
    public void periodic(){
        //Apply Voltage to Sim 
        rightMotorModel.setInputVoltage(rightMotorSim.getMotorVoltage());
        leftMotorModel.setInputVoltage(leftMotorSim.getMotorVoltage());

        //Update by 20ms
        rightMotorModel.update(0.02);
        leftMotorModel.update(0.02);

        //Update the Talon's Internal sensors from the physics world
        rightMotorSim.setRawRotorPosition(rightMotorModel.getAngularPositionRotations());
        leftMotorSim.setRawRotorPosition(leftMotorModel.getAngularPositionRotations());

        rightMotorSim.setRotorVelocity(rightMotorModel.getAngularVelocityRPM()/60);
        leftMotorSim.setRotorVelocity(leftMotorModel.getAngularVelocityRPM()/60);
    }

    @Override
    public void runDutyCycleRight(double dutyCycle){
        rightMotor.setControl(new DutyCycleOut(dutyCycle));
    }

    @Override
    public void runDutyCycleLeft(double dutyCycle){
        leftMotor.setControl(new DutyCycleOut(dutyCycle));
    }

    @Override
    public AngularVelocity getLeftSpeed(){
        return leftMotor.getVelocity().getValue();
    }

    @Override
    public AngularVelocity getRightSpeed(){
        return rightMotor.getVelocity().getValue();
    }

    @Override
    public Current getLeftCurrent(){
        return leftMotor.getStatorCurrent().getValue();
    }

    @Override
    public Current getRightCurrent(){
        return rightMotor.getStatorCurrent().getValue();
    }

    @Override
    public Distance getLeftRange(){
        throw new UnsupportedOperationException("method undefined 'getLeftRange'");
    }

    @Override
    public Distance getRightRange(){
        throw new UnsupportedOperationException("method undefined 'getRightRange'");
    }
    }

