package frc.team2412.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.team2412.robot.commands.DriveCommand;
import frc.team2412.robot.commands.ShiftCommand;
import frc.team2412.robot.commands.VictorySpinCommand;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

import java.util.function.Consumer;
import java.util.function.Function;

//This is the class in charge of all the buttons and joysticks that the drivers will use to control the robot
public class OperatorInterface {

	// Joysticks
	public XboxController controller = new XboxController(0);

	public Button shiftButton = new Button(controller::getAButton);

	public Button spinButton = new Button(controller::getBButton);

	public Button climbUpButton = new Button(controller::getXButton);

	public Button climbDownButton = new Button(controller::getYButton);


	//	 Constructor to set all of the commands and buttons
	public OperatorInterface(RobotContainer robotContainer) {

		robotContainer.drivebaseSubsystem.setDefaultCommand(new DriveCommand(robotContainer.drivebaseSubsystem, controller));

		shiftButton.whenHeld(new ShiftCommand(robotContainer.drivebaseSubsystem));

		//spinButton.whenPressed(new VictorySpinCommand(robotContainer.drivebaseSubsystem, 5));

		climbUpButton.whenPressed(robotContainer.climbSubsystem::up).whenReleased(robotContainer.climbSubsystem::stop);
		climbDownButton.whenPressed(robotContainer.climbSubsystem::down).whenReleased(robotContainer.climbSubsystem::stop);

	}
}
