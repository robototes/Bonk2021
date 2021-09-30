package frc.team2412.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivebaseSubsystem extends SubsystemBase {

	private WPI_TalonFX leftMotor;

	private WPI_TalonFX rightMotor;

	private DoubleSolenoid shifter;

	public DrivebaseSubsystem(WPI_TalonFX rightMotor, WPI_TalonFX leftMotor, DoubleSolenoid shifter) {
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;
		this.shifter = shifter;
	}

	public void drive(double left, double right) {
		rightMotor.set(right);
		leftMotor.set(left);
	}

	public DrivebaseSubsystem arcadeDrive(XboxController j){
		double y = j.getY(GenericHID.Hand.kLeft);
		double speedMod = -y/2+0.5;
		double turnMod = Math.pow(j.getX(GenericHID.Hand.kLeft), 3);
		drive(y - turnMod*speedMod,y + turnMod*speedMod);
		return this;
	}
	public DrivebaseSubsystem dumbDrive(XboxController j){
		drive(j.getY(GenericHID.Hand.kLeft), j.getY(GenericHID.Hand.kRight));
		return this;
	}

	public DrivebaseSubsystem gtaDrive(XboxController j){
		double y = j.getTriggerAxis(GenericHID.Hand.kLeft)-j.getTriggerAxis(GenericHID.Hand.kRight);
		double speedMod = -y/2+0.5;
		double turnMod = Math.pow(j.getX(GenericHID.Hand.kLeft), 3);
		drive(y - turnMod*speedMod,y + turnMod*speedMod);
		return this;
	}

	public void upShift(){
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	public void downShift(){
		shifter.set(DoubleSolenoid.Value.kReverse);
	}

}
