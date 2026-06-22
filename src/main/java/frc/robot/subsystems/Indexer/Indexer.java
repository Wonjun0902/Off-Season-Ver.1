package frc.robot.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static edu.wpi.first.units.Units.Seconds;
import static frc.robot.subsystems.Indexer.IndexerConstants.*;

public class Indexer extends SubsystemBase {
    private IndexerIO io;

    //Logic: We'll have two triggers, one for left and one for right. 
    public final Trigger LeftIsJammed;
    public final Trigger RightIsJammed;

    //Debounce Triggers: If there is a ball longer than 0.5 seconds, then we'll return true for debounce left and right
    //This is basically for logical and filtering purposes so that we don't have to worry about the ball bouncing around and causing the triggers to rapidly turn on and off.
    public Trigger debouncedLeft, debouncedRight; 

    public Indexer(IndexerIO io) {
        this.io = io;

        LeftIsJammed = new Trigger(() -> io.getLeftRange().lt(LEFT_TRIGGER)); //Js a placeholder(hardcoded)
        RightIsJammed = new Trigger(() -> io.getRightRange().lt(RIGHT_TRIGGER)); //Js a placeholder(hardcoded)
        
        debouncedLeft = LeftIsJammed.debounce(JAME_TIME_TOLERANCE.in(Seconds));
        debouncedRight = RightIsJammed.debounce(JAME_TIME_TOLERANCE.in(Seconds));// For both left and right, might need to figure the most optimal time
    }
   
    public Command feed(){
        return runEnd(() -> {
            io.spinSpeedLeft(FEED_SPEED_LEFT);
            io.spinSpeedRight(FEED_SPEED_RIGHT);
        }
        , () -> io.Stop());
    }

    public Command outFeed(){
        return runEnd(() -> {
            io.spinSpeedLeft(FEED_SPEED_LEFT.times(-1));
            io.spinSpeedRight(FEED_SPEED_RIGHT.times(-1));
        }
        , () -> io.Stop());
    }

    public Command feedWithUnjam(){ 
       
        Command unjamLeft = run(() -> 
            io.spinSpeedLeft(UNJAMMED_SPEED)
        ).until(() -> !LeftIsJammed.getAsBoolean());

        Command unjamRight = run(() -> 
            io.spinSpeedRight(UNJAMMED_SPEED)
        ).until(() -> !RightIsJammed.getAsBoolean());

        Command feedleft = run(() -> 
            io.spinSpeedLeft(FEED_SPEED_LEFT));

        Command feedRight = run(() -> 
            io.spinSpeedRight(FEED_SPEED_RIGHT));

        //LEFT UNJAM & FEED LOGIC
        Command leftLogic = Commands.either(unjamLeft, feedleft, debouncedLeft);

        //RIGHT UNJAM & FEED LOGIC
        Command rightLogic = Commands.either(unjamRight, feedRight, debouncedRight);

        return Commands.parallel(leftLogic, rightLogic).repeatedly().finallyDo(() -> io.Stop());
    }

    public Command Stop(){
        return runOnce(() -> io.Stop());
    }

    public Command feedForTime(double seconds){
        return run(() -> {
            io.spinSpeedLeft(FEED_SPEED_LEFT);
            io.spinSpeedRight(FEED_SPEED_RIGHT);
        }).withTimeout(seconds);
    }

    public Command outFeedForTime(double seconds){
        return run(() -> {
            io.spinSpeedLeft(FEED_SPEED_LEFT.times(-1));
            io.spinSpeedRight(FEED_SPEED_RIGHT.times(-1));
        }).withTimeout(seconds);
    }   

    public Command feedWithUnjamForTime(double seconds){
        Command unjamLeft = run(() -> 
            io.spinSpeedLeft(UNJAMMED_SPEED)
        ).until(() -> !LeftIsJammed.getAsBoolean());

        Command unjamRight = run(() -> 
            io.spinSpeedRight(UNJAMMED_SPEED)
        ).until(() -> !RightIsJammed.getAsBoolean());

        Command feedleft = run(() -> 
            io.spinSpeedLeft(FEED_SPEED_LEFT));

        Command feedRight = run(() -> 
            io.spinSpeedRight(FEED_SPEED_RIGHT));

        //LEFT UNJAM & FEED LOGIC
        Command leftLogic = Commands.either(unjamLeft, feedleft, debouncedLeft);

        //RIGHT UNJAM & FEED LOGIC
        Command rightLogic = Commands.either(unjamRight, feedRight, debouncedRight);

        return Commands.parallel(leftLogic, rightLogic).withTimeout(seconds).finallyDo(() -> io.Stop());
    }

    @Override
    public void periodic() {
        io.periodic();

        SmartDashboard.putBoolean("Left Jammed", LeftIsJammed.getAsBoolean());
        SmartDashboard.putBoolean("Right Jammed", RightIsJammed.getAsBoolean());
        SmartDashboard.putString("Mr. Patel's Favorite Color", "Green");
    }
}