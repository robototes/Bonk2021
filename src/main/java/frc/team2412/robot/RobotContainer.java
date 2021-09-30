package frc.team2412.robot;

import frc.team2412.robot.subsystems.DrivebaseSubsystem;

// this is the class for containing all the subsystems and OI of the robot
public class RobotContainer {

	public DrivebaseSubsystem drivebaseSubsystem;

	public RobotContainer() {

		drivebaseSubsystem = new DrivebaseSubsystem(RobotMap.driveRightMotor1, RobotMap.driveLeftMotor1, RobotMap.driveShifter);

	}
}
