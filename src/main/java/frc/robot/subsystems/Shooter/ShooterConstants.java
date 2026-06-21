package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Time;

public class ShooterConstants {

    public static double FUEL_SPEED = 1.0; //m/s

    public class AngleLockConfig{
        public static final AngularVelocity MAX_ROT_ANGLELOCK = RotationsPerSecond.of(0.0);

        public static final Angle ROT_ANGLE_TOLERANCE = Rotations.of(0.0);

        public static final double ANGLELOCK_P = 0.0;
        public static final double ANGLELOCK_I = 0.0;
        public static final double ANGLELOCK_D = 0.0;
    }

    public class Field {
        public static final AprilTagFieldLayout FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);
    
        public static final double FIELD_WIDTH = Inches.of(47.0).in(Meters);
        public static final double FIELD_LENGTH = FIELD_LAYOUT.getFieldLength();
        public static final Translation2d BLUE_HUB_POSE = new Translation2d(
            4.625, // 4.625594, 
            4.034536 // 4.034536
        );
        public static final Translation2d RED_HUB_POSE = new Translation2d(
            11.905,// 12.896088,
            4.034536 // 4.034536
        );
    }

    public class LookupTables{
        public static final InterpolatingDoubleTreeMap DIST_TIME_MAP = new InterpolatingDoubleTreeMap();
        public static final InterpolatingDoubleTreeMap SPEEDS_INCREMENT_MAP = new InterpolatingDoubleTreeMap();

        public static final double          HALF_HUB                 = 47.0 / 2.0;
        public static final double          HALF_BUMPER              = 16.5;
        public static final double          HALF_ROBOT_NO_BUMPER     = Centimeters.of(34.25).in(Inches);

        public static final Distance        HUB_POSITION             = Inches.of(8.75 + HALF_BUMPER+HALF_HUB);
        public static final Distance        TOWER_FRONT_POSITION     = Inches.of(79   + HALF_BUMPER+HALF_HUB);
        public static final Distance        TRENCH_POSITION          = Inches.of(1000 + HALF_BUMPER+HALF_HUB);
        public static final Distance        TOWER_BACK_POSITION      = Inches.of(2000 + HALF_BUMPER+HALF_HUB);

        public static final Time            HUB_TIME                 = Seconds.of(0.0); //NOT TRUE!!
        public static final Time            TOWER_FRONT_TIME         = Seconds.of(1.0); //NOT TRUE!!
        public static final Time            TRENCH_TIME              = Seconds.of(2.0); //NOT TRUE!!
        public static final Time            TOWER_BACK_TIME          = Seconds.of(3.0); //NOT TRUE!! 

        public static final AngularVelocity  IDLE_VELOCITY           = RotationsPerSecond.of(0.0);
        public static final AngularVelocity  HUB_VELOCITY            = RotationsPerSecond.of(1.0);
        public static final AngularVelocity  TOWER_FRON_VELOCITY     = RotationsPerSecond.of(2.0);
        public static final AngularVelocity  TRENCH_VELOCITY         = RotationsPerSecond.of(3.0);
        public static final AngularVelocity  TOWER_BACK_VELOCITY     = RotationsPerSecond.of(4.0);

        public static final Time            OFF_SET_TIME             = Seconds.of(0.1); 
        
        static{
            DIST_TIME_MAP.put(1.0,          HUB_TIME.in(Seconds));
            SPEEDS_INCREMENT_MAP.put(1.0,       IDLE_VELOCITY.in(RotationsPerSecond));

            DIST_TIME_MAP.put(2.0, 1.0     +HUB_TIME.in(Seconds));
            SPEEDS_INCREMENT_MAP.put(2.0, 1.0  +IDLE_VELOCITY.in(RotationsPerSecond));

            DIST_TIME_MAP.put(3.0, 2.0     +HUB_TIME.in(Seconds));
            SPEEDS_INCREMENT_MAP.put(2.0, 2.0  +IDLE_VELOCITY.in(RotationsPerSecond));

            DIST_TIME_MAP.put(3.0, 3.0     +HUB_TIME.in(Seconds));
            SPEEDS_INCREMENT_MAP.put(2.0, 3.0  +IDLE_VELOCITY.in(RotationsPerSecond));
        }
    }
}
