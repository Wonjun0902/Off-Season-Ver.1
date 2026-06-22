package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public class IntakeConstants {

    public static double ROTATION_TOLERANCE = 0.0; //placeholder

    public static AngularVelocity INTAKE_SPEED_BOTTOM = RotationsPerSecond.of(0.0); //Placeholder
    public static AngularVelocity INTAKE_SPEED_TOP = RotationsPerSecond.of(0.0); //Placeholder

    //public static AngularVelocity OUTTAKE_SPEED_BOTTOM = INTAKE_SPEED_BOTTOM.unaryMinus();
    public static AngularVelocity OUTTAKE_SPEED_BOTTOM = RotationsPerSecond.of(0.0); //Placeolder
    //public static AngularVelocity OUTTAKE_SPEED_TOP = INTAKE_SPEED_TOP.unaryMinus();
    public static AngularVelocity OUTTAKE_SPEED_TOP = RotationsPerSecond.of(0.0); //Placeholder

    public static final Current STATOR_LIMIT = Amps.of(0.0); //Placeholder, will need to be tuned
    public static final Current SUPPLY_LIMIT = Amps.of(0.0); //Placeholder, will need to be tuned

    public static String CANBUS = "MR.PATEL";

    public class Configurations{

        public class TopRoller{
            public static int MOTOR_ID = 0;
            public static final double SENSOR_TO_MECH_RATIO = 1.0;

            public static final Current STATOR_LIMIT = Amps.of(30.0); //Placeholder, will need to be tuned
            public static final Current SUPPLY_LIMIT = Amps.of(30.0); //Placeholder, will need to be tuned

            public static final double kP = 0.0;
            public static final double kI = 0.0;
            public static final double kD = 0.0;

            public static final double kS = 0.0;
            public static final double kG = 0.0;
            public static final double kV = 0.0; 
            public static final double kA = 0.0;

            public static final double EXPO_A = 0.0;
            public static final double EXPO_V = 0.0;

            public static final double CRUISE_VELOCITY = 80.0;
            public static final double MAX_ACCELERATION = 160.0;
        }

        public class BottomRoller{
            public static int MOTOR_ID = 0;
            public static final double SENSOR_TO_MECH_RATIO = 1.0;

            public static final Current STATOR_LIMIT = Amps.of(30.0); //Placeholder, will need to be tuned
            public static final Current SUPPLY_LIMIT = Amps.of(30.0); //Placeholder, will need to be tuned

            public static final double kP = 0.0;
            public static final double kI = 0.0;
            public static final double kD = 0.0;

            public static final double kS = 0.0;
            public static final double kG = 0.0;
            public static final double kV = 0.0; 
            public static final double kA = 0.0;

            public static final double EXPO_A = 0.0;
            public static final double EXPO_V = 0.0;

            public static final double CRUISE_VELOCITY = 80.0;
            public static final double MAX_ACCELERATION = 160.0;
        }
    }
}
