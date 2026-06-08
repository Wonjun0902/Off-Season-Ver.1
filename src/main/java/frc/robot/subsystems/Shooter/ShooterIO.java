package frc.robot.subsystems.Shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public interface ShooterIO {

    public void spinSpeedRight();
    public void spinSpeedLeft();

    public void stop();

    public void runDutyCycleRight(double dutyCycle);
    public void runDutyCycleLeft(double dutyCycle);
    
    public AngularVelocity getLeftSpeed();
    public AngularVelocity getRightSpeed();

    public Current getLeftCurrent();
    public Current getRightCurrent();

    public void periodic();
}
