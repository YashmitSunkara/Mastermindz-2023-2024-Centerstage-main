package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
@Config
public class GamepadHorizTest extends LinearOpMode {
    private DcMotorEx left_horizontal, right_horizontal;

    @Override
    public void runOpMode() throws InterruptedException {
        left_horizontal = hardwareMap.get(DcMotorEx.class, "left_horizontal");
        right_horizontal = hardwareMap.get(DcMotorEx.class, "right_horizontal");

        waitForStart();
        while (opModeIsActive()) {
            left_horizontal.setPower(gamepad1.left_stick_y);
            right_horizontal.setPower(gamepad1.left_stick_y);
        }
    }
}