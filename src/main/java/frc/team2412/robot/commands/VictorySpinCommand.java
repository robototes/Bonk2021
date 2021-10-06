package frc.team2412.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2412.robot.subsystems.DrivebaseSubsystem;

public class VictorySpinCommand extends CommandBase {
    public DrivebaseSubsystem subsystem;
    public Timer timer;
    public double time;
    public VictorySpinCommand(DrivebaseSubsystem s, double t){
        subsystem = s;
        addRequirements(subsystem);
        timer = new Timer();
        time = t;
    }

    @Override
    public void initialize() {
        subsystem.drive(timer.get()/time/2, -timer.get()/time/2);
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
