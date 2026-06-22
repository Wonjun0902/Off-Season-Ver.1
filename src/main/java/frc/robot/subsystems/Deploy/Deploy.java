package frc.robot.subsystems.Deploy;

import edu.wpi.first.units.measure.Angle;

import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;
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
        return runOnce(() -> io.moveTo(M1_ANGLE));
    }

    public Command Retract(){
        return runOnce(() -> io.moveTo(RETRACTED_ANGLE));
    }

    public Command Agitate(Angle StartPoint, Angle EndPoint, Time duration){
        return new SequentialCommandGroup(
            run(() -> io.moveTo(EndPoint)).withTimeout(duration),
            run(() -> io.moveTo(StartPoint)).withTimeout(duration)
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

    public void periodic(){
        SmartDashboard.putNumber("Deploy Position", io.getPosition().in(Rotations));
        SmartDashboard.putNumber("Deploy Speed", io.getSpeed().in(RotationsPerSecond));
    }

    @Override
    public void simulationPeriodic() {
        io.periodic();
    }
}
