package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.teleop.Claw;
import org.firstinspires.ftc.teamcode.teleop.DepositHorizontalSlides;
import org.firstinspires.ftc.teamcode.teleop.FieldCentric;
import org.firstinspires.ftc.teamcode.teleop.Intake;
import org.firstinspires.ftc.teamcode.teleop.IntakeOuttake;

@TeleOp(name = "TeleTest", group = "Concept")
@Config
public class TeleOpMode extends LinearOpMode {
    FieldCentric driver;

    public static double start_position = 0;
    public static double end_position = 0;
    public Servo left_claw;
    public Servo right_claw;
    public Servo left_claw_tilt;
    public Servo right_claw_tilt;
    public Servo left_deposit_horizontal;
    public Servo right_deposit_horizontal;
    public DcMotorEx left_horizontal;
    public DcMotorEx right_horizontal;
    public DcMotorEx vertical;
    public DcMotorEx intake;
    public Servo dropdown;

    public static double down = 0;
    public static double up = 0.5;

    public static double left_intake = 0.08;
    public static double right_intake = 0.08;
    public static double left_claw_i = 0;
    public static double right_claw_i = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        driver = new FieldCentric(hardwareMap, new SampleMecanumDrive(hardwareMap), gamepad2);

        left_claw = hardwareMap.get(Servo.class, "left_claw");
        right_claw = hardwareMap.get(Servo.class, "right_claw");

        left_claw.setDirection(Servo.Direction.REVERSE);

        left_claw_tilt = hardwareMap.get(Servo.class, "left_claw_tilt");
        right_claw_tilt = hardwareMap.get(Servo.class, "right_claw_tilt");

        left_claw_tilt.setDirection(Servo.Direction.REVERSE);

        left_deposit_horizontal = hardwareMap.get(Servo.class, "left_deposit_horizontal");
        right_deposit_horizontal = hardwareMap.get(Servo.class, "right_deposit_horizontal");

        left_deposit_horizontal.setDirection(Servo.Direction.REVERSE);

        dropdown = hardwareMap.get(Servo.class, "dropdown");
        intake = hardwareMap.get(DcMotorEx.class, "intake_motor");

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        dropdown.setDirection(Servo.Direction.REVERSE);

        left_horizontal = hardwareMap.get(DcMotorEx.class, "left_horizontal");
        right_horizontal = hardwareMap.get(DcMotorEx.class, "right_horizontal");

        vertical = hardwareMap.get(DcMotorEx.class, "vertical");

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();

        while (opModeIsActive()) {

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            if (currentGamepad1.a && !previousGamepad1.a) {
                left_claw.setPosition(Claw.left_claw_open_position);
                right_claw.setPosition(Claw.right_claw_open_position);
            }

            if (currentGamepad1.b && !previousGamepad1.b) {
                left_claw.setPosition(Claw.left_claw_close_position);
                right_claw.setPosition(Claw.right_claw_close_position);
            }

            if (currentGamepad1.dpad_left && !previousGamepad1.dpad_left) {
                left_deposit_horizontal.setPosition(DepositHorizontalSlides.left_intake_position);
                right_deposit_horizontal.setPosition(DepositHorizontalSlides.right_intake_position);
            }

            if (currentGamepad1.dpad_right && !previousGamepad1.dpad_right) {
                left_deposit_horizontal.setPosition(DepositHorizontalSlides.deposit_position);
                right_deposit_horizontal.setPosition(DepositHorizontalSlides.deposit_position);
            }

            if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up) {
                left_claw_tilt.setPosition(Claw.left_claw_tilt_intake);
                right_claw_tilt.setPosition(Claw.right_claw_tilt_intake);
            }

            if (currentGamepad1.dpad_down && !previousGamepad1.dpad_down) {
                left_claw_tilt.setPosition(Claw.left_claw_tilt_deposit);
                right_claw_tilt.setPosition(Claw.right_claw_tilt_deposit);
            }

            if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
                intake.setPower(Intake.intakeSpeed);
            }

            if (currentGamepad1.left_bumper && !previousGamepad1.left_bumper) {
                intake.setPower(0);
            }

            if (currentGamepad1.right_trigger > 0.5 && !(previousGamepad1.right_trigger > 0.5)) {
                intake.setPower(Intake.outtakeSpeed);
            }

            if (currentGamepad1.x && !previousGamepad1.x) {
                dropdown.setPosition(Intake.dropdown_up_position);
            }

            if (currentGamepad1.y && !previousGamepad1.y) {
                dropdown.setPosition(Intake.dropdown_down_position);
            }

            left_horizontal.setPower(currentGamepad1.left_stick_y);
            right_horizontal.setPower(currentGamepad1.left_stick_y);

            vertical.setPower(currentGamepad1.right_stick_x);
            driver.move();
        }
    }
}
