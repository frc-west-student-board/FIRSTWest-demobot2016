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
		// Error checking to prevent crashing - currently does not work
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
			idleAuto(1f); // Robot stands still
			break;
		case 1:
			forwardAuto(4f); // Robot drives forward
			break;
		case 2:
			zigzagAuto(2f, 3f, 5f, 6f, 8f); // Sequence is: forward, turns left,
											// forward, turns right, forward
			break;
		case 3:
			spinAuto(2f); // Spins to the right
			break;
		case 9:
			selfTest(); // Activates each mechanism individually for
						// troubleshooting
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

	public double joystickDeadzone(
			double joystickValue) { /*
									 * Adds a dead-zone to the selected axis,
									 * then scales down the axis so that 0
									 * starts from the edge of the dead-zone
									 */
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

	// Creating new methods to be referenced in Auto.java, preventing resource
	// conflict. Not currently effective but used below in the Auto Modes.

	public void FWDAuto(float AutoSpeed) {
		Driver.forward(AutoSpeed);
	}

	public void BWDAuto(float AutoSpeed) {
		FWDAuto(-AutoSpeed);
	}

	public void TRNAuto(float AutoSpeed, boolean left) {
		Driver.turn(AutoSpeed, left);
	}

	public void STOPAuto() {
		Driver.stop();
	}

	public void ENDAUTO() {
		STOPAuto();
	}

	// Auto Modes

	// ~~~~~~~~~AUTO WIP - TO BE REMOVED TO DIFFERNT CLASS~~~~~~~~~~~~~~~

	public void idleAuto(float timeState1) {
		System.out.println("time: " + time);
		if (time.get() <= timeState1) {
			ENDAUTO();
		}
	}

	public void forwardAuto(float timeState1) {
		System.out.println("time: " + time);
		if (time.get() <= timeState1) {
			FWDAuto(1);
		} else if (time.get() >= timeState1) {
			ENDAUTO();
		}
	}

	public void zigzagAuto(float timeState1, float timeState2, float timeState3, float timeState4, float timeState5) {
		System.out.println("time: " + time);
		if (time.get() <= timeState1) {
			FWDAuto(1);
		} else if (time.get() >= timeState1 && time.get() <= timeState2) {
			STOPAuto(); //These stops help prevent coasting
			TRNAuto(0.5f, false);
		} else if (time.get() >= timeState2 && time.get() <= timeState3) {
			STOPAuto();
			FWDAuto(1);
		} else if (time.get() >= timeState3 && time.get() <= timeState4) {
			STOPAuto();
			TRNAuto(0.5f, true);
		} else if (time.get() >= timeState4 && time.get() <= timeState5) {
			STOPAuto();
			FWDAuto(1);
		} else if (time.get() >= timeState5) {
			ENDAUTO();
		}
	}

	public void spinAuto(float timeState1) {
		System.out.println("time: " + time);
		if (time.get() <= timeState1) {
			TRNAuto(0.5f, false);
		} else if (time.get() >= timeState1) {
			ENDAUTO();
		}
	}

	public void selfTest() {
		if (time.get() <= 1) {
			Driver.BL.set(0.75f); // Back Left motor going forward
		} else if (time.get() > 2 && time.get() <= 3) {
			Driver.BL.set(-0.75f); // Back Left motor going backwards
		} else if (time.get() > 3 && time.get() <= 4) {
			Driver.BL.set(0);
			Driver.FL.set(0.75f); // Front Left motor going forward
		} else if (time.get() > 4 && time.get() <= 5) {
			Driver.FL.set(-0.75f); // Front Left motor going backwards
		} else if (time.get() > 5 && time.get() <= 6) {
			Driver.FL.set(0);
			Driver.BR.set(0.75f); // Back Right motor going forward
		} else if (time.get() > 6 && time.get() <= 7) {
			Driver.BR.set(-0.75f); // Back Right motor going backwards
		} else if (time.get() > 7 && time.get() <= 8) {
			Driver.BR.set(0);
			Driver.FR.set(0.75f); // Front Right motor going forward
		} else if (time.get() > 8 && time.get() <= 9) {
			Driver.FR.set(-0.75f); // Front Right motor going backwards
		} else if (time.get() > 9 && time.get() <= 9.5f) {
			Driver.FR.set(0);
		} else {
			ENDAUTO(); // backup measure in case the last running motor doesn't
						// stop
		}
	}
}
