package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class  DepositHorizontalSlides {
    public static Servo left_deposit_horizontal_slides;
    public static Servo right_deposit_horizontal_slides;
    public static final double deposit_position = 0.5;
    public static final double right_intake_position = 0.95;
    public static final double left_intake_position = 0.9;
    static final double INCREMENT = 0.01;
    static final int CYCLE_MS = 50;
    public double TURNING_POINT = 0.85;
    static double previous_action = System.currentTimeMillis();
    public static Double left_current_position = 0.7, right_current_position = 0.75;

    public DepositHorizontalSlides(HardwareMap hardwareMap) {
        left_deposit_horizontal_slides = hardwareMap.get(Servo.class, "left_deposit_horizontal");
        right_deposit_horizontal_slides = hardwareMap.get(Servo.class, "right_deposit_horizontal");

        left_deposit_horizontal_slides.setDirection(Servo.Direction.REVERSE);

        left_current_position = null;
        right_current_position = null;
    }

    public static void set() {
        if(left_current_position != null) {
            left_deposit_horizontal_slides.setPosition(left_current_position);
        }
        if(right_current_position != null) {
            right_deposit_horizontal_slides.setPosition(right_current_position);
        }
    }

    public static void intake() {
        left_current_position = left_intake_position;
        right_current_position = right_intake_position;
    }

    public static void deposit() {
        left_current_position = deposit_position;
        right_current_position = deposit_position;
    }
}
