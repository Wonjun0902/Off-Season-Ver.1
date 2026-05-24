package frc.robot.lib;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.ParentConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.DriverStation;

public class LazyTalonBuilder implements LazyCTREBuilder<LazyTalon, FeedbackSensorSourceValue>{
    private int motorID, folowID, canCoderID;
    private String canBus = "", followCanbus = "" , canCoderCanBus = "";
    private TalonFXConfiguration motorConfiguration = null, followConfiguration = null;
    private CANcoderConfiguration canCoderConfiguration = null;

    private MotorAlignmentValue followerInverted;

    
}