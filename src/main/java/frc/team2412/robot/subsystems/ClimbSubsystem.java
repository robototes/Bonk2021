package frc.team2412.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {
    public PWMTalonSRX motor;
    public ClimbSubsystem(PWMTalonSRX m){
        motor = m;
    }
    public void setMotor(double v){
        motor.set(v);
    }
    public void up(){
        setMotor(1);
    }
    public void down(){
        setMotor(-1);
    }
    public void stop(){
        setMotor(0);
    }

}
