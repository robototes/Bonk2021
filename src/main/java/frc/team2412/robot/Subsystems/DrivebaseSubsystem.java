package frc.team2412.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.numbers.N2;
import frc.team2412.robot.Robot;

public class DrivebaseSubsystem extends SubsystemBase {
	private static final double TRACK_WIDTH = 0.381 * 2; // meters
	private static final double WHEEL_RADIUS = 0.0508; // meters
	private static final int ENCODER_RESOLUTION = 4096;

	private final WPI_TalonFX leftMotor;
	private final WPI_TalonFX rightMotor;
	private final AnalogGyro m_gyro = new AnalogGyro(0);

	private final DifferentialDriveOdometry odometry;

	  // Simulation classes help us simulate our robot
	  private final AnalogGyroSim m_gyroSim = new AnalogGyroSim(m_gyro);
	  private double leftSimDistance = 0.0;
	  private double rightSimDistance = 0.0;
	  private final Field2d m_fieldSim = new Field2d();
	  private final LinearSystem<N2, N2, N2> m_drivetrainSystem
	  	= LinearSystemId.identifyDrivetrainSystem(1.98, 0.2, 1.5, 0.3);
	  private final DifferentialDrivetrainSim m_drivetrainSimulator
		= new DifferentialDrivetrainSim(m_drivetrainSystem, DCMotor.getFalcon500(1), 8, TRACK_WIDTH, WHEEL_RADIUS, null);
		  
	public DrivebaseSubsystem(WPI_TalonFX rightMotor, WPI_TalonFX leftMotor) {
		TalonFXConfiguration motorConfig = new TalonFXConfiguration();
		motorConfig.statorCurrLimit.currentLimit = 5; // Amps
		motorConfig.statorCurrLimit.triggerThresholdTime = 0.5; // Seconds

		this.rightMotor = rightMotor;
		this.rightMotor.configAllSettings(motorConfig);
		this.rightMotor.setInverted(TalonFXInvertType.CounterClockwise);
		this.rightMotor.setSelectedSensorPosition(0);
		this.leftMotor = leftMotor;
		this.leftMotor.configAllSettings(motorConfig);
		this.leftMotor.setInverted(TalonFXInvertType.Clockwise);
		this.leftMotor.setSelectedSensorPosition(0);

		this.odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());

		SmartDashboard.putData("Field", m_fieldSim);
	}

	public void updateOdometry() {
		final double leftDistance = Robot.isSimulation() ? leftSimDistance : this.leftMotor.getSelectedSensorPosition() * 2 * Math.PI * WHEEL_RADIUS / ENCODER_RESOLUTION;
		final double rightDistance = Robot.isSimulation() ? rightSimDistance : this.rightMotor.getSelectedSensorPosition() * 2 * Math.PI * WHEEL_RADIUS / ENCODER_RESOLUTION;
		odometry.update(m_gyro.getRotation2d(), leftDistance, rightDistance);
	}

	public void drive(Joystick joystick) {
		final double speedMod = -joystick.getRawAxis(3)/2+0.5;
		rightMotor.set((joystick.getY() + (Math.pow(joystick.getTwist(), 3)))*speedMod);
		leftMotor.set((joystick.getY() - (Math.pow(joystick.getTwist(), 3)))*speedMod);
	}

	public void simulationPeriodic() {
		// To update our simulation, we set motor voltage inputs, update the
		// simulation, and write the simulated positions and velocities to our
		// simulated encoder and gyro. We negate the right side so that positive
		// voltages make the right side move forward.
		m_drivetrainSimulator.setInputs(leftMotor.get() * RobotController.getInputVoltage(),
			rightMotor.get() * RobotController.getInputVoltage());
		m_drivetrainSimulator.update(0.02);
	
		leftSimDistance = m_drivetrainSimulator.getLeftPositionMeters();
		rightSimDistance = m_drivetrainSimulator.getRightPositionMeters();
		m_gyroSim.setAngle(-m_drivetrainSimulator.getHeading().getDegrees());
	  }
	
	  public void periodic() {
		updateOdometry();
		m_fieldSim.setRobotPose(odometry.getPoseMeters());
	  }
}
