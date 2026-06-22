package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import frc.robot.subsystems.Intake.IntakeConstants.Configurations;
import frc.robot.subsystems.Intake.IntakeConstants.Configurations.BottomRoller;
import frc.robot.subsystems.Intake.IntakeConstants.Configurations.TopRoller; 

public class IntakeIOSim implements IntakeIO{

    private TalonFX intakeTopMotor, intakeBottomMotor;
    private TalonFXSimState intakeTopMotorSim, intakeBottomMotorSim;

    private final DCMotorSim intakeTopMotorModel = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(
            DCMotor.getKrakenX60Foc(1), 0.08, TopRoller.SENSOR_TO_MECH_RATIO
        ),
        DCMotor.getKrakenX60Foc(1)
    );

    private final DCMotorSim intakeBottomMotorModel = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(
            DCMotor.getKrakenX60Foc(1), 0.08, TopRoller.SENSOR_TO_MECH_RATIO
        ),
        DCMotor.getKrakenX60Foc(1)
    );

    public IntakeIOSim(){
        intakeTopMotor = new TalonFX(TopRoller.MOTOR_ID);
        intakeBottomMotor = new TalonFX(BottomRoller.MOTOR_ID);

        TalonFXConfiguration intakeTopConfig = new TalonFXConfiguration();
        intakeTopConfig.Slot0.kP = TopRoller.kP;
        intakeTopConfig.Slot0.kI = TopRoller.kI;
        intakeTopConfig.Slot0.kD = TopRoller.kD;
        intakeTopConfig.Feedback.SensorToMechanismRatio = TopRoller.SENSOR_TO_MECH_RATIO;
        intakeTopConfig.MotionMagic.MotionMagicCruiseVelocity = TopRoller.CRUISE_VELOCITY;
        intakeTopConfig.MotionMagic.MotionMagicAcceleration = TopRoller.MAX_ACCELERATION;
        intakeTopConfig.MotionMagic.MotionMagicExpo_kV = TopRoller.kV;
        intakeTopConfig.MotionMagic.MotionMagicExpo_kA = TopRoller.kA;
        intakeTopMotor.getConfigurator().apply(intakeTopConfig);

        TalonFXConfiguration intakeBottomConfig = new TalonFXConfiguration();
        intakeBottomConfig.Slot0.kP = TopRoller.kP;
        intakeBottomConfig.Slot0.kI = TopRoller.kI;
        intakeBottomConfig.Slot0.kD = TopRoller.kD;
        intakeBottomConfig.Feedback.SensorToMechanismRatio = TopRoller.SENSOR_TO_MECH_RATIO;
        intakeBottomConfig.MotionMagic.MotionMagicCruiseVelocity = TopRoller.CRUISE_VELOCITY;
        intakeBottomConfig.MotionMagic.MotionMagicAcceleration = TopRoller.MAX_ACCELERATION;
        intakeBottomConfig.MotionMagic.MotionMagicExpo_kV = TopRoller.kV;
        intakeBottomConfig.MotionMagic.MotionMagicExpo_kA = TopRoller.kA;
        intakeTopMotor.getConfigurator().apply(intakeBottomConfig);

        //Get the Simstate after creating motors
        intakeTopMotorSim = intakeTopMotor.getSimState();
        intakeBottomMotorSim = intakeBottomMotor.getSimState();

        //Set Supply Voltage for sim 
        intakeTopMotorSim.setSupplyVoltage(12);
    }

    @Override
    public void periodic(){
        //Apply Voltage from Talon to physics sim 
        intakeTopMotorModel.setInputVoltage(intakeTopMotorSim.getMotorVoltage());
        intakeBottomMotorModel.setInputVoltage(intakeBottomMotorSim.getMotorVoltage());

        //Advanced Physics World
        intakeTopMotorModel.update(0.02);
        intakeBottomMotorModel.update(0.02);

        //Update Motor's internal Sensors
        intakeTopMotorSim.setRawRotorPosition(intakeTopMotorModel.getAngularPosition());
        intakeTopMotorSim.setRotorVelocity(intakeTopMotorModel.getAngularVelocityRPM()/60);

        intakeBottomMotorSim.setRawRotorPosition(intakeBottomMotorModel.getAngularPosition());
        intakeBottomMotorSim.setRotorVelocity(intakeBottomMotorModel.getAngularVelocityRPM()/60);
    }

    @Override
    public void spinTopRoller(AngularVelocity speed){
        intakeTopMotor.setControl(new MotionMagicVelocityVoltage(speed));
    }

    @Override
    public void spinBottomRoller(AngularVelocity speed){
        intakeBottomMotor.setControl(new MotionMagicVelocityVoltage(speed));
    }

    @Override
    public AngularVelocity getTopSpeed(){
        return intakeTopMotor.getVelocity().getValue();
    }

    @Override
    public AngularVelocity getBottomSpeed(){
        return intakeBottomMotor.getVelocity().getValue();
    }

    @Override
    public void Stop(){
        intakeBottomMotor.stopMotor();
        intakeTopMotor.stopMotor();
    }

    @Override
    public void runDutyCycleTop(double dutyCycle){
        intakeTopMotor.setControl(new DutyCycleOut(dutyCycle));
    }

    @Override
    public void runDutyCycleBottom(double dutyCycle){
        intakeBottomMotor.setControl(new DutyCycleOut(dutyCycle));
    }

    @Override
    public Current getTopCurrent(){
        return intakeTopMotor.getStatorCurrent().getValue();
    }

    @Override
    public Current getBottomCurrent(){
        return intakeBottomMotor.getStatorCurrent().getValue();
    }

}
