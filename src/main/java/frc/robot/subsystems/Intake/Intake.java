package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.subsystems.Intake.IntakeConstants.*;

public class Intake extends SubsystemBase{

    private IntakeIO io;

    public final Trigger isIntaking;
    public final Trigger isOuttaking;
    public final Trigger isIdle;

    public Intake(IntakeIO io){

        this.io = io;

        isIntaking = new Trigger(() -> io.getTopSpeed().lt(RotationsPerSecond.of(0)) && io.getBottomSpeed().lt(RotationsPerSecond.of(0)));
        isOuttaking = new Trigger(() -> io.getTopSpeed().gt(RotationsPerSecond.of(0)) && io.getBottomSpeed().gt(RotationsPerSecond.of(0)));
        isIdle = new Trigger(() -> io.getBottomSpeed().isNear(RotationsPerSecond.of(0), ROTATION_TOLERANCE) && io.getTopSpeed().isNear(RotationsPerSecond.of(0), ROTATION_TOLERANCE));
    }

    public Command intake(AngularVelocity speed){
        return new SequentialCommandGroup(
            run(() -> io.spinBottomRoller(INTAKE_SPEED_BOTTOM)),
            run(() -> io.spinTopRoller(INTAKE_SPEED_TOP))
        );
    }

    public Command outtake(AngularVelocity speed){
        return new SequentialCommandGroup(
            run(() -> io.spinBottomRoller(OUTTAKE_SPEED_BOTTOM)),
            run(() -> io.spinTopRoller(OUTTAKE_SPEED_TOP))
        );
    }

    public Command stop(){
        return run(() -> {
            io.spinBottomRoller(RotationsPerSecond.of(0));
            io.spinTopRoller(RotationsPerSecond.of(0));
        });
    }

    @Override
    public void periodic(){
        // SmartDashboard.putNumber("Bottom intake voltage", io.getBottomIntakeVoltage().magnitude());
        // SmartDashboard.putNumber("Bottom intake supply current", io.getBottomIntakeSupplyCurrent().magnitude());
        // SmartDashboard.putNumber("Bottom intake velocity", io.getBottomIntakeVelocity().magnitude());
        // SmartDashboard.putNumber("Bottom intake position", io.getBottomIntakePosition().magnitude());

        SmartDashboard.putBoolean("Intake running", isIntaking.getAsBoolean());

        SmartDashboard.putBoolean("Intake state", io.getBottomSpeed().abs(RotationsPerSecond) > 1.0);
    }

}
