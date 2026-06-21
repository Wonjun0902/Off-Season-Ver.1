package frc.robot.subsystems.Shooter;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
public interface ShooterIO {

    public void setShooterSpeed(AngularVelocity velocity);
    public void setHoodAngle(Angle angle);

    public void stop();

    public AngularVelocity getLeftSpeed();
    public AngularVelocity getRightSpeed();
    public Angle getHoodAngle();

    public void periodic();
}
