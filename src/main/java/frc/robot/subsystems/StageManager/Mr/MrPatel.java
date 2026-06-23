package frc.robot.subsystems.StageManager.Mr;


import static frc.robot.RobotContainer.autoAlign;
import static frc.robot.RobotContainer.shootOnetheMove;
import static frc.robot.subsystems.Indexer.IndexerConstants.*;
import static frc.robot.subsystems.Intake.IntakeConstants.INTAKE_SPEED_BOTTOM;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.Throat.Throat;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Deploy.Deploy;
import frc.robot.subsystems.Shooter.ShootOnetheMove;

public class MrPatel {

    private Deploy deploy;
    private Indexer indexer;
    private Intake intake;
    private Shooter shooter;
    private Throat throat;

    public MrPatel(){
        this.deploy = deploy;
        this.indexer = indexer;
        this.intake = intake;
        this.shooter = shooter;
        this.throat = throat;
    }

    /**
     * Commands!
     * 1. reset
     * 2. idle
     * 3. begin intaking 
     * 4. outtake
     * 5. stop intake
     * 6. Retract Intake
     * 7. shoot
     * 8. shoot from everywhere
     */

    //Reset Robot's State - retract intake
    public Command reset(){
        return new ParallelCommandGroup(
            intake.stop().andThen(deploy.retract()),
            indexer.stop(),
            shooter.stop(),
            throat.stop()
        );
    }

    //Reset Robot State - doesn't retract intake
    public Command idle(){
        return new ParallelCommandGroup(
            intake.stop(),
            indexer.stop(),
            shooter.stop(),
            throat.stop()
        );
    }

    public Command beginIntaking(){
        return new SequentialCommandGroup(
            deploy.deploy(),
            intake.intake()
        );
    }

    public Command outTake(){
        return intake.outtake();
    }

    public Command stopIntake(){
        return new SequentialCommandGroup(
            intake.stop(),
            deploy.retract()
        );
    }

    public Command shoot(AngularVelocity shooterSpeed, Angle hoodAngle){
        return new ParallelCommandGroup(
            shooter.shoot(shooterSpeed, hoodAngle),
            Commands.waitSeconds(0.4).andThen(throat.feed()),
            Commands.waitSeconds(0.8).andThen(indexer.feedWithUnjam())
        );
    }

    public Command shootFromEverywhere(){
        return new ParallelCommandGroup(
            shooter.shootfromEveryWhere(),
            Commands.waitSeconds(0.4).andThen(throat.feed()),
            Commands.waitSeconds(0.8).andThen(indexer.feedWithUnjam())
        );
    }

    //Use this for actual bot implementation cause it got autoalign
    public Command shootWithAutoAlign(){
        return new ParallelCommandGroup(
            autoAlign.alignToHub(),
            shootFromEverywhere()
        );
    }


}
