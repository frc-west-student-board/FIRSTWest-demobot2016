package org.usfirst.frc.team5897.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Timer;

//Code responsible for drivetrain; includes tank and arcade

public class DriveTrain {

	Robot main = new Robot();

	// Order of motors is FL,BL,FR,BR
	public final VictorSP FL = new VictorSP(0); // front left motor
	public final VictorSP BL = new VictorSP(1); // back left motor
	public final VictorSP FR = new VictorSP(6); // front right motor
	public final VictorSP BR = new VictorSP(5); // back right motor

	final float smoothingFactor = .1f;
	final float GearAdjust = 0.00f; // From APEX code, made to compensate for
									// faulty gearbox

	Timer time = new Timer();

	public void tank(double left, double right) { // experimental code the
													// allows each stick to
													// control each side, useful
													// for discovering
													// mechanical issues
		left = -left; // since the left stick happens to be inverted (see note
						// at top of main code) this is inverting it back
		correct();
		setLeft(left);
		setRight(right);
		correct();
		/*
		 * FL.set(left); BL.set(left); FR.set(right); BR.set(right);
		 */
	}

	public void drive(double speed, double turn) {
		speed = -speed; // since the left stick happens to be inverted (see note
						// at top of main code) this is inverting it back
		correct();
		setLeft(speed);
		setRight(speed);

		if (speed > 0) { // if moving forward
			if (turn < 0) { // if turning left
				setLeft(speed - Math.abs(turn)); // subtract left side by the
													// turn value
			} else { // if turning right
				setRight(speed - Math.abs(turn));// subtract right side by the
													// turn value
			}
			if (FL.getSpeed() < 0) { // this prevents the motors from being
										// subtracted so much it goes the other
										// way
				setLeft(0);
			}
			if (FR.getSpeed() < 0) { // this prevents the motors from being
										// subtracted so much it goes the other
										// way
				setRight(0);
			}
		}
		if (speed < 0) { // if moving backward
			if (turn < 0) { // if turning left
				setLeft(speed + Math.abs(turn)); // I should check if this works
													// and why it's different...
			}

			else {
				setRight(speed + Math.abs(turn));
			}
			if (FL.getSpeed() > 0) {
				setLeft(0);
			}
			if (FR.getSpeed() > 0) {
				setRight(0);
			}
		}

		if (speed == 0) { // if not moving, rotate on the spot
			setLeft(turn);
			setRight(-turn);
		}
		correct(); // corrects the inverted motor

	}

	public void stop() { // used to stop robot in auto
		setLeft(0);
		setRight(0);
	}

	public void setLeft(double speed) { // set the value of the two left motors
		if (speed > GearAdjust) {
			speed -= GearAdjust;
		} else if (speed < -GearAdjust) {
			speed += GearAdjust;
		}

		FL.set(smooth(FL.get(), speed, smoothingFactor));
		BL.set(smooth(BL.get(), speed, smoothingFactor));
	}

	public void setRight(double speed) { // set the value of the two left motors
		FR.set(smooth(FR.get(), speed, smoothingFactor));
		BR.set(smooth(BR.get(), speed, smoothingFactor));
	}

	private void correct() { // one of the sides is inverted due to the nature
								// of the design, adding this would make it
								// easier
		FR.set(-FR.get());
		BR.set(-BR.get());
	}

	static double smooth(double a, double b, float f) {
		/*
		 * this code makes the joystick a target to gradually smooth into,
		 * rather than being a direct value set, this allows smoother driving
		 */
		return a + ((b - a) * f);
	}
}