package frc.robot.subsystems.Deploy;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

public class DeployConstants {

    public static final AngularVelocity DEPOLY_SPEED = RotationsPerSecond.of(0.5); 

    public static final double FORWARD_LIMIT = 0.0;
    public static final double REVERSE_LIMIT = 0.0;

    public class Configurations{
        public static String CANBUS = "Kingstone";

        public class Deployer{
                public static final int MOTOR_ID = 0; //not correct value! 
                public static final double GEAR_RATIO = 1.0; //not correct value!
                public static final double ROTOR_TO_SENSOR_RATIO = 40.0 / 18.0 * 45.0; 

                public static final Current STATOR_LIMIT = Amps.of(30);
                public static final Current SUPPLY_LIMIT = Amps.of(30);

                public static final double kP = 6.0;
                public static final double kI = 0.0;
                public static final double kD = 0.0;

                public static final double kS = 0.0;
                public static final double kG = 0.0;
                public static final double kV = 0.0; // 10 volts for 80.5 rpm
                public static final double kA = 0.0;

                public static final Current CURRENT_FF = Amps.of(0.0); // If using TCFOC 

                public static final double EXPO_A = 0.0;
                public static final double EXPO_V = 0.0;

                public static final double CRUISE_VELOCITY = 0.0;
                public static final double MAX_ACCELERATION = 0.0; 
        }

        public class Positions{
                public static final Angle M1_ANGLE = Rotations.of(0.0); //Placeholder, will need to be tuned
                public static final Angle M2_ANGLE = Rotations.of(0.0); //Placeholder, will need to be tuned

                public static final Angle RETRACTED_ANGLE = Rotations.of(0.0); //Placeholder, will need to be tuned
                public static final Angle DEPLOYED_ANGLE = Rotations.of(0.0); //Placeholder, will need to be tuned

                public static final Angle DEPLOY_TOLERANCE = Rotations.of(0.0); //Placeholder, will need to be tuned
        }

        public class CanCoder{
            public static final int CANCODER_ID = 0; //not correct value!
            public static final double CANCODER_OFFSET = 0.0;
            public static final SensorDirectionValue CANCODER_DIRECTION = SensorDirectionValue.CounterClockwise_Positive;
            public static final FeedbackSensorSourceValue CANCODER_TYPE = FeedbackSensorSourceValue.FusedCANcoder;
        }
    }

}
