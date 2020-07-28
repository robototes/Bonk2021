package frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.team2412.robot.Commands.drive;

//This is the class in charge of all the buttons and joysticks that the drivers will use to control the robot
public class OI {

	private static final int JOYSTICK_PORT = 0;

	// Joysticks
	public Joystick driverStick = new Joystick(JOYSTICK_PORT);

	// Constructor to set all of the commands and buttons
	public OI(RobotContainer robotContainer) {

		robotContainer.drivebaseSubsystem.setDefaultCommand(new drive(robotContainer.drivebaseSubsystem, driverStick));
	
	}
}
