package frc.team2412.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.team2412.robot.util.Configurable;

//This is the class in charge of all the motors, motor ids, and any other sensors the robot uses. 
//remember to declare robot container at the bottom of this class 
public class RobotMap {

	/*
	0   3
	1   4
	2   5
	 */
	public static WPI_TalonFX driveLeftMotor1, driveLeftMotor2, driveLeftMotor3;
	public static WPI_TalonFX driveRightMotor1, driveRightMotor2, driveRightMotor3;

	public static DoubleSolenoid driveShifter;

	public static PWMTalonSRX climbMotor;

	public static Configurable<WPI_TalonFX> leftConfig = ()->driveRightMotor1, rightConfig = ()->driveLeftMotor2;
	
	static{

		driveLeftMotor1 = new WPI_TalonFX(2);
		driveLeftMotor2 = new WPI_TalonFX(3);
		driveLeftMotor3 = new WPI_TalonFX(4);

		driveRightMotor1 = new WPI_TalonFX(5);
		driveRightMotor2 = new WPI_TalonFX(6);
		driveRightMotor3 = new WPI_TalonFX(7);

		driveRightMotor1.setInverted(true);
		driveRightMotor2.setInverted(true);
		driveLeftMotor3.setInverted(true);

		leftConfig.apply(driveLeftMotor2::follow).apply(driveLeftMotor3::follow);

		rightConfig.apply(driveRightMotor2::follow).apply(driveRightMotor3::follow);

		driveShifter = new DoubleSolenoid(0, 1);
		
		climbMotor = new PWMTalonSRX(0);

	}
}
