package frc.robot.subsystems.Indexer;


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerIOSim {

    private DCMotor gearbox;
    private DCMotorSim motorSim;   

    public IndexerIOSim(){
        gearbox = DCMotor.getKrakenX60(2);
        motorSim = new DCMotorSim(LinearSystemId.createDCMotorSystem(gearbox, 0.1, 1), gearbox); 
    }
}
