package frc.robot.subsystems.Deploy;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.Deploy.DeployConstants.Configurations.Deployer;

public class DeployIOSim extends SubsystemBase implements DeployIO{
    private TalonFX deployMotor;
    private TalonFXSimState deployMotorSim;

    private final DCMotorSim deployMotorModel = new DCMotorSim(LinearSystemId.createDCMotorSystem(
        DCMotor.getKrakenX60Foc(0), 0.08, Deployer.ROTOR_TO_SENSOR_RATIO),
        DCMotor.getKrakenX60Foc(1)
    );

    public DeployIOSim(){
        deployMotor = new TalonFX(Deployer.MOTOR_ID);

        TalonFXConfiguration deployerConfig = new TalonFXConfiguration();
        deployerConfig.Slot0.kP = Deployer.kP;
        deployerConfig.Slot0.kI = Deployer.kI;
        deployerConfig.Slot0.kD = Deployer.kD;
        deployerConfig.Feedback.SensorToMechanismRatio = Deployer.SENSOR_TO_MECH_RATIO;
        deployerConfig.MotionMagic.MotionMagicAcceleration = Deployer.MAX_ACCELERATION.magnitude();
        deployerConfig.MotionMagic.MotionMagicCruiseVelocity = Deployer.CRUISE_VELOCITY.magnitude();
        deployerConfig.MotionMagic.MotionMagicExpo_kA = Deployer.EXPO_A;
        deployerConfig.MotionMagic.MotionMagicExpo_kV = Deployer.EXPO_V;
        deployMotor.getConfigurator().apply(deployerConfig);

        //Get Sim State
        deployMotorSim = deployMotor.getSimState();

        //Set Supply Voltage 
        deployMotorSim.setSupplyVoltage(12);
    }

    @Override
    public void moveTo(Angle position){
        deployMotor.setControl(new MotionMagicVoltage(position));
    }

    @Override
    public void runDutyCycle(double dutyCycle){
        deployMotor.setControl(new DutyCycleOut(dutyCycle));
    }

    @Override
    public AngularVelocity getSpeed(){
        return deployMotor.getVelocity().getValue();
    }

    @Override
    public void stop(){
        deployMotor.stopMotor();
    }

    @Override
    public void periodic(){
        //Apply volatge from Talon to the physics sim 
        deployMotorModel.setInputVoltage(deployMotorSim.getMotorVoltage());

        //Advanced Physics World
        deployMotorModel.update(0.02);

        //Update Internal Sensors 
        deployMotorSim.setRawRotorPosition(deployMotorModel.getAngularPositionRotations());
        deployMotorSim.setRotorVelocity(deployMotorModel.getAngularVelocityRPM()/60);
    }

    @Override
    public Current getCurrent(){
        return deployMotor.getStatorCurrent().getValue();
    }

    @Override
    public Angle getPosition(){
        return deployMotor.getPosition().getValue();
    }
}
