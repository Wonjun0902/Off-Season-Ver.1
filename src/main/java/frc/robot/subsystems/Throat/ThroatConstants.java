package frc.robot.subsystems.Throat;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public class ThroatConstants {

    public static final AngularVelocity FEED_SPEED_LEFT = RotationsPerSecond.of(0.0);
    public static final AngularVelocity FEED_SPEED_RIGHT = RotationsPerSecond.of(0.0);

    public static final double STATOR_CURRENT_LIMIT = 0.0;
    public static final double SUPPLY_CURRENT_LIMIT =-0.0;

    public class Configurations{
        public static String CANBUS = "MR.PATEL";

        public class Throat{
            public class Left{
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

                public static final double CURRENT_FF = 26.0;

                public static final int FOLLOWER_LEFT_ID = 0;
            }

            public class Right{
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

                public static final double CURRENT_FF = 26.0;

                public static final int FOLLOWER_RIGHT_ID = 0;
            }
        }
    }
}
