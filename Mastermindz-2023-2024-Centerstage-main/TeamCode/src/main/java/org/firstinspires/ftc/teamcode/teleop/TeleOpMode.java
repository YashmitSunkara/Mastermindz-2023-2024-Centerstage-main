package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOpMode", group = "Concept")
public class TeleOpMode extends LinearOpMode {
    FieldCentric driver;
    IntakeOuttake in_out_take;
    Claw claw;
    VerticalSlides vertical_slides;
    HorizontalSlides horizontal_slides;
    Intake intake;
    Sensors sensors;
    public CRServoImplEx left_hang;
    public CRServoImplEx right_hang;
    public Servo drone;
    DepositHorizontalSlides deposit_horizontal_slides;

    enum States {
        REGULAR,
        HANG
    }

    public States state = States.REGULAR;

    @Override
    public void runOpMode() {
        driver = new FieldCentric(hardwareMap, new SampleMecanumDrive(hardwareMap), gamepad1);

        claw = new Claw(hardwareMap);
        vertical_slides = new VerticalSlides(hardwareMap);
        horizontal_slides = new HorizontalSlides(hardwareMap);
        intake = new Intake(hardwareMap);
        sensors = new Sensors(hardwareMap);
        deposit_horizontal_slides = new DepositHorizontalSlides(hardwareMap);
        left_hang = hardwareMap.get(CRServoImplEx.class, "left_hang");
        right_hang = hardwareMap.get(CRServoImplEx.class, "right_hang");
        drone = hardwareMap.get(Servo.class, "drone");

        left_hang.setDirection(DcMotorSimple.Direction.REVERSE);

        in_out_take = new IntakeOuttake(sensors, claw, deposit_horizontal_slides, horizontal_slides, intake, vertical_slides);

        in_out_take.setInstructions(IntakeOuttake.Instructions.INITIAL_CLOSED);
        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.EXTEND_VERTICAL);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        while (!isStarted() && !isStopRequested() && !opModeIsActive()) {
            state = States.REGULAR;
            in_out_take.update();
            drone.setPosition(0.05);
        }

        waitForStart();

        while (opModeIsActive()) {

            driver.move();

            if (state == States.REGULAR) in_out_take.update();

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            if (state == States.REGULAR) {
                if (currentGamepad1.y && !previousGamepad1.y) {
                    in_out_take.setInstructions(IntakeOuttake.Instructions.EXTEND_VERTICAL_FOR_INTAKE);
                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.EXTEND_VERTICAL);
                }

                if (currentGamepad1.options && !previousGamepad1.options) {
                    in_out_take.setInstructions(IntakeOuttake.Instructions.OPEN_INTAKE);
                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.SINGLE_PIXEL);
                }

                if (currentGamepad1.left_trigger > 0.5 && !(previousGamepad1.left_trigger > 0.5)) {
                    in_out_take.intake.outtake();
                }

                if (currentGamepad1.right_trigger > 0.5 && !(previousGamepad1.right_trigger > 0.5)) {
                    in_out_take.intake.intake();
                }

                if (currentGamepad1.right_trigger < 0.5 && !(previousGamepad1.right_trigger < 0.5)) {
                    in_out_take.intake.stop();
                }

                if (currentGamepad1.left_trigger < 0.5 && !(previousGamepad1.left_trigger < 0.5)) {
                    in_out_take.intake.stop();
                }

                if (currentGamepad1.right_bumper && !(previousGamepad1.right_bumper)) {
                    in_out_take.setInstructions(IntakeOuttake.Instructions.OPEN_INTAKE);
                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.EXTEND_VERTICAL);
                }

                if (currentGamepad1.b && !previousGamepad1.b) {
                    in_out_take.setInstructions(IntakeOuttake.Instructions.CLOSED);
                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.DROP_PIXEL);
                }

                if (currentGamepad1.x && !(previousGamepad1.x)) {
                    in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                }

                if (currentGamepad1.dpad_left && !(previousGamepad1.dpad_left)) {
                    in_out_take.claw.open_right_claw();
                }

                if (currentGamepad1.dpad_right && !(previousGamepad1.dpad_right)) {
                    in_out_take.claw.open_left_claw();
                }

                if ((gamepad1.dpad_up)) {
                    in_out_take.vertical_slides.vertical_offset += 15;
                }

                if ((gamepad1.dpad_down)) {
                    in_out_take.vertical_slides.vertical_offset -= 15;
                }
            }



            if (currentGamepad2.start && !previousGamepad2.start) {
                state = States.HANG;
            }

            if (currentGamepad2.back && !previousGamepad2.back) {
                state = States.REGULAR;
            }

            if (state == States.HANG) {
                in_out_take.vertical_slides.verticalSlides.setPower(currentGamepad2.left_stick_y);

                if (gamepad2.a) {
                    left_hang.setPower(1);
                    right_hang.setPower(1);
                }
                else if (gamepad2.y) {
                    left_hang.setPower(-1);
                    right_hang.setPower(-1);
                }
                else if(gamepad2.b){
                    left_hang.setPwmDisable();
                    right_hang.setPwmDisable();
                }
                else{
                    left_hang.setPower(0);
                    right_hang.setPower(0);
                }

                if (currentGamepad2.right_trigger > 0.5 && !(previousGamepad2.right_trigger > 0.5)) {
                    drone.setPosition(1);
                }

                if (currentGamepad2.left_trigger > 0.5 && !(previousGamepad2.left_trigger > 0.5)) {
                    in_out_take.setInstructions(IntakeOuttake.Instructions.CLOSED);
                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.DROP_PIXEL);
                }
            }

            // telemetry
            telemetry.update();
        }
    }
}