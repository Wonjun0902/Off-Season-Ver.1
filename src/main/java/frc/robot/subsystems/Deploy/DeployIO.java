package frc.robot.subsystems.Deploy;

public interface DeployIO {

    public void deploy();
    public void retract();
    public void stop();

    public void periodic();

    public void getSpeed();

    public void getCurrent();
    
    public void getPosition();

}
