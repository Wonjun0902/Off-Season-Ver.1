package frc.robot.subsystems.Indexer;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;

public interface IndexerIO {

    public void spinSpeedLeft(AngularVelocity speed);
    public void spinSpeedRight(AngularVelocity speed);

    public void stop();

    public void runDutyCycleRight(double dutyCycle);
    public void runDutyCycleLeft(double dutyCycle);
    
    public AngularVelocity getLeftSpeed();
    public AngularVelocity getRightSpeed();

    public Current getLeftCurrent();
    public Current getRightCurrent();

    public Distance getLeftRange();
    public Distance getRightRange();

    public void periodic();
}
