package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class movementClasses {

    public mainLibrary mainLibrary;

    public double tickPerInch = 50;

    public driverCentricMovement driverCentricMovement;

    public movementClasses(mainLibrary mainLibrary, driverCentricMovement driverCentricMovement) {

        this.mainLibrary = mainLibrary;

        this.driverCentricMovement = driverCentricMovement;

    }

    public void moveForwardInches(double inches, double speed) {

        int TickTarget = (int) Math.round(inches * tickPerInch);

        mainLibrary.resetDriveEncoders();

        mainLibrary.motorFL.setTargetPosition(TickTarget);
        mainLibrary.motorFR.setTargetPosition(TickTarget);
        mainLibrary.motorBL.setTargetPosition(TickTarget);
        mainLibrary.motorBR.setTargetPosition(TickTarget);

        driverCentricMovement.driverMovement(0, speed);

        for (DcMotorEx x : mainLibrary.allMotors) {

            x.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }


        mainLibrary.waitForMotors();

        mainLibrary.resetDriveEncoders();

    }

    public void moveBackwardInches(double inches, double speed) {

        moveForwardInches(-inches, speed);

    }

    public void moveRightInches(double inches, double speed) {

        int TickTarget = (int) Math.round(inches * tickPerInch);

        mainLibrary.resetDriveEncoders();

        mainLibrary.motorFL.setTargetPosition(TickTarget);
        mainLibrary.motorFR.setTargetPosition(-TickTarget);
        mainLibrary.motorBL.setTargetPosition(-TickTarget);
        mainLibrary.motorBR.setTargetPosition(TickTarget);

        for (DcMotor x : mainLibrary.allMotors) {

            x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        }

        driverCentricMovement.driverMovement(speed, 0);

        for (DcMotor x : mainLibrary.allMotors) {

            x.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }


        mainLibrary.waitForMotors();

        mainLibrary.resetDriveEncoders();

    }

    public void moveLeftInches(double inches, double speed) {

        moveRightInches(-inches, speed);

    }
}
