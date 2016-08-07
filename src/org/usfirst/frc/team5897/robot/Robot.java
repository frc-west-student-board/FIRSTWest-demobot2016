/*
 * Tentative code for the FRC West 2016 Demo Bot. Contained elements adapted 
 * from APEX Robotics' 2016 Impulse Code - specifically the drivetrain class. 
 * 
 * Robot assembled in through a collaboration between: 
 * 
 */

package org.usfirst.frc.team5897.robot;

import java.util.HashMap;
import java.util.Map;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import com.ni.vision.NIVision.Image;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/*
 * Standard order of motors in code is 'FL,BL,FR,BR'
 */

public class Robot extends IterativeRobot {

	boolean ran;

	int autonomousNumber = 1; // Number of auto mode running

	/**
	 * Values For Autonomous Number 1: Forward Auto 2: Portaculus Auto 3:Chivale
	 * de Frise Auto 4: Low Bar Auto: Shooting 5: Low Bar Auto: Not Shooting 6:
	 * Troubleshoot Auto
	 */

	// -----------------code auto generated for autonomous ------------//
	SmartDashboard dash = new SmartDashboard();
	// ----------------------------------------------------------------//

	int session;
	Image frame;

	// Definition of classes
	DriveTrain Driver; // reference to the driver class

	final public Joystick Joy1 = new Joystick(0); // first joystick;
	final public Joystick Joy2 = new Joystick(1); // second joystick;

	Timer time = new Timer();
	Timer buzz = new Timer();
	Timer Scaletimer = new Timer();

	boolean upPresed = false;
	boolean downPressed = false;
	boolean leftPressed = false;
	boolean rightPressed = false;
	boolean yPressed = false;
	boolean bPressed = false;

	boolean ScaleIsIdle;
	boolean LiftIsIdle;
	boolean LevelIsIdle;

	final double deadzoneValue = .25; // the zone in which the joystick
										// registers the
										// value as zero

	double speed; // the variable to which speed will be bound to
	double turn; // the variable to which the turning value will be bound to

	String autoSelected;
	SendableChooser chooser;

	public final Map<String, Integer> joyMap = new HashMap<String, Integer>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		buzz.start();
		joyMap.put("a", 1);
		joyMap.put("b", 2);
		joyMap.put("x", 3);
		joyMap.put("y", 4);
		joyMap.put("LB", 5);
		joyMap.put("RB", 6);
		joyMap.put("back", 7);
		joyMap.put("start", 8);
		joyMap.put("L3", 9);
		joyMap.put("R3", 10);
		joyMap.put("LX", 0);
		joyMap.put("LY", 1);
		joyMap.put("LT", 2);
		joyMap.put("RT", 3);
		joyMap.put("RX", 4);
		joyMap.put("RY", 5);

		Driver = new DriveTrain();

		dashboard(); // this sets the initial value for the dashboard
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
		time.reset();
		time.start();
		ran = false;
		// Error checking to prevent crashing
		if (dash.getString("DB/String 0") != null) {
			autonomousNumber = Integer.valueOf(dash.getString("DB/String 0"));
		} else {
			autonomousNumber = 0;
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		dash.putNumber("DB/Slider 0", autonomousNumber);
		switch (autonomousNumber) {
		case 0:
			// doNothingAuto(1f);
			break;
		case 1:
			// forwardAuto(4f);
			break;
		case 2:
			// portcullisAuto(1, 3.4f, 4.4f, 7);
			break;
		case 3:
			// seesawAuto(2.4f, 3.0f, 6, 7);
			break;
		case 4:
			// lowbarShoot(1, 9.125f, 10.325f, 10.825f, 15.173f, 16.173f);
		case 5:
			// lowbarNoShoot(1, 9.125f, 10.325f);
		case 6:
			// selfTest();
		}
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		speed = (joystickDeadzone(Joy1.getRawAxis(joyMap
				.get("LY")))); /*
								 * the speed variable is set to the y value of
								 * the left stick on the first joystick
								 */
		turn = (joystickDeadzone(Joy1.getRawAxis(joyMap
				.get("RX")))); /*
								 * the turn variable is set to the x value of
								 * the right stick on the first joystick
								 */

		Driver.drive(speed,
				turn); /*
						 * the method that controls all the drivetrain motors to
						 * make the robot drive given the speed and turning
						 */

		dashboard(); // refresh the dashboard

	}

	public double joystickDeadzone(double joystickValue) { /* Adds a dead-zone
															 to the selected
															 axis, then scales
															 down the axis so
															 that 0 starts
															 from the edge of
															 the dead-zone */
		if (joystickValue <= deadzoneValue && joystickValue >= -deadzoneValue) {
			joystickValue = 0;
		}

		return joystickValue * (((Math.abs(joystickValue)) - deadzoneValue) / (1 - deadzoneValue));
	}

	void dashboard() { // outputs certain value to the optional smart dashboard

		// SmartDashboard.putString("DB/String 0", "1");

	}

	public void testPeriodic() {
	}

	public void operatorControl() {

		while (isOperatorControl() && isEnabled()) {
			/** robot code here! **/
			Timer.delay(0.005); // wait for a motor update time
		}
	}
}
