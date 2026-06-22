package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Shooter.ShooterConstants.Shooter.Right; 
import frc.robot.subsystems.Shooter.ShooterConstants.Shooter.Left; 
import frc.robot.subsystems.Shooter.ShooterConstants.Hood;

public class ShooterIOSim implements ShooterIO{

    private DCMotor gearboxes = DCMotor.getKrakenX60(2);
    private DCMotorSim HOOD = new DCMotorSim(LinearSystemId.createDCMotorSystem(gearboxes, 0.00001, 1), gearboxes);
    private FlywheelSim FLY_WHEEL_LEFT = new FlywheelSim(LinearSystemId.createFlywheelSystem(gearboxes, 0.1, 1), gearboxes);
    private FlywheelSim FLY_WHEEL_RIGHT = new FlywheelSim(LinearSystemId.createFlywheelSystem(gearboxes, 0.1, 1), gearboxes);
    private double shooterTarget = 0.0;
    private double hoodTarget = 0.0;

    public ShooterIOSim(){}

    @Override
    public void setShooterSpeed(AngularVelocity velocity){
        shooterTarget = velocity.in(RotationsPerSecond);
    }

    @Override
    public void setHoodAngle(Angle angle){
        hoodTarget = angle.in(Rotation);
    }

    @Override
    public void stop(){
        shooterTarget = 0.0;
        hoodTarget = 0.0;
    }

    @Override
    public AngularVelocity getLeftSpeed(){
        return FLY_WHEEL_LEFT.getAngularVelocity();
    }

    @Override
    public AngularVelocity getRightSpeed(){
        return FLY_WHEEL_RIGHT.getAngularVelocity();
    }

    @Override
    public Angle getHoodAngle(){
        return HOOD.getAngularPosition();
    }

    @Override
    public void periodic(){
        double leftError = shooterTarget - FLY_WHEEL_LEFT.getAngularVelocity().in(RotationsPerSecond);
        double rightError = shooterTarget - FLY_WHEEL_RIGHT.getAngularVelocity().in(RotationsPerSecond);
        double hoodError = hoodTarget - HOOD.getAngularPosition().in(Rotation);
        FLY_WHEEL_LEFT.setInput(MathUtil.clamp(Left.kP*leftError,-12,12));
        FLY_WHEEL_RIGHT.setInput(MathUtil.clamp(Right.kP*rightError,-12,12));
        HOOD.setInput(Math.min(Hood.kP*hoodError, -12));
        SmartDashboard.putNumber("Left Shooter Speed (rps): ", leftError);
        SmartDashboard.putNumber("Right Shooter Speed (rps): ", rightError);
        SmartDashboard.putNumber("Left Shoter Speed (rps): ", FLY_WHEEL_LEFT.getAngularVelocity().magnitude());
        SmartDashboard.putNumber("Right Shooter Speed (rps): ", FLY_WHEEL_RIGHT.getAngularVelocity().magnitude());
        SmartDashboard.putNumber("Hood Angle (rot): ", HOOD.getAngularPosition().magnitude());
        SmartDashboard.putNumber("Hood Error (rot): ", hoodError);
        FLY_WHEEL_LEFT.update(0.02);
        FLY_WHEEL_RIGHT.update(0.02);
        HOOD.update(0.02);
    }
}
