package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class IntakeTest extends LinearOpMode {
    public static double start_position = 0;
    public static double end_position = 0;
    public static double power_in = 0;
    public static double power_out = 0;
    public Servo dropdown;
    public DcMotorEx intake;

    @Override
    public void runOpMode() throws InterruptedException {
        dropdown = hardwareMap.get(Servo.class, "dropdown");
        intake = hardwareMap.get(DcMotorEx.class, "intake_motor");

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        dropdown.setDirection(Servo.Direction.REVERSE);

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
                intake.setPower(power_in);
            }

            else if (currentGamepad1.b && !previousGamepad1.b) {
                intake.setPower(power_out);
            }

            else if (currentGamepad1.x && !previousGamepad1.x) {
                dropdown.setPosition(start_position);
            }

            else if (currentGamepad1.y && !previousGamepad1.y) {
                dropdown.setPosition(end_position);
            }
        }
    }
}
