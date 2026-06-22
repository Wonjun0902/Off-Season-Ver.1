package frc.robot.subsystems.Throat;

import static frc.robot.subsystems.Indexer.IndexerConstants.FEED_SPEED_LEFT;
import static frc.robot.subsystems.Indexer.IndexerConstants.FEED_SPEED_RIGHT;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Throat extends SubsystemBase{

    private ThroatIO io;

    public Throat(ThroatIO io){
        this.io = io;
    }

    public Command feed(){
        return runEnd(() -> {
            io.setLeftSpeed(FEED_SPEED_LEFT);
            io.setRightSpeed(FEED_SPEED_RIGHT);
        },
        () -> io.stop());
    }

    public Command outFeed(){
        return runEnd(() -> {
            io.setLeftSpeed(FEED_SPEED_LEFT.unaryMinus());
            io.setRightSpeed(FEED_SPEED_RIGHT.unaryMinus());
        },
        () -> io.stop());
    }

    public Command stop(){
        return run(() -> io.stop());
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Right Throat Speed (rps): ", io.getRightSpeed().magnitude());
        SmartDashboard.putNumber("Left Throat Speed (rps): ", io.getLeftSpeed().magnitude());
    }
}
