package frc.team2412.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team2412.robot.util.Configurable;

//This is the class in charge of all the motors, motor ids, and any other sensors the robot uses. 
//remember to declare robot container at the bottom of this class 
public class RobotMap {

	/*
	2   5
	3   6
	4   7
	 */
	public static WPI_TalonFX driveLeftMotor1, driveLeftMotor2, driveLeftMotor3;
	public static WPI_TalonFX driveRightMotor1, driveRightMotor2, driveRightMotor3;


	public static DoubleSolenoid driveShifter;

	
	static{

		driveLeftMotor1 = new WPI_TalonFX(2);
		driveLeftMotor2 = new WPI_TalonFX(3);
		driveLeftMotor3 = new WPI_TalonFX(4);

		driveRightMotor1 = new WPI_TalonFX(5);
		driveRightMotor2 = new WPI_TalonFX(6);
		driveRightMotor3 = new WPI_TalonFX(7);



//		driveLeftMotor1 = new WPI_TalonFX(0);
//		driveLeftMotor2 = new WPI_TalonFX(1);
//		driveLeftMotor3 = new WPI_TalonFX(2);
//
//		driveLeftMotor2.follow(driveLeftMotor1);
//		driveLeftMotor3.follow(driveLeftMotor2);
//
//		driveRightMotor1 = new WPI_TalonFX(3);
//		driveRightMotor2 = new WPI_TalonFX(4);
//		driveRightMotor3 = new WPI_TalonFX(5);
//
//		driveRightMotor1.setInverted(true);
//		driveRightMotor2.setInverted(true);
//		driveLeftMotor3.setInverted(true);r
//
//		driveRightMotor2.follow(driveRightMotor1);
//		driveLeftMotor3.follow(driveRightMotor1);

		driveShifter = new DoubleSolenoid(0, 1);

	}
}
