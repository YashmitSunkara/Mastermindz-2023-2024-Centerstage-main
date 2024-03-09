package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HorizontalSlides {
    public static PIDController controller;
    public static final double p = 0.05, i = 0, d = 0;
    public static final double f = 0.00004;
    public static final int extendedBound = -600;
    public static final int extended = -1000;
    public static final int retractedBound = 12;

    public static DcMotorEx leftHorizontalSlides;
    public static DcMotorEx rightHorizontalSlides;
    public static double current_position = 0;

    public HorizontalSlides(HardwareMap hardwareMap) {
        controller = new PIDController(p, i, d);

        leftHorizontalSlides = hardwareMap.get(DcMotorEx.class, "left_horizontal");
        rightHorizontalSlides = hardwareMap.get(DcMotorEx.class, "right_horizontal");

        current_position = leftHorizontalSlides.getCurrentPosition();
    }

    public static void set() {
        if (current_position <= extendedBound) {
            current_position = extendedBound;
        }
        if (current_position >= retractedBound) {
            current_position = retractedBound;
        }

        controller.setPID(p, i, d);
        double horizontal_current_position = leftHorizontalSlides.getCurrentPosition();

        double pid = controller.calculate(horizontal_current_position, current_position);

        double power = pid + f;

        leftHorizontalSlides.setPower(power);
        rightHorizontalSlides.setPower(power);
    }

    public static void extend() {
        current_position = extended;
    }

    public static void retract() {
        current_position = retractedBound;
    }
}
