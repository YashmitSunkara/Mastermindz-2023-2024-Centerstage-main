package org.firstinspires.ftc.teamcode.opencv;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class BlueSpikemarkPipeline extends OpenCvPipeline {
    private final Mat mat = new Mat();
    Telemetry telemetry;
    public enum SpikemarkPosition {
        DEFAULT,
        ONE,
        TWO,
        THREE
    }

    public SpikemarkPosition position = SpikemarkPosition.DEFAULT;

    public BlueSpikemarkPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat frame) {
        double[] hsvThresholdHue = {74, 130};
        double[] hsvThresholdSaturation = {191, 255};
        double[] hsvThresholdValue = {0, 255};
        hsvThreshold(frame, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, frame);

        Mat matLeft = frame.submat(0, 448, 0, 267);
        Mat matCenter = frame.submat(0, 448, 267, 533);
        Mat matRight = frame.submat(0, 448, 533, 800);

        Imgproc.rectangle(frame, new Rect(0, 0, 106, 240), new Scalar(0, 255, 0));
        Imgproc.rectangle(frame, new Rect(106, 0, 107, 240), new Scalar(0, 255, 0));
        Imgproc.rectangle(frame, new Rect(213, 0, 106, 240), new Scalar(0, 255, 0));

        double leftTotal = Core.sumElems(matLeft).val[0];
        double centerTotal = Core.sumElems(matCenter).val[0];
        double rightTotal = Core.sumElems(matRight).val[0];

        if (leftTotal > centerTotal && leftTotal > rightTotal) {
            position = SpikemarkPosition.ONE;
            telemetry.addData("Position", "ONE");
        }

        else if (centerTotal > rightTotal && centerTotal > leftTotal) {
            position = SpikemarkPosition.TWO;
            telemetry.addData("Position", "TWO");
        }

        else if (rightTotal > centerTotal && rightTotal > leftTotal) {
            position = SpikemarkPosition.THREE;
            telemetry.addData("Position", "THREE");
        }

        telemetry.addData("CenterTotal", centerTotal);
        telemetry.addData("RightTotal", rightTotal);
        telemetry.addData("LeftTotal", leftTotal);

        telemetry.update();

        return frame;
    }

    public SpikemarkPosition getPosition() {
        return position;
    }
    private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
                              Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_RGB2HSV);
        Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
                new Scalar(hue[1], sat[1], val[1]), out);
    }

}