package frc.robot.lib;

import com.ctre.phoenix6.configs.ParentConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
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
         * Configures a {@link CANcoder} remote feedback sensor
         *
         * @param CANCoderID      the device ID of the CANcoder
         * @param canCoderCanBus  the CAN bus name the CANcoder is on
         * @param sensorType      the type of feedback the CANcoder will supply
         * @param magnetOffset    the digital magnet offset applied to the sensor
         * @param sensorDirection the sensor direciton relative to the mechanism
         * @return this motor instance for method chaining
         */
        public LazyCTREBuilder<T, S> withCANCoder(int CANCoderID, String canCoderCanBus, S sensorType,
                        double magnetOffset,
                        SensorDirectionValue sensorDirection);

   /**
         * Configures a {@link CANcoder} remote feedback sensor with a specified rotor
         * to sensor ratio and discontinuity point
         *
         * @param CANCoderID         the device ID of the CANcoder
         * @param canCoderCanBus     the CAN bus name the CANcoder is on
         * @param sensorType         the type of feedback the CANcoder will supply
         * @param magnetOffset       the digital magnet offset applied to the sensor
         * @param sensorDirection    the sensor direciton relative to the mechanism
         * @param discontinuityPoint the point of discontinuity for the sensor
         * @param rotorToSensorRatio the gear ratio of the sensor to the motor
         * @return this motor instance for method chaining
         */
        public LazyCTREBuilder<T, S> withCANCoder(int CANCoderID, String canCoderCanBus, S sensorType,
                        double magnetOffset,
                        SensorDirectionValue sensorDirection, double discontinuityPoint, double rotorToSensorRatio);

    /**
         * Configures the PID and Feedforward gains for this motor
         * 
         * @param p               the proportional gain
         * @param i               the integral gain
         * @param d               the derivative gain
         * @param s               the static friction feed forward (voltage reqiured to
         *                        overcome static friction)
         * @param g               the gravity feed forward (voltage required to overcome
         *                        gravity)
         * @param v               the velocity feed forward (voltage required to
         *                        maintain a 1 rotation/s angular velocity in mechanism
         *                        rotations)
         * @param a               the velocity feed forward (voltage required to
         *                        maintain a 1 rotation/s^2 angular acceleration in
         *                        mechanism rotations)
         * @param gravityType     what type of gravity feed forward to use
         * @param staticSignValue the sign of the static friction feed forward
         * @param slotNumber      the slot number to use for the PIDF gains
         * @return this motor instance for method chaining
         */
        public LazyCTREBuilder<T, S> withPIDFConfiguration(double p, double i, double d, double s, double g, double v,
                        double a, GravityTypeValue gravityType, StaticFeedforwardSignValue staticSignValue,
                        int slotNumber);


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