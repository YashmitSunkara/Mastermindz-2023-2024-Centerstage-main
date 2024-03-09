package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
@Config
public class PIDHoriz extends OpMode {
    private PIDController controller;

    public static final double p = 0.1, i = 0, d = 0;
    public static final double f = 0.00004;

    public static double targetPosition = 0;

    private DcMotorEx left_horizontal;
    private DcMotorEx right_horizontal;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        left_horizontal = hardwareMap.get(DcMotorEx.class, "left_horizontal");
        right_horizontal = hardwareMap.get(DcMotorEx.class, "right_horizontal");
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        double slidePos = left_horizontal.getCurrentPosition();

        double pid = controller.calculate(slidePos, targetPosition);

        double power = pid + f;

        left_horizontal.setPower(power);
        right_horizontal.setPower(power);

        telemetry.addData("targetPos", targetPosition);
        telemetry.addData("currentPos", slidePos);
        telemetry.update();
    }
}