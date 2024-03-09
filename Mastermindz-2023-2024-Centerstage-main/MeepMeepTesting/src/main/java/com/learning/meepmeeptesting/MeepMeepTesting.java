package com.learning.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        final double TILE_SIZE = 23.5;
        int robotx = 0;
        int roboty = 0;

        Pose2d startPose = new Pose2d(-35, 60, Math.PI/2);


        Pose2d backboard = new Pose2d(0, 0);
        Pose2d stack = new Pose2d(startPose.getX() - 1 * TILE_SIZE, startPose.getY()- 2.1 * (TILE_SIZE));


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.4)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)
                                .lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY()-(TILE_SIZE), Math.toRadians(135)))
                                // for 1
                                //.lineToSplineHeading(new Pose2d(startPose.getX() + 0.1 * TILE_SIZE, startPose.getY()- 1 * (TILE_SIZE), Math.toRadians(135)))
                                //.lineToSplineHeading(new Pose2d(startPose.getX(), startPose.getY()- 1 * (TILE_SIZE), Math.toRadians(135)))
                                //.splineToConstantHeading(new Vector2d(startPose.getX() - 0 * TILE_SIZE, startPose.getY()- 2.1 * (TILE_SIZE)), Math.toRadians(270))
                                // for 2.line
                                .lineToSplineHeading(new Pose2d(startPose.getX() - 0 * TILE_SIZE, startPose.getY() - 0.9 * (TILE_SIZE), Math.toRadians(90)))
                                .splineToConstantHeading(new Vector2d(startPose.getX() - 1 * TILE_SIZE, startPose.getY()- 1.5 * (TILE_SIZE)), Math.toRadians(270))

                                .lineToSplineHeading(new Pose2d(startPose.getX() - 0.1 * TILE_SIZE, startPose.getY()- 2.1 * (TILE_SIZE), Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(startPose.getX() - 0.8 * TILE_SIZE, startPose.getY()- 2.1 * (TILE_SIZE), Math.toRadians(180)))
                                .waitSeconds(3)
                                .turn(Math.toRadians(-3))
                                .lineToSplineHeading(new Pose2d(startPose.getX() + 2.7 * TILE_SIZE, startPose.getY() - 2.1 * TILE_SIZE, Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(startPose.getX() + 3.3* TILE_SIZE, startPose.getY() - 1.5 * TILE_SIZE), Math.toRadians(0))
                                .turn(Math.toRadians(-5))
                                .waitSeconds(2)
                                .lineToSplineHeading(new Pose2d(startPose.getX() + 2.7 * TILE_SIZE, startPose.getY()- 2.1 * (TILE_SIZE), Math.toRadians(180)))
                                .lineToSplineHeading(new Pose2d(startPose.getX() - 0.9 * TILE_SIZE, startPose.getY()- 2.1 * (TILE_SIZE), Math.toRadians(180)))
                                .waitSeconds(3)
                                .lineToSplineHeading(new Pose2d(startPose.getX() + 2.7 * TILE_SIZE, startPose.getY() - 2.1 * TILE_SIZE, Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(startPose.getX() + 3.6 * TILE_SIZE, startPose.getY() - 0.8 * TILE_SIZE), Math.toRadians(0))



                                .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}