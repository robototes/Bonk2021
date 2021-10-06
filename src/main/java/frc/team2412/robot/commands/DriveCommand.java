package frc.team2412.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

import java.util.function.Consumer;

public class DriveCommand extends RunCommand {
	public DriveCommand(DrivebaseSubsystem subsystem, XboxController joystick) {
		super(()->subsystem.current.accept(joystick), subsystem);
	}
	
}
