package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    public static Servo left_claw;
    public static Servo right_claw;
    public static Servo left_claw_tilt;
    public static Servo right_claw_tilt;
    public static final double left_claw_open_position = -0.7;
    public static final double left_claw_close_position = -3;
    public static final double right_claw_open_position = 0.38;
    public static final double right_claw_close_position = -1;
    public static final double left_claw_tilt_intake = 0.278;
    public static final double right_claw_tilt_intake = 0.150;
    public static final double left_claw_tilt_deposit = 0.585;
    public static final double right_claw_tilt_deposit = 0.43;
    public static Double left_claw_position = 0.0;
    public static Double right_claw_position = 0.0;
    public static Double left_claw_tilt_position = 0.11;
    public static Double right_claw_tilt_position = 0.095;

    public Claw(HardwareMap hardwareMap) {
        left_claw = hardwareMap.get(Servo.class, "left_claw");
        right_claw = hardwareMap.get(Servo.class, "right_claw");
        left_claw_tilt = hardwareMap.get(Servo.class, "left_claw_tilt");
        right_claw_tilt = hardwareMap.get(Servo.class, "right_claw_tilt");

        left_claw.setDirection(Servo.Direction.REVERSE);
        left_claw_tilt.setDirection(Servo.Direction.REVERSE);

        left_claw_tilt_position = null;
        right_claw_tilt_position = null;
        left_claw_position = null;
        right_claw_position = null;
    }

    public static void open_left_claw() {
        left_claw_position = left_claw_open_position;
    }

    public static void close_left_claw() {
        left_claw_position = left_claw_close_position;
    }

    public static void open_right_claw() {
        right_claw_position = right_claw_open_position;
    }

    public static void close_right_claw() {
        right_claw_position = right_claw_close_position;
    }

    public static void intake_tilt() {
        left_claw_tilt_position = left_claw_tilt_intake;
        right_claw_tilt_position = right_claw_tilt_intake;
    }

    public static void deposit_tilt() {
        left_claw_tilt_position = left_claw_tilt_deposit;
        right_claw_tilt_position = right_claw_tilt_deposit;
    }

    public static void set() {
        if(left_claw_position != null) {
            left_claw.setPosition(left_claw_position);
        }
        if(right_claw_position != null) {
            right_claw.setPosition(right_claw_position);
        }
        if(left_claw_tilt_position != null) {
            left_claw_tilt.setPosition(left_claw_tilt_position);
        }
        if (right_claw_tilt_position != null) {
            right_claw_tilt.setPosition(right_claw_tilt_position);
        }
    }
}
