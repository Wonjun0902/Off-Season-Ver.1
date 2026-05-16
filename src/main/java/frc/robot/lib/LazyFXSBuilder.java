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
    private MotorArrangementValue motorArrangement;
    private int motorID, followID, canCoderID;
    private String canBus = "", followCanbus = "", canCoderCanBus = "";

    private TalonFXSConfiguration motorConfiguration = null, followerConfiguration = null;
    private CANcoderConfiguration canCoderConfiguration = null;

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
        motorConfiguration.Commutation.MotorArrangement = motorArrangement;
        this.motorArrangement = motorArrangement;
        motorConfiguration.ExternalFeedback.SensorToMechanismRatio = sensorToMechanismRatio;
        motorConfiguration.MotorOutput.Inverted = invertedValue;

        motorConfiguration.CurrentLimits.StatorCurrentLimit = statorCurrentLimit;
        motorConfiguration.CurrentLimits.SupplyCurrentLimit = supplyCurrentLimit;
        motorConfiguration.CurrentLimits.SupplyCurrentLowerTime = 0.0;

        motorConfiguration.ClosedLoopGeneral.ContinuousWrap = false;
        motorConfiguration.Commutation.AdvancedHallSupport = AdvancedHallSupportValue.Enabled;

        motorConfiguration.Audio.AllowMusicDurDisable = true;
    }

    public LazyFXSBuilder withRotorToSensorRatio(double rotorToSensorRatio){
        motorConfiguration.ExternalFeedback.RotorToSensorRatio = rotorToSensorRatio;
        return this;
    }

    @Override
    public LazyFXSBuilder withFollower(int followerID, String canBus, MotorAlignmentValue isInverted){
        this.followID = followerID;
        this.canBus = canBus;
        this.follwerInverted = isInverted;

        followerConfiguration = new TalonFXSConfiguration();
        followerConfiguration.Commutation.MotorArrangement = this.motorArrangement;

        return this;
    }

    @Override
    public LazyFXSBuilder withCANCoder(int CANcoderID, String canCoderCanBus, 
            ExternalFeedbackSensorSourceValue sensorType, double magnetOffset,
            SensorDirectionValue sensorDirection){
        
        this.canCoderID = CANcoderID;
        this.canCoderCanBus = canCoderCanBus;
        
        canCoderConfiguration = new CANcoderConfiguration();
        canCoderConfiguration.MagnetSensor.MagnetOffset = magnetOffset;
        canCoderConfiguration.MagnetSensor.SensorDirection = sensorDirection;
        motorConfiguration.ExternalFeedback.FeedbackRemoteSensorID = CANcoderID;
        motorConfiguration.ExternalFeedback.ExternalFeedbackSensorSource = sensorType;

        return this;
    }
}