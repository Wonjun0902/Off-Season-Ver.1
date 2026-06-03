package frc.robot.subsystems.Deploy;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public interface DeployIO {

    public void moveTo(Angle position);

    public void runDutyCycle(double dutyCycle);

    public AngularVelocity getSpeed();

    public void stop();

    public void periodic();

    public Current getCurrent();
    
    public Angle getPosition();
}
