package frc.robot.subsystems.Deploy;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DeployIOSim extends SubsystemBase implements DeployIO{

    private DCMotor gearbox;
    private DCMotorSim motorSim;

    public DeployIOSim(){
        gearbox = DCMotor.getKrakenX44(2);
        motorSim = new DCMotorSim(LinearSystemId.createDCMotorSystem(gearbox, 0.1,1 ), gearbox);
    }

    @Override
    public void moveTo(Angle position){
        motorSim.setInputVoltage(0);
    }
}
