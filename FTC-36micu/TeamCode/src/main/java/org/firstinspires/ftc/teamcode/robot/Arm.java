package org.firstinspires.ftc.teamcode.robot;

import android.text.TextDirectionHeuristic;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@SuppressWarnings("all")
public class Arm {
	private static final double ROTATION_SPEED = 1.0d;
	private static final double EXTENSION_SPEED = 1.0d;

	public enum Direction {
		FORWARD,
		BACKWARD,
		LEFT,
		RIGHT
	}

	public static void run(){
		rotateArm();
		extendArm();
		rotateBox();
		rotateCollector();
	}

	public static void rotateArm() {
		if(Control.armOpen()) {
			Robot.motorRotation.setPower(ROTATION_SPEED);
			double servoPosition = Range.scale(Robot.motorRotation.getCurrentPosition(), 0, 2100, 0, 0.2);
			Robot.servoBox.setPosition(servoPosition);
		}
		else if(Control.armClose()) {
			Robot.motorRotation.setPower(-ROTATION_SPEED);
			double servoPosition = Range.scale(Robot.motorRotation.getCurrentPosition(), 0, 2100, 0, 0.2);
			Robot.servoBox.setPosition(servoPosition);
		}
		else
			Robot.motorRotation.setPower(0);

//		robot.telemetry.addData("Rotation", Integer.toString(robot.motorRotation.getCurrentPosition()));
//		robot.telemetry.update();
	}

	public static void extendArm() {
		if(Control.armExtend())
			Robot.motorExtension.setPower(EXTENSION_SPEED);
		else if(Control.armRetract())
			Robot.motorExtension.setPower(-EXTENSION_SPEED);
		else
			Robot.motorExtension.setPower(0);

//		robot.telemetry.addData("Extension", Integer.toString(robot.motorExtension.getCurrentPosition()));
//		robot.telemetry.update();
	}

	public static void rotateCollector() {
		if(Control.collectorIn())
			Robot.crServo.setPower(1);
		else if(Control.collectorOut())
			Robot.crServo.setPower(-1);
		else
			Robot.crServo.setPower(0);
	}

	private static double boxPosition = 0;

	public static void rotateBox() {
		if(Control.boxOpen()) {
			boxPosition = Range.clip(boxPosition, 0, 0.7);
			Robot.servoBox.setPosition(boxPosition);
			boxPosition += 0.05;
		}
		else if(Control.boxClose()) {
			boxPosition = Range.clip(boxPosition, 0, 0.7);
			Robot.servoBox.setPosition(boxPosition);
			boxPosition -= 0.05;
		}
	}

	/**  ---- Rotate arm pentru autonom ----
	 *   created by "andu,adi,pila" at 02/02/2019
	 *
 	 */

	public static void rotateArmAuto(Direction direction, int counts) throws NullPointerException
	{
		Robot.motorRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		Robot.motorRotation.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		switch(direction){

			case FORWARD:
				Robot.motorRotation.setPower(ROTATION_SPEED);
				break;
			case BACKWARD:
				Robot.motorRotation.setPower(-ROTATION_SPEED);
				break;
			default:
				Robot.motorRotation.setPower(0);
				break;



		}
		int currentCounts=0;
		while(currentCounts < counts) {
			double servoPosition = Range.scale(Robot.motorRotation.getCurrentPosition(), 0, 2100, 0, 0.7);
			Robot.servoBox.setPosition(servoPosition);

			currentCounts = Math.abs(Robot.motorRotation.getCurrentPosition());

		}
		Robot.motorRotation.setPower(0);
	}

}
