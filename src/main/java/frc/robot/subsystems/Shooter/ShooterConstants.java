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
        public static final InterpolatingDoubleTreeMap HOOD_LOOKUP_TABLE = new InterpolatingDoubleTreeMap();
        public static final InterpolatingDoubleTreeMap SHOOTER_LOOKUP_TABLE = new InterpolatingDoubleTreeMap();

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

        public static final AngularVelocity SHOOTER_HUB_SPEED        = RotationsPerSecond.of(26.0);
        public static final AngularVelocity SHOOTER_RENEE_SPEED      = RotationsPerSecond.of(32.0);
        public static final AngularVelocity SHOOTER_TOWER_SPEED      = RotationsPerSecond.of(44.0);
        public static final AngularVelocity SHOOTER_TRENCH_SPEED     = RotationsPerSecond.of(60.0);
        public static final AngularVelocity SHOOTER_BACK_SPEED       = RotationsPerSecond.of(50.0);
        public static final AngularVelocity SHOOTER_PASS_SPEED       = RotationsPerSecond.of(60.0);
        
        public static final Angle           HOOD_HUB_ANGLE           = Rotations.of(0.1);
        public static final Angle           HOOD_TOWER_ANGLE         = Rotations.of(0.3);
        public static final Angle           HOOD_TRENCH_ANGLE        = Rotations.of(0.6);
        public static final Angle           HOOD_BACK_ANGLE          = Rotations.of(0.425);
        public static final Angle           HOOD_PASS_ANGLE          = Rotations.of(0.85); // +0.525

        public static final Time            OFF_SET_TIME             = Seconds.of(0.1); 
        
        static{
            DIST_TIME_MAP.put(1.0,          HUB_TIME.in(Seconds));
            HOOD_LOOKUP_TABLE   .put(1.3,        HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(1.3,        SHOOTER_HUB_SPEED  .in(RotationsPerSecond));

            DIST_TIME_MAP.put(2.0, 1.0     +HUB_TIME.in(Seconds));
            HOOD_LOOKUP_TABLE   .put(1.77, 0.0  +HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(1.77, 2    +SHOOTER_HUB_SPEED  .in(RotationsPerSecond));

            DIST_TIME_MAP.put(3.0, 2.0     +HUB_TIME.in(Seconds));
            HOOD_LOOKUP_TABLE   .put(2.25, 0.15 +HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(2.25, 2    +SHOOTER_HUB_SPEED  .in(RotationsPerSecond));

            DIST_TIME_MAP.put(3.0, 3.0     +HUB_TIME.in(Seconds));
            HOOD_LOOKUP_TABLE   .put(2.95, 0.2  +HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(2.95, 4    +SHOOTER_HUB_SPEED  .in(RotationsPerSecond));

            HOOD_LOOKUP_TABLE   .put(3.75, 0.2  +HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(3.75, 8   +SHOOTER_HUB_SPEED  .in(RotationsPerSecond));

            HOOD_LOOKUP_TABLE   .put(4.05, 0.25 +HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(4.05, 8   +SHOOTER_HUB_SPEED  .in(RotationsPerSecond));
            
            HOOD_LOOKUP_TABLE   .put(4.95, 0.25 +HOOD_HUB_ANGLE     .in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(4.95, 12   +SHOOTER_HUB_SPEED  .in(RotationsPerSecond));

            //For Passing 
            HOOD_LOOKUP_TABLE   .put(5.85, 0.35 +HOOD_HUB_ANGLE.in(Rotations));
            SHOOTER_LOOKUP_TABLE.put(5.85, 38   +SHOOTER_HUB_SPEED.in(RotationsPerSecond));

            HOOD_LOOKUP_TABLE.put(10.65,0.65);
            SHOOTER_LOOKUP_TABLE.put(10.65, 100.0);
        }
    }
}
