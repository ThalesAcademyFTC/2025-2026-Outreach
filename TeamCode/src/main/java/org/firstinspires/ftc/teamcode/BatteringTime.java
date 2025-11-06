package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class BatteringTime extends OpMode {
    DcMotor leftRear;
    DcMotor rightRear;
    DcMotorEx ramMotor;

    boolean retracting = false;
    boolean retracted = false;
    final int LIFT_POSITION = 0;
    final int RELEASE_POSITION = 2000;

    public void init() {

        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ramMotor = hardwareMap.get(DcMotorEx.class, "ramMotor");
        ramMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // lift ram until it stops and reset/zero the motor position
        ramMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ramMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int newPosition = -10000;
        int position = ramMotor.getCurrentPosition();
        ramMotor.setPower(-0.1);
        while(Math.abs(newPosition - position) < 2) {
            position = newPosition;
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            newPosition = ramMotor.getCurrentPosition();
        }
        ramMotor.setPower(0);
        ramMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ramMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        retracted = true;
    }

    public void loop() {

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        drive(x, y);

        if(!retracting && gamepad1.aWasPressed()) {
            retracting = true;
            liftRam();
        }
        if(!retracting && gamepad1.rightBumperWasPressed()) {
            releaseRam();
            retracted = false;
        }
        if(retracting) {
            if(Math.abs(ramMotor.getCurrentPosition() - LIFT_POSITION) < 3) {
                retracting = false;
                retracted = true;
            }
        }
    }

    public void liftRam() {
        ramMotor.setTargetPosition(LIFT_POSITION);
    }

    public void releaseRam() {
        ramMotor.setTargetPosition(RELEASE_POSITION);
    }
    public void drive(double x, double y) {
        // y=1, x=0  forward
        // y=0, x=1  turn clockwise in place
        // y=1, x=1  turn to the right while moving 'forward'
        double leftPower = Range.clip(x + y, -1.0, 1.0);
        double rightPower = Range.clip(x - y, -1.0, 1.0);
        rightRear.setPower(rightPower);
        leftRear.setPower(leftPower);
    }
}
