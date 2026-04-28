package frc.robot.lib;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.wpilibj.DriverStation;

public class LazyFXS implements LazyCTRE {
    private TalonFXS motor, follower;
    private CANcoder canCoder;
    private boolean enableFOC = true;

    public LazyFXS(int motorID, String canBus, TalonFXSConfiguration configuration, int followID, String followCanBus,
            TalonFXSConfiguration followConfiguration, MotorAlignmentValue followerInverted, int canCoderID,
            String canCoderCanBus, CANcoderConfiguration canCoderConfiguration) {
        motor = new TalonFXS(motorID, canBus);
        motor.getConfigurator().apply(configuration);

        if (followConfiguration != null) {
            follower = new TalonFXS(followID, followCanBus);
            follower.getConfigurator().apply(followConfiguration);
            follower.setControl(new Follower(motorID, followerInverted));
        }

        if (canCoderConfiguration != null) {
            canCoder = new CANcoder(canCoderID, canCoderCanBus);
            canCoder.getConfigurator().apply(canCoderConfiguration);
        }
    if (motor.getIsProLicensed().getValue() ==  false) DriverStation.reportWarning("Motor" + motor.getDeviceID() + " on CANbus" + motor.getNetwork(), false);
        BaseStatusSignal.setUpdateFrequencyForAll(250, motor.getPosition(),motor.getVelocity(),motor.getAcceleration(),motor.getStatorCurrent(),motor.getSupplyCurrent());
        motor.optimizeBusUtilization();
        for (int i = 0; i < 5; i++) {
            var error = motor.getConfigurator().apply(configuration);
            if (error.isOK()) {
                break;
            } else {
                DriverStation.reportWarning(
                        "Warning: Your motor configuration on motor " + motor.getDeviceID() + " was not applied!",
                        false);
            }
        }        
    motor.setNeutralMode(NeutralModeValue.Brake);
    }

    @Override
    public void setMMPositionTarget(Angle setpoint, int slot) {
        this.motor.setControl(new MotionMagicVoltage(setpoint).withEnableFOC(this.enableFOC).withSlot(slot));
    }

    @Override
    public void setMMExpoTarget(Angle setpoint, int slot) {
        this.motor.setControl(new MotionMagicExpoVoltage(setpoint).withEnableFOC(this.enableFOC).withSlot(slot));
    }

    @Override
    public void setMMVelocityTarget(AngularVelocity velocity, int slot) {
        this.motor.setControl(new MotionMagicVelocityVoltage(velocity).withEnableFOC(this.enableFOC).withSlot(slot));
    }

    @Override
    public void setTFOCVelocityTarget(AngularVelocity velocity, Current feedforward, int slot) {
        this.motor.setControl(new VelocityTorqueCurrentFOC(velocity).withFeedForward(feedforward).withSlot(slot));
    }

    @Override
    public void setPIDPositionTarget(Angle setpoint, int slot) {
        this.motor.setControl(new PositionVoltage(setpoint).withEnableFOC(this.enableFOC).withSlot(slot));
    }

    @Override
    public void setPIDVelocityTarget(AngularVelocity velocity, int slot) {
        this.motor.setControl(new VelocityVoltage(velocity).withEnableFOC(this.enableFOC).withSlot(slot));
    }

    @Override
    public void setDutyCycle(double percent) {
        this.motor.set(percent);
    }

    @Override
    public void setCoast() {
        this.motor.setNeutralMode(NeutralModeValue.Coast);
    }

    @Override
    public void setBrake() {
        this.motor.setNeutralMode(NeutralModeValue.Brake);
    }

    @Override
    public void enableFOC() {
        this.enableFOC = true;
    }

    @Override
    public void disableFOC() {
        this.enableFOC = false;
    }

    @Override
    public void stop() {
        this.motor.stopMotor();
    }

    @Override
    public Angle getPosition() {
        return this.motor.getPosition().getValue();
    }

    @Override
    public AngularVelocity getVelocity() {
        return this.motor.getVelocity().getValue();
    }

    @Override
    public AngularAcceleration getAcceleration() {
        return this.motor.getAcceleration().getValue();
    }

    @Override
    public void updateTelemetry(MotorTelemetry telemetry) {
        telemetry.position = motor.getPosition().getValue();
        telemetry.velocity = motor.getVelocity().getValue();
        telemetry.acceleration = motor.getAcceleration().getValue();
        telemetry.statorCurrent = motor.getStatorCurrent().getValue();
        telemetry.supplyCurrent = motor.getSupplyCurrent().getValue();
    }
    
    public TalonFXS getMotor() {
        return motor;
    }

    public TalonFXS getFollower() {
        return follower;
    }

    public CANcoder getCanCoder() {
        return canCoder;
    }
}