package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Sensors {
    public static RevColorSensorV3 left_pixel_sensor;
    public static RevColorSensorV3 right_pixel_sensor;

    public Sensors(HardwareMap hardwareMap) {
        left_pixel_sensor = hardwareMap.get(RevColorSensorV3.class, "left_pixel_sensor");
        right_pixel_sensor = hardwareMap.get(RevColorSensorV3.class, "right_pixel_sensor");
    }
    public static NormalizedRGBA get_left_pixel_sensor_color() {
        return left_pixel_sensor.getNormalizedColors();
    }
    public static NormalizedRGBA get_right_pixel_sensor_color() {
        return right_pixel_sensor.getNormalizedColors();
    }
    public static double get_left_pixel_sensor_distance() {
        return left_pixel_sensor.getDistance(DistanceUnit.MM);
    }
    public static double get_right_pixel_sensor_distance() {
        return right_pixel_sensor.getDistance(DistanceUnit.MM);
    }
}
