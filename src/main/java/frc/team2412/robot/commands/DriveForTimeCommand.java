package frc.team2412.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

public class DriveForTimeCommand extends CommandBase {
    public DrivebaseSubsystem subsystem;
    public Timer timer;
    public double time;
    public DriveForTimeCommand(DrivebaseSubsystem s, double t){
        subsystem = s;
        addRequirements(subsystem);
        timer = new Timer();
        time = t;
    }

    @Override
    public void initialize() {
        subsystem.drive(0.5, 0.5);
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.get() > time;
    }

    @Override
    public void end(boolean cancel) {
        subsystem.drive(0, 0);
        timer.reset();
    }
}
