package com.learning.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepNear {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        final double TILE_SIZE = 23.5;
        int robotx = 0;
        int roboty = 0;

        Pose2d startPose = new Pose2d(10, -60, -Math.PI/2);


        Pose2d backboard = new Pose2d(startPose.getX() + 1.6 * TILE_SIZE, startPose.getY() + 1 * TILE_SIZE, Math.toRadians(0));
        Pose2d stack = new Pose2d(startPose.getX() - 0.9 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180));


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.4)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)
                                // for 1
                                .lineToSplineHeading(new Pose2d(startPose.getX() + 0.1 * TILE_SIZE, startPose.getY() + TILE_SIZE, Math.toRadians(300)))
                                .lineToSplineHeading(new Pose2d(startPose.getX() - 0.1 * TILE_SIZE, startPose.getY() + TILE_SIZE, Math.toRadians(300)))
                                // for 2
                                //.splineToConstantHeading(new Vector2d(startPose.getX() + 0.1 * TILE_SIZE, startPose.getY() + TILE_SIZE), Math.toRadians(270))
                                // for 3
                                //.lineToSplineHeading(new Pose2d(startPose.getX() + 0.1 * TILE_SIZE, startPose.getY() + TILE_SIZE, Math.toRadians(240)))
                                //.lineToSplineHeading(new Pose2d(startPose.getX() + 0.2 * TILE_SIZE, startPose.getY() + TILE_SIZE, Math.toRadians(240)))
                                //.lineToSplineHeading(new Pose2d(startPose.getX() + 0.3 * TILE_SIZE, startPose.getY() + 0.3 * TILE_SIZE, Math.toRadians(180)))

                                // for 1 and 2 only
                                .splineToSplineHeading(new Pose2d(startPose.getX() + 0.2 * TILE_SIZE, startPose.getY() + 0.4 * TILE_SIZE, Math.toRadians(180)), Math.toRadians(300))


                                .splineToConstantHeading(new Vector2d(startPose.getX() + 1.6 * TILE_SIZE, startPose.getY() + 1 * TILE_SIZE), Math.toRadians(0))
                                .waitSeconds(2)
                                .splineToConstantHeading(new Vector2d(startPose.getX() + 0.6 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE), Math.toRadians(180))
                                .lineToSplineHeading(new Pose2d(startPose.getX() - 3 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180)))
                                .waitSeconds(3.5)
                                .lineToSplineHeading(new Pose2d(startPose.getX() + 0.6 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180)))

                                .splineToConstantHeading(new Vector2d(startPose.getX() + 1.6 * TILE_SIZE, startPose.getY() + 1 * TILE_SIZE), Math.toRadians(0))
                                .waitSeconds(2)
                                .splineToConstantHeading(new Vector2d(startPose.getX() + 0.6 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE), Math.toRadians(180))
                                .lineToSplineHeading(new Pose2d(startPose.getX() - 3 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180)))
                                .waitSeconds(3.5)
                                .lineToSplineHeading(new Pose2d(startPose.getX() + 0.6 * TILE_SIZE, startPose.getY() + 2 * TILE_SIZE, Math.toRadians(180)))

                                .splineToConstantHeading(new Vector2d(startPose.getX() + 1.6 * TILE_SIZE, startPose.getY() + 1 * TILE_SIZE), Math.toRadians(0))
                                .waitSeconds(2)


                                .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}