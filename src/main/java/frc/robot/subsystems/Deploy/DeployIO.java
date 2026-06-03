package frc.robot.subsystems.Deploy;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public interface DeployIO {

    public void deploy(Angle position);
    public void retract(Angle position);

    public void moveToM1(Angle position);
    public void moveToM2(Angle position);

    public void agitateFrom(Angle StartPoint, Angle EndPoint, Time duration);

    public void runDutyCycleLeft(double dutyCycle);
    public void runDutyCycleRight(double dutyCycle);

    public AngularVelocity getSpeed();

    public void stop();

    public void periodic();

    public void getCurrent();
    
    public Angle getPosition();
}
