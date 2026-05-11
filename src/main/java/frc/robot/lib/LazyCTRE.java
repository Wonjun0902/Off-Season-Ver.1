package frc.robot.lib;

import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public interface LazyCTRE {

    /**
     * Sets the target position for the motor using Motion Magic Voltage Controls.
     * 
     * This request uses the PID gains, feedforward, and motion magic acceleration, 
     * velocity, and jerk
     * 
     * @param setpoint the mechanism relative target
     */
    void setMMPositionTarget(Angle setpoint, int slot);

    /**
     * Sets the target position for the motor using Motion Magic Expo Voltage Controls.
     * 
     * This request uses the PID gains, feedforward, and motion magic acceleration 
     * 
     * @param setpoint the mechanism relative target
     */
    void setMMExpoPositionTarget(Angle setpoint, int slot);

    /**
     * Sets the target velocity for the motor using Motion Magic Voltage Controls.
     * 
     * This request uses the PID gains, feedforward, and motion magic acceleration
     * 
     * @param setpoint the mechanism relative target
     */
    void setMMVelocityTarget(AngularVelocity setpoint, int slot);

    /**
     * Sets the target Velocity for the motor using Torque Current FOC Controls
     * 
     * This request uses the PID gains and TFOC Current Feedforward, 
     * 
     * @param setpoint the mechanism relative target
     */
    void setTFOCVelocityTarget(AngularVelocity setpoint, Current feedforward, int slot);

    /** 
     * Sets the target velocity for the motor using Velocity Voltage Controls.
     * 
     * This request uses raw PID gains and feedforward
     * 
     * @param setpoint the mechanism relative target
     */
    void setPIDVelocityTarget(AngularVelocity setpoint, int slot);

    /**
     * Sets the target position for the motor using Position Voltage Controls
     * 
     * This request uses raw PID gains and feedforward
     * 
     * @param setpoint the mechanism relative target
     */
    void setPIDPositionTarget(Angle setpoint, int slot);

    /**
     * Sets the motor output to a percentage of the maximum output
     * 
     * The Maximum output is 1 and the Minimum is -1 
     * 
     * This request does not require any PID gains for FeedForwad
     * But can use PID sometimes for better performance, but it is not required
     * 
     * @param output the percentage of the maximum output to set the motor to
     */
    void setDutyCycle(double output);

    /**
     * Sets the motor's {@link NeutralModeValue} to Coast, allowing the motor to 
     * spin freely when no power is applied
     */
    void setCoast();

    /**
     * Sets the motor's {@link NeutralModeValue} to Brake, 
     * causing the motor to resist motion when no power is applied
     */
    void setBrake();

    /**
     * Stops the Motor 
     */
    void stop();

    /**
     * Enables FOC, increasing the peak power to 15%
     */
    void enableFOC();

    /**
     * Disables FOC, decreasing the peak power to 12.5%        
     */
    void disableFOC();

    /**
     * Gets the motor's current position in mechanism relative units
     * 
     * @return the current mechanism position 
     */
    Angle getPosition();

    /**
     * Gets the motor's current feedback velocity in mechanism relative units 
     * 
     * @return the current mechanism velocity
     */
    AngularVelocity getVelocity();

     /**
     * Gets the motor's current feedback acceleration in mechanism relative units 
     * 
     * @return the current mechanism acceleration
     */
    AngularAcceleration getAcceleration();

    /**
     * Updates the Telemetry data of the motor 
     * @param telemetry the Telemetry object to update
     */
    public void updateTelemetry(MotorTelemetry telemetry);

    public class MotorTelemetry{
        public Angle position;
        public AngularVelocity velocity;
        public AngularAcceleration acceleration;
        public Current statorCurrent;
        public Current supplyCurrent;
    }
}