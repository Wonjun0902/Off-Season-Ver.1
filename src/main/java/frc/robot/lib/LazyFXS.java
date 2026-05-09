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

    public LazyFXS(int motorID, String canBus, TalonFXSConfiguration configuration, int followID, String followCanbus, 
        TalonFXSConfiguration followConfiguration, MotorAlignmentValue followerInverted, int canCoderID, 
        String canCoderCanBus, CANcoderConfiguration canCoderConfig) {
    motor = new TalonFXS(motorID, canBus);
    motor.getConfigurator().apply(configuration);

    if(followConfiguration != null){
        follower = new TalonFXS(followID, followCanbus);
        follower.getConfigurator().apply(followConfiguration);
        follower.setControl(new Follower(motor.getDeviceID(), followerInverted));

    }
    

    }

}