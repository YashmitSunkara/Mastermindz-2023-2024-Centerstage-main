package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "HangTest", group = "Concept")
@Config
public class HangTest extends LinearOpMode {
    public CRServoImplEx left_hang;
    public CRServoImplEx right_hang;
    public static double a_power = 1;
    public static double b_power = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        left_hang = hardwareMap.get(CRServoImplEx.class, "left_hang");
        right_hang = hardwareMap.get(CRServoImplEx.class, "right_hang");

        left_hang.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                left_hang.setPower(a_power);
                right_hang.setPower(b_power);
            }
            else if (gamepad1.y) {
                left_hang.setPower(-a_power);
                right_hang.setPower(-b_power);
            }
            else if(gamepad1.b){
                left_hang.setPwmDisable();
                right_hang.setPwmDisable();
            }
            else{
                left_hang.setPower(0);
                right_hang.setPower(0);
            }
        }
    }
}
