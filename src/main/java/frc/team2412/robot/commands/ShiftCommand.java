package frc.team2412.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

public class ShiftCommand extends CommandBase {

	private DrivebaseSubsystem subsystem;

	public ShiftCommand(DrivebaseSubsystem s) {
		subsystem = s;
	}

	@Override
	public void initialize() {
		subsystem.upShift();
	}

	@Override
	public void end(boolean interrupted) {
		subsystem.downShift();
	}

}
