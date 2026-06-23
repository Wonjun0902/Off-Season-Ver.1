package frc.robot;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import static frc.robot.RobotContainer.indexer;
import static frc.robot.RobotContainer.intake;
import static frc.robot.RobotContainer.mrPatel;
import static frc.robot.RobotContainer.shooter;
import static frc.robot.RobotContainer.throat;

public class OperatorControls {

    public static CommandXboxController OPERATOR_CONTROLLER = new CommandXboxController(1);

    public static void ConfigureBindings(){
        OPERATOR_CONTROLLER.a().onTrue(mrPatel.beginIntaking());
    }
}
