package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.checkerframework.checker.units.qual.C;

@TeleOp
@Config
public class GamepadVertTest extends LinearOpMode {
    private DcMotorEx vertical;

    @Override
    public void runOpMode() throws InterruptedException {
        vertical = hardwareMap.get(DcMotorEx.class, "vertical");

        waitForStart();
        while (opModeIsActive()) {
            vertical.setPower(gamepad1.left_stick_y);
        }
    }
}