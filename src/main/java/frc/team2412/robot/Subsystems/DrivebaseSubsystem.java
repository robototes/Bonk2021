package frc.team2412.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivebaseSubsystem extends SubsystemBase {

	private WPI_TalonFX leftMotor;

	private WPI_TalonFX rightMotor;

	private double speedMod;

	public DrivebaseSubsystem(WPI_TalonFX rightMotor, WPI_TalonFX leftMotor) {
		this.rightMotor = rightMotor;
		this.rightMotor.setInverted(TalonFXInvertType.CounterClockwise);
		this.leftMotor = leftMotor;
		this.leftMotor.setInverted(TalonFXInvertType.Clockwise);
	}

	public void drive(Joystick joystick) {
		speedMod = -joystick.getRawAxis(3)/2+0.5;
		rightMotor.set((joystick.getY() + (Math.pow(joystick.getTwist(), 3)))*speedMod);
		leftMotor.set((joystick.getY() - (Math.pow(joystick.getTwist(), 3)))*speedMod);
	}

}
