package frc.team2412.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivebaseSubsystem extends SubsystemBase {

	private WPI_TalonFX leftMotor;

	private WPI_TalonFX rightMotor;

	public DrivebaseSubsystem(WPI_TalonFX rightMotor, WPI_TalonFX leftMotor) {
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;
	}

	public void drive(Joystick joystick) {
		rightMotor.set(joystick.getY() - (Math.pow(joystick.getTwist(), 3)));
		leftMotor.set(joystick.getY() + (Math.pow(joystick.getTwist(), 3)));
	}

}
