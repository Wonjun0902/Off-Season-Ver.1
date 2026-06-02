package frc.robot.subsystems.Deploy;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static frc.robot.subsystems.Deploy.DeployConstants.*;

public class Deploy extends SubsystemBase{

    private DeployIO io;
    
    public Deploy(DeployIO io) {
        this.io = io;
    }

    public Command deploy(){
        return run(() -> {
            io.deploy();
        });
    }

     public Command retract(){
        return run(() -> {
            io.retract();
        });
     }

}
