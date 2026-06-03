package frc.robot.subsystems.Deploy;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Time;
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
import static frc.robot.subsystems.Deploy.DeployConstants.Configurations.Positions.*;

public class Deploy extends SubsystemBase{

    private DeployIO io;

    public final Trigger isAtM1;
    public final Trigger isAtM2;

    public final Trigger isRetracted;
    public final Trigger isDeployed;
    
    public Deploy(DeployIO io) {
        this.io = io;

        isAtM1 = new Trigger(() -> io.getPosition().isNear(M1_ANGLE, DEPLOY_TOLERANCE));
        isAtM2 = new Trigger(() -> io.getPosition().isNear(M2_ANGLE, DEPLOY_TOLERANCE));
        
        isRetracted = new Trigger(() -> io.getPosition().isNear(RETRACTED_ANGLE, DEPLOY_TOLERANCE));
        isDeployed = new Trigger(() -> io.getPosition().isNear(DEPLOYED_ANGLE, DEPLOY_TOLERANCE));
    }

    public Command Deploy(){
        return runOnce(() -> io.deploy(M1_ANGLE));
    }

    public Command Retract(){
        return runOnce(() -> io.retract(RETRACTED_ANGLE));
    }

    public Command Agitate(Angle StartPoint, Angle EndPoint, Time duration){
        return new SequentialCommandGroup(
            run(() -> io.agitateFrom(StartPoint, EndPoint, duration)).withTimeout(duration.in(Seconds)),
            run(() -> io.agitateFrom(EndPoint, StartPoint, duration)).withTimeout(duration.in(Seconds))
        );
    }

    public Command AgitateM1(){
        return Agitate(DEPLOYED_ANGLE, M1_ANGLE, Seconds.of(0.5)); //Time Placeholder
    }

    public Command AgitateM2(){
        return Agitate(DEPLOYED_ANGLE, M2_ANGLE, Seconds.of(0.5)); //Time Placeholder
    }

    public Command FullAgitate(){
        return Agitate(DEPLOYED_ANGLE, RETRACTED_ANGLE, Seconds.of(0.5)); //Time Placeholder
    }

    public Command MidAgitate(){
        return Agitate(M1_ANGLE, M2_ANGLE, Seconds.of(0.5)); //Time Placeholder
    }
}
