package frc.team2412.robot.Commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2412.robot.Subsystems.DrivebaseSubsystem;

public class drive extends CommandBase {
	
	private DrivebaseSubsystem drivebaseSubsystem;
	
	private Joystick joystick;
	
	public drive(DrivebaseSubsystem drivebaseSubsystem, Joystick joystick) {
		addRequirements(drivebaseSubsystem);
		this.drivebaseSubsystem = drivebaseSubsystem;
		this.joystick = joystick;
	}

	public void execute() {
		drivebaseSubsystem.drive(joystick);
	}
	
	public boolean isFinished() {
		return false;
	}
	
	
}
