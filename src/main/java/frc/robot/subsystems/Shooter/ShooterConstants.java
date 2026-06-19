package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public class ShooterConstants {

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
}
