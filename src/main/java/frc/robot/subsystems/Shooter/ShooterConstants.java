package frc.robot.subsystems.Shooter;

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

}
