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
        timer.start();
    }
    @Override
    public void execute(){
        if(timer.get()*2<time) subsystem.drive(timer.get()*2/time, -timer.get()*2/time);
        else subsystem.drive((time-timer.get())*2/time, -(time-timer.get())*2/time);
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
