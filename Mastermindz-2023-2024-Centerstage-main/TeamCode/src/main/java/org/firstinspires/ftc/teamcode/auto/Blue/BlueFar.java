package org.firstinspires.ftc.teamcode.auto.Blue;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.opencv.BlueSpikemarkPipeline;
import org.firstinspires.ftc.teamcode.opencv.SpikemarkDetection;
import org.firstinspires.ftc.teamcode.teleop.Claw;
import org.firstinspires.ftc.teamcode.teleop.DepositHorizontalSlides;
import org.firstinspires.ftc.teamcode.teleop.FieldCentric;
import org.firstinspires.ftc.teamcode.teleop.HorizontalSlides;
import org.firstinspires.ftc.teamcode.teleop.Intake;
import org.firstinspires.ftc.teamcode.teleop.IntakeOuttake;
import org.firstinspires.ftc.teamcode.teleop.Sensors;
import org.firstinspires.ftc.teamcode.teleop.VerticalSlides;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "BlueFar", group = "Concept")
public class BlueFar extends LinearOpMode {

    OpenCvCamera webcam;
    IntakeOuttake in_out_take;
    Claw claw;
    VerticalSlides vertical_slides;
    HorizontalSlides horizontal_slides;
    Intake intake;
    Sensors sensors;
    DepositHorizontalSlides deposit_horizontal_slides;

    Pose2d startPose = new Pose2d(-35, 60, Math.PI / 2);

    SampleMecanumDrive drive;

    BlueSpikemarkPipeline blueSpikemarkPipeline;

    BlueSpikemarkPipeline.SpikemarkPosition position;

    final double TILE_SIZE = 23.5;

    @Override
    public void runOpMode() {

        drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(startPose);

        claw = new Claw(hardwareMap);
        vertical_slides = new VerticalSlides(hardwareMap);
        horizontal_slides = new HorizontalSlides(hardwareMap);
        intake = new Intake(hardwareMap);
        sensors = new Sensors(hardwareMap);
        deposit_horizontal_slides = new DepositHorizontalSlides(hardwareMap);


        in_out_take = new IntakeOuttake(sensors, claw, deposit_horizontal_slides, horizontal_slides, intake, vertical_slides);

        in_out_take.setInstructions(IntakeOuttake.Instructions.INITIAL_CLOSED);
        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.EXTEND_VERTICAL);


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);
        blueSpikemarkPipeline = new BlueSpikemarkPipeline(telemetry);
        webcam.setPipeline(blueSpikemarkPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int i) {

            }
        });


        while (!isStarted() && !isStopRequested() && !opModeIsActive()) {
            in_out_take.update();
            telemetry.update();
        }

        position = blueSpikemarkPipeline.getPosition();

        if (position == BlueSpikemarkPipeline.SpikemarkPosition.ONE) {
            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startPose)
                    // for 1
                    .lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY() - (TILE_SIZE), Math.toRadians(135)))
                    .lineToSplineHeading(new Pose2d(startPose.getX() + 0.3 * TILE_SIZE, startPose.getY() - 1 * (TILE_SIZE), Math.toRadians(135)))
                    .lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY() - 1 * (TILE_SIZE), Math.toRadians(135)))
                    .splineToConstantHeading(new Vector2d(startPose.getX() - 0 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE)), Math.toRadians(270))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.1 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE), Math.toRadians(180)))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.95 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE), Math.toRadians(180)))
                    .waitSeconds(3)
                    .waitSeconds(2)
                    .turn(Math.toRadians(-5))
                    .waitSeconds(2)
                    .lineToSplineHeading(new Pose2d(startPose.getX() + 2.7 * TILE_SIZE, startPose.getY() - 2.3 * TILE_SIZE, Math.toRadians(180)))
                    .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3 * TILE_SIZE, startPose.getY() - 1.2 * TILE_SIZE), Math.toRadians(0))
                    .turn(Math.toRadians(-5))
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                    })
                    .waitSeconds(4)
                    .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3 * TILE_SIZE + 8, startPose.getY() - 1.2 * TILE_SIZE), Math.toRadians(0))
                    .waitSeconds(3)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        Claw.open_left_claw();
                        Claw.open_right_claw();
                    })
                    .waitSeconds(1)
                    .forward(3)
                    .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                        in_out_take.setInstructions(IntakeOuttake.Instructions.CLOSED);
                        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.DROP_PIXEL);
                    })
                    .build()
            );
        } else if (position == BlueSpikemarkPipeline.SpikemarkPosition.TWO) {
            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startPose)
                    .lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY() - 1.1 * (TILE_SIZE), Math.toRadians(90)))
                    .splineToConstantHeading(new Vector2d(startPose.getX(), startPose.getY() - 20), Math.toRadians(90))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.5 * TILE_SIZE, startPose.getY() - 20, Math.toRadians(180)))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.5 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE), Math.toRadians(180)))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.95 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE), Math.toRadians(180)))
                    .waitSeconds(3)
                    .waitSeconds(2)
                    .turn(Math.toRadians(-5))
                    .waitSeconds(2)
                    .lineToSplineHeading(new Pose2d(startPose.getX() + 2.7 * TILE_SIZE, startPose.getY() - 2.3 * TILE_SIZE, Math.toRadians(180)))
                    .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3 * TILE_SIZE, startPose.getY() - 1.55 * TILE_SIZE), Math.toRadians(0))
                    .turn(Math.toRadians(-5))
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                    })
                    .waitSeconds(4)
                    .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3 * TILE_SIZE + 8, startPose.getY() - 1.55 * TILE_SIZE), Math.toRadians(0))
                    .waitSeconds(3)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        Claw.open_left_claw();
                        Claw.open_right_claw();
                    })
                    .waitSeconds(1)
                    .forward(3)
                    .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                        in_out_take.setInstructions(IntakeOuttake.Instructions.CLOSED);
                        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.DROP_PIXEL);
                    })
                    .build()
            );
        } else {
            drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(startPose)
                    .lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY() - (TILE_SIZE), Math.toRadians(45)))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.2 * TILE_SIZE, startPose.getY() - 1 * (TILE_SIZE), Math.toRadians(45)))
                    .lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY() - 1 * (TILE_SIZE), Math.toRadians(45)))
                    .splineToConstantHeading(new Vector2d(startPose.getX() - 0 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE)), Math.toRadians(270))
                    .lineToSplineHeading(new Pose2d(startPose.getX() - 0.85 * TILE_SIZE, startPose.getY() - 2.1 * (TILE_SIZE), Math.toRadians(180)))
                    .waitSeconds(3)
                    .waitSeconds(2)
                    .turn(Math.toRadians(-5))
                    .waitSeconds(2)
                    .lineToSplineHeading(new Pose2d(startPose.getX() + 2.7 * TILE_SIZE, startPose.getY() - 2.3 * TILE_SIZE, Math.toRadians(180)))
                    .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3 * TILE_SIZE, startPose.getY() - 2 * TILE_SIZE), Math.toRadians(0))
                    .turn(Math.toRadians(-5))
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                    })
                    .waitSeconds(4)
                    .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3 * TILE_SIZE + 8, startPose.getY() - 1.9 * TILE_SIZE), Math.toRadians(0))
                    .waitSeconds(3)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        Claw.open_left_claw();
                        Claw.open_right_claw();
                    })
                    .waitSeconds(1)
                    .forward(3)
                    .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                        in_out_take.setInstructions(IntakeOuttake.Instructions.CLOSED);
                        in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.DROP_PIXEL);
                    })
                    .build()
            );
        }

        waitForStart();

        long startTime = System.currentTimeMillis();


        while (opModeIsActive()) {
            long currentTime = System.currentTimeMillis();

            drive.update();
            in_out_take.update();

            Pose2d currentPose = drive.getPoseEstimate();

            /*
            if (currentTime - startTime >= 6000 && currentTime - startTime <= 12000 && !drive.isBusy()) {
                drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(currentPose)
                        .lineToSplineHeading(new Pose2d(startPose.getX() - 0.4 * TILE_SIZE, startPose.getY() + 0.5 * TILE_SIZE, Math.toRadians(90)))
                        .lineToSplineHeading(new Pose2d(startPose.getX() - 0.9 * TILE_SIZE, startPose.getY() + 0.5 * TILE_SIZE, Math.toRadians(180)))
                        .lineToSplineHeading(new Pose2d(startPose.getX() - 0.9 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180)))
                        .lineToSplineHeading(new Pose2d(startPose.getX() - 1.1 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180)))
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            in_out_take.setInstructions(IntakeOuttake.Instructions.EXTEND_VERTICAL_FOR_INTAKE);
                            in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.EXTEND_VERTICAL);
                            in_out_take.intake.intake();
                        })
                        .UNSTABLE_addTemporalMarkerOffset(4, () -> {
                            in_out_take.intake.outtake();
                        })
                        .UNSTABLE_addTemporalMarkerOffset(6, () -> {
                            in_out_take.setInstructions(IntakeOuttake.Instructions.CLOSED);
                            in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.DROP_PIXEL);
                            in_out_take.intake.stop();
                        })
                        .build()
                );
            }

            if (currentTime - startTime >= 15000 && currentTime - startTime <= 20000 && !drive.isBusy()) {
                switch (position) {
                    case ONE:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(currentPose)
                                .lineToConstantHeading(new Vector2d(startPose.getX() + 2.5 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE))
                                .splineTo(new Vector2d(startPose.getX() + 3.5 * TILE_SIZE, startPose.getY() + 1.3 * TILE_SIZE), Math.toRadians(0))
                                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                    in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                                })
                                .build()
                        );
                        break;
                    case TWO:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(currentPose)
                                .lineToConstantHeading(new Vector2d(startPose.getX() + 2.5 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE))
                                .splineTo(new Vector2d(startPose.getX() + 3.5 * TILE_SIZE, startPose.getY() + 1.05 * TILE_SIZE), Math.toRadians(0))
                                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                    in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                                })
                                .build()
                        );
                        break;
                    case THREE:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(currentPose)
                                .lineToConstantHeading(new Vector2d(startPose.getX() + 2.5 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE))
                                .splineTo(new Vector2d(startPose.getX() + 3.5 * TILE_SIZE, startPose.getY() + 0.8 * TILE_SIZE), Math.toRadians(0))
                                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                                    in_out_take.setInstructions(IntakeOuttake.Instructions.DEPOSIT);
                                    in_out_take.setSpecificInstruction(IntakeOuttake.SpecificInstructions.RETRACT_VERTICAL);
                                })
                                .build()
                        );
                        break;
                }
            }
            */
        }
    }

    public void lockTo(Pose2d targetPos) {
        Pose2d currPos = drive.getPoseEstimate();
        Pose2d difference = targetPos.minus(currPos);
        Vector2d xy = difference.vec().rotated(-currPos.getHeading());

        double heading = Angle.normDelta(targetPos.getHeading()) - Angle.normDelta(currPos.getHeading());
        drive.setWeightedDrivePower(new Pose2d(xy, heading));
    }
}