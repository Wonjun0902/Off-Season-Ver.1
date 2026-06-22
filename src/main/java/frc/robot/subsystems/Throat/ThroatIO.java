package frc.robot.subsystems.Throat;

import edu.wpi.first.units.measure.AngularVelocity;

public interface ThroatIO {

    public void setThroatSpeed(AngularVelocity velocity);
    
    public void stop();

    public AngularVelocity getRightSpeed();
    public AngularVelocity getLeftSpeed();

    public void periodic();

}
