package frc.robot.subsystems.Throat;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import frc.robot.subsystems.Throat.ThroatConstants.Configurations.Throat.Right;
import frc.robot.subsystems.Throat.ThroatConstants.Configurations.Throat.Left;

public class ThroatIOSim implements ThroatIO{

    private TalonFX rightMotor, leftMotor;
    private TalonFXSimState rightMotorSim, leftMotorSim;

    private final DCMotorSim rightMotorModel = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 0.01, Right.SENSOR_TO_MECH_RATIO),
        DCMotor.getKrakenX60(1)
    );

    private final DCMotorSim leftMotorModel = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 0.01, Right.SENSOR_TO_MECH_RATIO),
        DCMotor.getKrakenX60(1)
    );

    public ThroatIOSim(){
        rightMotor = new TalonFX(Right.MOTOR_ID);
        leftMotor = new TalonFX(Left.MOTOR_ID);

        TalonFXConfiguration rightMotorConfig = new TalonFXConfiguration();
        rightMotorConfig.Slot0.kP = Right.kP;
        rightMotorConfig.Slot0.kI = Right.kI;
        rightMotorConfig.Slot0.kD = Right.kD;
        rightMotorConfig.Feedback.SensorToMechanismRatio = Right.SENSOR_TO_MECH_RATIO;
        rightMotorConfig.TorqueCurrent.PeakForwardTorqueCurrent = Right.CURRENT_FF;
        rightMotorConfig.TorqueCurrent.PeakReverseTorqueCurrent = -Right.CURRENT_FF;
        rightMotorConfig.TorqueCurrent.TorqueNeutralDeadband = Right.TORQUE_DEADBAND;
        rightMotor.getConfigurator().apply(rightMotorConfig);

        TalonFXConfiguration leftMotorConfig = new TalonFXConfiguration();
        leftMotorConfig.Slot0.kP = Left.kP;
        leftMotorConfig.Slot0.kI = Left.kI;
        leftMotorConfig.Slot0.kD = Left.kD;
        leftMotorConfig.Feedback.SensorToMechanismRatio = Left.SENSOR_TO_MECH_RATIO;
        leftMotorConfig.TorqueCurrent.PeakForwardTorqueCurrent = Left.CURRENT_FF;
        leftMotorConfig.TorqueCurrent.PeakReverseTorqueCurrent = -Left.CURRENT_FF;
        leftMotorConfig.TorqueCurrent.TorqueNeutralDeadband = Left.TORQUE_DEADBAND;
        leftMotor.getConfigurator().apply(leftMotorConfig);

        //Get SimState after configuring Motors
        rightMotorSim = rightMotor.getSimState();
        leftMotorSim = leftMotor.getSimState();

        //Set applied volts
        rightMotorSim.setSupplyVoltage(12);
        leftMotorSim.setSupplyVoltage(12);
    }

    @Override
    public void periodic(){
        //Again, loop the rightMotorSim 
        rightMotorSim = rightMotor.getSimState();
        leftMotorSim = leftMotor.getSimState();

        //Set batteryVoltage for the motors
        rightMotorSim.setSupplyVoltage(RobotController.getBatteryVoltage());
        leftMotorSim.setSupplyVoltage(RobotController.getBatteryVoltage());

        //Get the Motor Voltage of the TalonFX
        double rightMotorVoltage = rightMotorSim.getMotorVoltage();
        double leftMotorVoltage = leftMotorSim.getMotorVoltage();

        //Use the MotorVoltage to calculate new Velocity and Position
        //using WPILib's DCMotor Sim class for physics Sim
        rightMotorModel.setInputVoltage(rightMotorVoltage);
        leftMotorModel.setInputVoltage(leftMotorVoltage);

        //Update!
        rightMotorModel.update(0.02);
        leftMotorModel.update(0.02);

        // apply the new rotor position and velocity to the TalonFX;
        // note that this is rotor position/velocity (before gear ratio), but
        // DCMotorSim returns mechanism position/velocity (after gear ratio)
        rightMotorSim.setRawRotorPosition(rightMotorModel.getAngularPosition().times(Right.SENSOR_TO_MECH_RATIO));
        leftMotorSim.setRawRotorPosition(leftMotorModel.getAngularPosition().times(Left.SENSOR_TO_MECH_RATIO));
    }

    @Override 
    public void setRightSpeed(AngularVelocity velocity){
        rightMotor.setControl(new VelocityTorqueCurrentFOC(velocity));
    }

    @Override
    public void setLeftSpeed(AngularVelocity velocity){
        leftMotor.setControl(new VelocityTorqueCurrentFOC(velocity));
    }

    @Override
    public void stop(){
        rightMotor.stopMotor();
        leftMotor.stopMotor();
    }

    @Override
    public AngularVelocity getRightSpeed(){
        return rightMotor.getVelocity().getValue();
    }

    @Override
    public AngularVelocity getLeftSpeed(){
        return leftMotor.getVelocity().getValue();
    }

}
