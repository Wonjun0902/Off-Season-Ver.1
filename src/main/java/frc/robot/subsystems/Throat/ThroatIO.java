package frc.robot.subsystems.Throat;

import edu.wpi.first.units.measure.AngularVelocity;

public interface ThroatIO {

    public void setRightSpeed(AngularVelocity velocity);
    public void setLeftSpeed(AngularVelocity velocity);
    
    public void stop();

    public AngularVelocity getRightSpeed();
    public AngularVelocity getLeftSpeed();

    public void periodic();

}
