package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Time;

public class IndexerConstants {

    public static final AngularVelocity UNJAMMED_SPEED = RotationsPerSecond.of(0.0); //Placeholder, will need to be tuned
    public static final Time JAME_TIME_TOLERANCE = Seconds.of(0.0); //Placeholder, will need to be tuned
    
    public static final Distance LEFT_TRIGGER = Meters.of(0.0); //Placeholder
    public static final Distance RIGHT_TRIGGER = Meters.of(0.0); //Placeholder

    public static final AngularVelocity FEED_SPEED_LEFT = RotationsPerSecond.of(0.0); //Placeholder, will need to be tuned
    public static final AngularVelocity FEED_SPEED_RIGHT = RotationsPerSecond.of(0.0); //Placeholder, will need to be tuned

}
