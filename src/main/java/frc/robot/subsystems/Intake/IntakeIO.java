package frc.robot.subsystems.Intake;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public interface IntakeIO {

    public void spinTopRoller(AngularVelocity speed);
    public void spinBottomRoller(AngularVelocity speed);

    public AngularVelocity getTopSpeed();
    public AngularVelocity getBottomSpeed();

    public void Stop();

    public void runDutyCycleTop(double dutyCycle);
    public void runDutyCycleBottom(double dutyCycle);

    public Current getTopCurrent();
    public Current getBottomCurrent();

    public void periodic();
}
