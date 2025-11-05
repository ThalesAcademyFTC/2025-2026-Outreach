package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class BatteringTime extends OpMode {
    public driverCentricMovement driverCentricMovement;

    public driverCentricMovement fieldCentricMovement;

    public mainLibrary mainLibrary;

    double rbtSpd = .5;

    double startPosition = .5;

    double rgbPos = .277;

    double keep = 0;

    double start = 0;

    public void init() {

        mainLibrary = new mainLibrary(this, org.firstinspires.ftc.teamcode.mainLibrary.Drivetrain.TANK);

        driverCentricMovement = new driverCentricMovement(mainLibrary);

        driverCentricMovement = new driverCentricMovement(mainLibrary);

        mainLibrary = new mainLibrary(this, org.firstinspires.ftc.teamcode.mainLibrary.Drivetrain.TANK);
        IMU imu = mainLibrary.hwMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);


        telemetry.addData("Touch sensor state", mainLibrary.touchSensor.isPressed());
        telemetry.addData("Distance from distance sensor", mainLibrary.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("Red value", mainLibrary.colorSensor.red());
        telemetry.addData("Blue value", mainLibrary.colorSensor.blue());
        telemetry.addData("Green value", mainLibrary.colorSensor.green());
        telemetry.addData("Alpha value", mainLibrary.colorSensor.alpha());
    }
    public void loop() {

        double y = (-gamepad1.left_stick_y);
        double x = (gamepad1.left_stick_x);

        driverCentricMovement.driverMovement(x, y);





    }
}
