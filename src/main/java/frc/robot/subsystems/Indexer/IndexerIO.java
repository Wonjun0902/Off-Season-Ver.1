package frc.robot.subsystems.Indexer;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;

public interface IndexerIO {

    public void pidSpeedLeft(double speed);
    public void pidSpeedRight(double speed);

    public void Stop();

    public void runDutyCycleRight(double dutyCycle);
    public void runDutyCycleLeft(double dutyCycle);
    
    public AngularVelocity getLeftSpeed();
    public AngularVelocity getRightSpeed();

    public Current getLeftCurrent();
    public Current getRightCurrent();

    public Distance getLeftPosition();
    public Distance getRightPosition();

    public void periodic();
}
