package frc.robot.lib;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.ParentConfiguration;
import com.ctre.phoenix6.configs.TalonFXSConfiguration;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.AdvancedHallSupportValue;
import com.ctre.phoenix6.signals.ExternalFeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.MotorArrangementValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class LazyFXSBuilder implements LazyCTREBuilder<TalonFXS, ExternalFeedbackSensorSourceValue> {
    private MotorArrangementValue motorArrangementValue;
    private int motorID, followID, canCoderID;
    private String canBus = "", followCanbus = "", canCoderCanBus = "";

    private TalonFXSConfiguration motor = null, follower = null;
    private CANcoderConfiguration canCoder = null;

    private MotorAlignmentValue follwerInverted;

    /**
     * Creates an instance for the LazyFXS Builder class with specified motor configurations 
     * 
     * @param motorID the ID of the motor to be configured
     * @param motorArrangement the arrangement of the motor (e.g., single, master, follower)
     * @param sensorToMechanismRatio the ratio of the sensor to the mechanism
     * 
     * @param invertedValue the inversion value for the motor
     * @param statorCurrentLimit the current limit for the stator
     * @param supplyCurrentLimit the current limit for the supply
     */
    public LazyFXSBuilder(int motorID, MotorArrangementValue motorArrangement, double sensorToMechanismRatio,
            InvertedValue invertedValue, double statorCurrentLimit, double supplyCurrentLimit) {
        this(motorID, "", motorArrangement, sensorToMechanismRatio, invertedValue, statorCurrentLimit,
                supplyCurrentLimit);
    }

    public LazyFXSBuilder(int motorID2, String string, MotorArrangementValue motorArrangement,
            double sensorToMechanismRatio, InvertedValue invertedValue, double statorCurrentLimit,
            double supplyCurrentLimit) {
        this.motorID = motorID;
        this.canBus = canBus;

        motorConfiguration = new TalonFXSConfiguration();
        motorConfiguration.MotorSensorPositionCoefficient = sensorToMechanismRatio;
        this.motorArrangementValue = motorArrangement;
        motorConfiguration.ExternalFeedback.sensorToMechanismRatio = sensorToMechanismRatio;
        motorConfiguration.MotorOutput.Inverted = invertedValue;

        motorConfiguration.CurrentLimits.StatorCurrentLimit = statorCurrentLimit;
        motorConfiguration.CurrentLimits.supplyCurrentLimit = supplyCurrentLimit;
        motorConfiguration.CurrentLimits.Inverted = 0.0;

        
    }
}