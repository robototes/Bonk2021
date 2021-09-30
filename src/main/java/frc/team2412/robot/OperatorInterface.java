package frc.team2412.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.team2412.robot.commands.DriveCommand;
import frc.team2412.robot.commands.ShiftCommand;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

import java.util.function.Consumer;
import java.util.function.Function;

//This is the class in charge of all the buttons and joysticks that the drivers will use to control the robot
public class OperatorInterface {

	private static final int JOYSTICK_PORT = 0;

	// Joysticks
	public XboxController controller = new XboxController(JOYSTICK_PORT);

	public Button shiftButton = new Button(controller::getAButtonPressed);

	public Function<XboxController, DrivebaseSubsystem> driveType;

	// Constructor to set all of the commands and buttons
	public OperatorInterface(RobotContainer robotContainer) {

		driveType = robotContainer.drivebaseSubsystem::arcadeDrive;

		robotContainer.drivebaseSubsystem.setDefaultCommand(new DriveCommand(driveType, controller));

		shiftButton.whileHeld(new ShiftCommand(robotContainer.drivebaseSubsystem));

	}
}
