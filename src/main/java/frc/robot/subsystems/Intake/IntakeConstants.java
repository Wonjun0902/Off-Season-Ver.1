package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public class IntakeConstants {

    public static double ROTATION_TOLERANCE = 0.0; //placeholder

    public static AngularVelocity INTAKE_SPEED_BOTTOM = RotationsPerSecond.of(0.0); //Placeholder
    public static AngularVelocity INTAKE_SPEED_TOP = RotationsPerSecond.of(0.0); //Placeholder

    //public static AngularVelocity OUTTAKE_SPEED_BOTTOM = INTAKE_SPEED_BOTTOM.unaryMinus();
    public static AngularVelocity OUTTAKE_SPEED_BOTTOM = RotationsPerSecond.of(0.0); //Placeolder
    //public static AngularVelocity OUTTAKE_SPEED_TOP = INTAKE_SPEED_TOP.unaryMinus();
    public static AngularVelocity OUTTAKE_SPEED_TOP = RotationsPerSecond.of(0.0); //Placeholder
}
