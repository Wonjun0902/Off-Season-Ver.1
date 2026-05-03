package frc.robot.lib;

import com.ctre.phoenix6.configs.ParentConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;

public interface LazyCTREBuilder<T, S> {
    /**
     * Configures the follower motor
     * 
     * @param followerID the motor ID of the follower motor to configure
     * @param canBus the CAN bus of the follower motor to configure
     * @param alignmentvalue the alignment of the follower motor to the leader motor
     * 
     * @return this motor instance for method chaining 
     */
    public LazyCTREBuilder<T, S> withFollower(int followerID, String canBus, MotorAlignmentValue alignmentValue);

    /**
     * Configures a {@link CANCoder} remote feedback sensor 
     * 
     * @param CANCoderID the motor ID of the remote sensor to configure
     * @param canBus the CAN bus of the remote sensor to configure
     * @param sensorType the type of remote sensor to configure
     * @param magnetOffset the magnet offset of the remote sensor to configure
     * @param sensorDirection the sensor direction of the remote sensor to configure
     * 
     * @return this motor instance for method chaining
     */
    public LazyCTREBuilder<T, S> withCANCoder(int CANCoderID, String canBus, S sensorType, 
                                            SensorDirectionValue sensorDirection, 
                                            double magnetOffset);

    /**
     * Configures a {@link CANCoder} remote feedback sensor as a secondary sensor
     * 
     * @param CANCoderID the motor ID of the remote sensor to configure
     * @param canBus the CAN bus of the remote sensor to configure
     * @param sensorType the type of remote sensor to configure
     * @param magnetOffset the magnet offset of the remote sensor to configure
     * @param sensorDirection the sensor direction of the remote sensor to configure
     * @param discontinuityPoint the point of discontinuity for the sensor
     * @param rotorToSensorRatio the gear ratio of the rotor to the sensor
     * 
     * @return this motor instance for method chaining 
     */
    public LazyCTREBuilder<T, S> withSecondaryCANCoder(int CANCoderID, String canBus, S sensorType, 
                                            SensorDirectionValue sensorDirection, 
                                            double magnetOffset, 
                                            double discontinuityPoint, 
                                            double rotorToSensorRatio);

    /**
     * Configures the PID gains and feedforward for a specific slot
     * 
     * @param p the proportional gain to configure
     * @param i the integral gain to configure
     * @param d the derivative gain to configure
     * 
     * @param s the static feedforward to configure
     * 
     * @param v the velocity feedforward to configure
     * 
     * @param a the acceleration feedforward to configure
     * 
     * @param g the gravity feedforward to configure
     * 
     * @param gravityType the type of gravity feedforward to configure
     * @param staticSignValue the sign of the static feedforward to configure
     * 
     * @param slot the slot to configure the gains and feedforward on
     * 
     * @return this motor instance for method chaining
     */
    public LazyCTREBuilder<T, S> withPIDGainsAndFeedforward(double p, double i, double d, 
                                                            double s, double v, double a, double g, 
                                                            GravityTypeValue gravityType, StaticFeedforwardSignValue staticSignValue,
                                                            int slot);

    /**
     * Configures the motion magic parameters for this motor's Motion Magic control mode
     * 
     * @param expoV the motion magic expo voltage to configure (lower values result in more aggressive velocity feedforward profile)
     * @param expoA the motion magic expo acceleration to configure (lower values result in more aggressive acceleration feedforward profile)
     * @param cruiseVelocity the motion magic cruise velocity to configure
     * @param maxAcceleration the motion magic acceleration to configure
     * 
     * @return this motor instance for method chaining
     */
    public LazyCTREBuilder<T, S> withMotionMagicCongifurations(double expoV, double expoA, 
                            AngularVelocity cruiseVelocity, 
                            AngularAcceleration maxAcceleration);

     /**
     * Configures the motion magic parameters for this motor's Motion Magic control mode
     * 
     * @param expoV the motion magic expo voltage to configure (lower values result in more aggressive velocity feedforward profile)
     * @param expoA the motion magic expo acceleration to configure (lower values result in more aggressive acceleration feedforward profile)
     * @param cruiseVelocity the motion magic cruise velocity to configure
     * @param maxAcceleration the motion magic acceleration to configure
     * @param jerk the motion magic jerk to configure (lower values result in more aggressive acceleration feedforward profile)
     * 
     * @return this motor instance for method chaining
     */
    public LazyCTREBuilder<T, S> withMotionMagicCongifurations(double expoV, double expoA, 
                            AngularVelocity cruiseVelocity, 
                            AngularAcceleration maxAcceleration, double jerk);


    /**
     * Configures the forward and reverse hardware limit switches 
     * 
     * @param forwardLimitEnabled if ture, enables the forward limit switch
     * @param forwardLimitAutosetPositionEnabled if true, the encoder position will be 
     *                                           set to the forward limit switch offset 
     *                                           when the forward limit switch is triggered
     * @param forwardLimitAutosetPositionValue   the position of the encoder when the limit 
     *                                           switch is triggered, if forwardLimitAutosetPositionEnabled is true
     * 
     * @param reverseLimitEnabled                if ture, enables the reverse limit switch
     * @param reverseLimitAutosetPositionEnabled if true, the encoder position will be set 
     *                                           to the reverse limit switch offset when the reverse limit switch is triggered
     * @param reverseLimitAutosetPositionValue   the position of the encoder when the limit switch is triggered, if reverseLimitAutosetPositionEnabled is true
     */
    public LazyCTREBuilder<T, S> withLimitSwitches (
        boolean forwardLimitEnabled, boolean forwardLimitAutosetPositionEnabled, double forwardLimitAutosetPositionValue,
        boolean reverseLimitEnabled, boolean reverseLimitAutosetPositionEnabled, double reverseLimitAutosetPositionValue);


    /**
     * Configures the forward and reverse soft limits 
     * 
     * @param forwardSoftLimitEnabled if true, enables the forward soft limit
     * @param forwardSoftLimit the position of the forward soft limit
     * 
     * @param reverseSoftLimitEnabled if true, enables the reverse soft limit
     * @param reverseSoftLimit the position of the reverse soft limit
     */
    public LazyCTREBuilder<T, S> withSoftLimits(
        boolean forwardSoftLimitEnabled, double forwardSoftLimit, 
        boolean reverseSoftLimitEnabled, double reverseSoftLimit
    );

    /**
     * Applies a custom configuration to this motor 
     * 
     * @param configuration the custom config object specific to the motor type
     * @return this motor instance for method chaining 
     */
    public LazyCTREBuilder<T, S> withCustomConfiguration(ParentConfiguration configuration);

    /**
     * Gets the motor's configuration 
     * 
     * @return the motor's configuration 
     */
    public ParentConfiguration getConfiguration();

    /**
     * Applies the motor configuration.
     * Use this method at the end of the configuration chain to apply the configuration to the motor. 
     * 
     * @return the instance of the motor for method chaining 
     */
    public LazyCTRE build();
}