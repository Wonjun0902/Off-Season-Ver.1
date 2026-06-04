package frc.robot.subsystems.Intake;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
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
import static frc.robot.subsystems.Intake.IntakeConstants.*;

public interface IntakeIO {

    public void spinTopRoller(AngularVelocity speed);
    public void spinBottomRoller(AngularVelocity speed);

    public AngularVelocity getTopSpeed();
    public AngularVelocity getBottomSpeed();

    public void Stop();

    public void runDutyCycleTop(double dutyCycle);
    public void runDutyCycleBottom(double dutyCycle);

    public Current getTopCurrent();
    public Current getBottomCurrent();

    public void periodic();
}
