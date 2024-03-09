package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class VerticalSlides {
    private static PIDController controller;
    private static final double p = 0.032, i = 0, d = 0.0001;
    private static final double f = 0.00004;
    private static final int extendedBound = -2700;
    private static final int retractedBound = 30;
    private static final double ground_position = 30;
    private static final double closed_reset = -800;
    private static final double low_position = -1000;
    private static final double medium_position = -1600;
    private static final double high_position = -2700;
    public static DcMotorEx verticalSlides;
    public static double current_position = 0;

    public static double vertical_offset = 0;

    public VerticalSlides(HardwareMap hardwareMap) {
        controller = new PIDController(p, i, d);

        verticalSlides = hardwareMap.get(DcMotorEx.class, "vertical");

        current_position = verticalSlides.getCurrentPosition();
    }

    public static void set() {
        if (current_position + vertical_offset <= extendedBound) {
            current_position = extendedBound;
        }
        if (current_position + vertical_offset >= retractedBound) {
            current_position = retractedBound;
        }

        controller.setPID(p, i, d);
        double vertical_current_position = verticalSlides.getCurrentPosition();

        double pid = controller.calculate(vertical_current_position + vertical_offset, current_position);

        double power = pid + f;

        verticalSlides.setPower(power);
    }

    public static void go_to_high() {
        current_position = high_position;
    }

    public static void go_to_medium() {
        current_position = medium_position;
    }

    public static void go_to_low() {
        current_position = low_position;
    }

    public static void go_to_ground() {
        current_position = ground_position;
    }

    public static void go_to_closed_reset() {
        current_position = closed_reset;
    }
}