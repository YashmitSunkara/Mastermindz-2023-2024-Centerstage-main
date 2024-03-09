package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.TimeUnit;

public class Intake {
    public static DcMotorEx intakeMotor;
    public static Servo dropdown;
    public static final double intakeSpeed = 1;
    public static final double outtakeSpeed = -1;
    public static final double dropdown_up_position = 0;
    public static final double dropdown_middle_position = 0.25;
    public static final double dropdown_down_position = 0.4;
    public static double intake_current_speed = 0;
    public static Double dropdown_current_position = 0.1;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake_motor");
        dropdown = hardwareMap.get(Servo.class, "dropdown");

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        dropdown.setDirection(Servo.Direction.REVERSE);

        intake_current_speed = intakeMotor.getPower();
        dropdown_current_position = null;
    }
    public static void stop() {
        intake_current_speed = 0;
    }
    public static void intake() {
        intake_current_speed = 1;
    }
    public static void intake(double speed) {
        if (Math.abs(speed) > 1) speed = 1;
        intake_current_speed = Math.abs(speed);
    }
    public static void outtake() {
        intake_current_speed = -1;
    }
    public static void outtake(double speed) {
        if (Math.abs(speed) > 1) speed = -1;
        intake_current_speed = -Math.abs(speed);
    }

    public static void dropdown_single() {
        dropdown_current_position = dropdown_middle_position;
    }
    public static void dropdown_single(double position) {
        if (position > dropdown_middle_position || position < dropdown_middle_position) position = dropdown_middle_position;
        dropdown_current_position = position;
    }
    public static void dropdown_down() {
        dropdown_current_position = dropdown_down_position;
    }
    public static void dropdown_down(double position) {
        if (position > dropdown_down_position) position = dropdown_down_position;
        dropdown_current_position = position;
    }
    public static void dropdown_up() {
        dropdown_current_position = dropdown_up_position;
    }
    public static void dropdown_up(double position) {
        if (position < dropdown_up_position) position = dropdown_up_position;
        dropdown_current_position = position;
    }

    public static void setIntake() {
        intakeMotor.setPower(intake_current_speed);
    }

    public static void setDropdown() {
        if(dropdown_current_position != null){
            dropdown.setPosition(dropdown_current_position);
        }
    }

    public static void pause() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
