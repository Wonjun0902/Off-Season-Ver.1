package frc.robot.subsystems.Deploy;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;

public interface DeployIO {

    public void deploy(AngularVelocity speed);
    public void retract(AngularVelocity speed);

    public void runDutyCycleLeft(double dutyCycle);
    public void runDutyCycleRight(double dutyCycle);

    public AngularVelocity getSpeed();

    public void stop();

    public void periodic();

    public void getCurrent();
    
    public void getPosition();
}
