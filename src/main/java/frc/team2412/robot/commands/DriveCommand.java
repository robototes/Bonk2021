package frc.team2412.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

import java.util.function.Function;

public class DriveCommand extends CommandBase {
	

	private XboxController joystick;

	private Function<XboxController, DrivebaseSubsystem> driveCommand;
	
	public DriveCommand(Function<XboxController, DrivebaseSubsystem> command, XboxController joystick) {
		addRequirements(command.apply(joystick));
		this.joystick = joystick;
		driveCommand = command;
	}

	public void execute() {
		driveCommand.apply(joystick);
	}
	
	public boolean isFinished() {
		return false;
	}
	
	
}
