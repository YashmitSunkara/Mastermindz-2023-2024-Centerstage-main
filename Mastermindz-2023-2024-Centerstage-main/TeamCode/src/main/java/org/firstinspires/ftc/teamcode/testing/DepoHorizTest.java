package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "DepoHorizTest", group = "Concept")
@Config
public class DepoHorizTest extends LinearOpMode {
    public static double left_start = 0;
    public static double left_end = 0;
    public static double right_start = 0;
    public static double right_end = 0;
    public Servo left_depo_horiz, right_depo_horiz;

    @Override
    public void runOpMode() throws InterruptedException {
        left_depo_horiz = hardwareMap.get(Servo.class, "left_deposit_horizontal");
        right_depo_horiz = hardwareMap.get(Servo.class, "right_deposit_horizontal");

        left_depo_horiz.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            Gamepad currentGamepad1 = new Gamepad();
            Gamepad currentGamepad2 = new Gamepad();

            Gamepad previousGamepad1 = new Gamepad();
            Gamepad previousGamepad2 = new Gamepad();

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            if (currentGamepad1.a && !previousGamepad1.a) {
                left_depo_horiz.setPosition(left_start);
                right_depo_horiz.setPosition(right_start);
            }

            else if (currentGamepad1.b && !previousGamepad1.b) {
                left_depo_horiz.setPosition(left_end);
                right_depo_horiz.setPosition(right_end);
            }
        }
    }
}
