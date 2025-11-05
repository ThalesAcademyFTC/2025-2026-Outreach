package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.Rev9AxisImu;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class mainLibrary {
    //This class will act as our "goldfish" or "robot" class for the year
    public HardwareMap hwMap;

    public LinearOpMode auton;
    public double ElapsedTime;

    public enum Drivetrain {

        TANK

    }

    public Drivetrain drive;

    public Telemetry telemetry;

    //defines motors to all classes that call main/mainlibrary

    public DcMotorEx motorFL, motorFR, motorBL, motorBR;

    public static DcMotorEx[] allMotors;

    public Servo rgbIndicator;

    public RevColorSensorV3 colorSensor;

    public TouchSensor touchSensor;

    public Rev2mDistanceSensor distanceSensor;

    public WebcamName camera;

    public IMU imu;

    //for commit

    public Rev9AxisImu ohMyCog;

    public boolean leftToggleToggle = true;



    public mainLibrary(OpMode opMode, Drivetrain drive) {

        this.hwMap = opMode.hardwareMap;

        this.drive = drive;

        this.telemetry = opMode.telemetry;

        setUpHardware();
    }

    public mainLibrary(LinearOpMode opMode, Drivetrain type) {

        this.auton = opMode;

        hwMap = opMode.hardwareMap;

        telemetry = opMode.telemetry;

        drive = type;

        setUpHardware();
    }

    public void setUpHardware() {
        switch (drive) {
            case TANK:
                //motors gotted in hardware map

                motorFL = (DcMotorEx) hwMap.dcMotor.get("MotorFL");
                motorFR = (DcMotorEx) hwMap.dcMotor.get("MotorFR");
                motorBL = (DcMotorEx) hwMap.dcMotor.get("MotorBL");
                motorBR = (DcMotorEx) hwMap.dcMotor.get("MotorBR");


                motorBL.setDirection(DcMotorSimple.Direction.REVERSE);
                motorFL.setDirection(DcMotorSimple.Direction.REVERSE);

                //servos gotted in hardware map

                rgbIndicator = hwMap.servo.get("rgbIndicator");

                //sensors defined here

                touchSensor =  hwMap.touchSensor.get("TouchSensor");
                distanceSensor = hwMap.get(Rev2mDistanceSensor.class,"DistanceSensor");
                colorSensor = hwMap.get(RevColorSensorV3.class,"ColorSensor");

                IMU imu = hwMap.get(IMU.class, "imu");

                IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
                // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
                imu.initialize(parameters);

                //all motors defined here

                allMotors =  new DcMotorEx[] {motorBL, motorBR, motorFL, motorFR};

                break;
        }

    }

      public void resetDriveEncoders() {

        for (DcMotorEx x : allMotors) {

            x.setPower(0);
            x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            x.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void waitForMotors() {
        boolean finished = false;
        while (auton.opModeIsActive() && !finished && !auton.isStopRequested()) {
            if (motorFL.isBusy() || motorFR.isBusy() || motorBL.isBusy() || motorBR.isBusy()) {
                telemetry.addData("front left encoder:", "%7d / % 7d", motorFL.getCurrentPosition(), motorFL.getTargetPosition());
                telemetry.addData("back left encoder:", "%7d / % 7d", motorBL.getCurrentPosition(), motorBL.getTargetPosition());
                telemetry.addData("front right encoder:", "%7d / % 7d", motorFR.getCurrentPosition(), motorFR.getTargetPosition());
                telemetry.addData("back right encoder:", "%7d / % 7d", motorBR.getCurrentPosition(), motorBR.getTargetPosition());
                telemetry.update();
            } else {
                finished = true;
            }
        }
    }

}

