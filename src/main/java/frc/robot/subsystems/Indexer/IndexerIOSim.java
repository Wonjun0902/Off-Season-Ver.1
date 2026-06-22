package frc.robot.subsystems.Indexer;


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.nio.channels.UnsupportedAddressTypeException;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerIOSim extends SubsystemBase implements IndexerIO{

    private DCMotor gearbox;
    private DCMotorSim motorSim;   

    public IndexerIOSim(){
        gearbox = DCMotor.getKrakenX60(2);
        motorSim = new DCMotorSim(LinearSystemId.createDCMotorSystem(gearbox, 0.1, 1), gearbox); 
    }

    @Override
    public void spinSpeedLeft(AngularVelocity speed){
        motorSim.setInputVoltage(0);
    }

    @Override 
    public void spinSpeedRight(AngularVelocity speed){
        motorSim.setInputVoltage(0);
    }

    @Override
    public void Stop(){
        motorSim.setInputVoltage(0); //This is actually 0 lol
    }

    @Override
    public AngularVelocity getLeftSpeed(){
        throw new UnsupportedOperationException("Unimplemented method 'getLeftSpeed'");
    }

    @Override
    public AngularVelocity getRightSpeed(){
        throw new UnsupportedOperationException("Unimplemented method 'getRightSpeed'");
    }

    @Override
    public Current getLeftCurrent(){
        throw new UnsupportedOperationException("Unimplemented method 'getLeftCurrent'");
    }

    @Override
    public Current getRightCurrent(){
        throw new UnsupportedOperationException("Unimplemented method 'getRightCurrent'");
    }

    @Override
    public Distance getLeftRange(){
        return Inches.of(0.0);
    }

    @Override
    public Distance getRightRange(){
        return Inches.of(0.0);
    }

    @Override
    public void periodic(){
        motorSim.update(0.02);
    }
}
