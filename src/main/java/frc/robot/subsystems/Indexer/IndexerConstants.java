package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public class IndexerConstants {

    public static final AngularVelocity UNJAMMED_SPEED = RotationsPerSecond.of(0.0); //Placeholder, will need to be tuned
    public static final Time JAME_TIME_TOLERANCE = Seconds.of(0.0); //Placeholder, will need to be tuned
    
    public static final Distance LEFT_TRIGGER = Meters.of(0.0); //Placeholder
    public static final Distance RIGHT_TRIGGER = Meters.of(0.0); //Placeholder

    public static final AngularVelocity FEED_SPEED_LEFT = RotationsPerSecond.of(0.0); //Placeholder, will need to be tuned
    public static final AngularVelocity FEED_SPEED_RIGHT = RotationsPerSecond.of(0.0); //Placeholder, will need to be tuned

    public static final double STATOR_LIMIT = 0.0; //Placeholder, will need to be tuned
    public static final double SUPPLY_LIMIT = 0.0; //Placeholder, will need to be tuned

    public class Configurations{
        public static String CANBUS = "MR.PATEL";

        public class Spindexer{
             public class Left{
            //TODO: Tune all of these values, cause all of these are placeholders.
                public static final int MOTOR_ID = 0;
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

                public static final AngularVelocity CRUISE_VELOCITY = RotationsPerSecond.of(80.0);
                public static final AngularAcceleration MAX_ACCELERATION = RotationsPerSecondPerSecond.of(160.0);
        }

            public class Right{
                //TODO: Tune all of these values, cause all of these are placeholders.
                public static final int MOTOR_ID = 0; 
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

        public class Canranges{
            public static final int LEFT_CANRANGE_ID = 0; //Change later
            public static final int RIGHT_CANRANGE_ID = 0; //Change later
        }
    }
}
