package frc.team2412.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.*;
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
import frc.team2412.robot.util.Configurable;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class DrivebaseSubsystem extends SubsystemBase implements Loggable {

	private static final double TRACK_WIDTH = 0.381 * 2; // meters
	private static final double WHEEL_RADIUS = 0.0508; // meters
	private static final int ENCODER_RESOLUTION = 4096;
	@Log.SpeedController(name="Drive Left Motor")
	private WPI_TalonFX leftMotor;
	@Log.SpeedController(name="Drive Right Motor")
	private WPI_TalonFX rightMotor;
	@Log(name="Drive Shifter")
	private DoubleSolenoid shifter;

	@Log.Gyro(name="Drive Gyro")
	private final AnalogGyro gyro = new AnalogGyro(0);

	private DifferentialDriveOdometry odometry;

	// Simulation classes help us simulate our robot
	private final AnalogGyroSim gyroSim = new AnalogGyroSim(gyro);
	private double leftSimDistance = 0.0;
	private double rightSimDistance = 0.0;
	private final Field2d fieldSim = new Field2d();
	private final LinearSystem<N2, N2, N2> drivetrainSystem
			= LinearSystemId.identifyDrivetrainSystem(1.98, 0.2, 1.5, 0.3);
	private final DifferentialDrivetrainSim drivetrainSimulator
			= new DifferentialDrivetrainSim(drivetrainSystem, DCMotor.getFalcon500(1), 8, TRACK_WIDTH, WHEEL_RADIUS, null);

	private Configurable<TalonFXConfiguration> config = TalonFXConfiguration::new;


	public Consumer<XboxController> current = this::arcadeDrive;

	@Config(name="Drive Control Type", numGridRows = 1)
	public void setDriveMode(boolean arcade, boolean dumb, boolean gta){
		if(gta) current = this::gtaDrive;
		else if(dumb) current = this::dumbDrive;
		else if(arcade) current = this::arcadeDrive;
	}

	public DrivebaseSubsystem(WPI_TalonFX rightMotor, WPI_TalonFX leftMotor, DoubleSolenoid shifter) {

		config.apply(c->{
			c.statorCurrLimit.currentLimit=5;
			c.statorCurrLimit.triggerThresholdTime = 0.5;
		});

		config.apply(rightMotor::configAllSettings);
		this.rightMotor = rightMotor;
		this.rightMotor.setSelectedSensorPosition(0);
		this.rightMotor.setInverted(true);

		config.apply(leftMotor::configAllSettings);
		this.leftMotor = leftMotor;
		this.leftMotor.setSelectedSensorPosition(0);

		this.odometry = new DifferentialDriveOdometry(gyro.getRotation2d());

		SmartDashboard.putData("Field", fieldSim);

		this.shifter = shifter;
	}

	public void drive(double left, double right) {
		rightMotor.set(right);
		leftMotor.set(left);
	}

	public void arcadeDrive(XboxController j){
		double y = j.getY(GenericHID.Hand.kLeft);
		double speedMod = -y/2+0.5;
		double turnMod = Math.pow(j.getX(GenericHID.Hand.kLeft), 3);
		drive(y - turnMod*speedMod,y + turnMod*speedMod);
	}
	public void dumbDrive(XboxController j){
		drive(j.getY(GenericHID.Hand.kLeft), j.getY(GenericHID.Hand.kRight));
	}

	public void gtaDrive(XboxController j){
		double y = j.getTriggerAxis(GenericHID.Hand.kLeft)-j.getTriggerAxis(GenericHID.Hand.kRight);
		double speedMod = -y/2+0.5;
		double turnMod = Math.pow(j.getX(GenericHID.Hand.kLeft), 3);
		drive(y - turnMod*speedMod,y + turnMod*speedMod);
	}

	public void upShift(){
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	public void downShift(){
		shifter.set(DoubleSolenoid.Value.kReverse);
	}

	public void updateOdometry() {
		final double leftDistance = Robot.isSimulation() ? leftSimDistance : this.leftMotor.getSelectedSensorPosition() * 2 * Math.PI * WHEEL_RADIUS / ENCODER_RESOLUTION;
		final double rightDistance = Robot.isSimulation() ? rightSimDistance : this.rightMotor.getSelectedSensorPosition() * 2 * Math.PI * WHEEL_RADIUS / ENCODER_RESOLUTION;
		odometry.update(gyro.getRotation2d(), leftDistance, rightDistance);
	}

	public void simulationPeriodic() {
		// To update our simulation, we set motor voltage inputs, update the
		// simulation, and write the simulated positions and velocities to our
		// simulated encoder and gyro. We negate the right side so that positive
		// voltages make the right side move forward.
		drivetrainSimulator.setInputs(leftMotor.get() * RobotController.getInputVoltage(),
				rightMotor.get() * RobotController.getInputVoltage());
		drivetrainSimulator.update(0.02);

		leftSimDistance = drivetrainSimulator.getLeftPositionMeters();
		rightSimDistance = drivetrainSimulator.getRightPositionMeters();
		gyroSim.setAngle(-drivetrainSimulator.getHeading().getDegrees());
	}

	public void periodic() {
		updateOdometry();
		fieldSim.setRobotPose(odometry.getPoseMeters());
	}
}
