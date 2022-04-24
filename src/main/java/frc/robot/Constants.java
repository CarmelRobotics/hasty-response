// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveTrain {
        public static final int DRIVE_PWM_LEFT1 = 0;
        public static final int DRIVE_PWM_LEFT2 = 1;
        public static final int DRIVE_PWM_RIGHT1 = 2;
        public static final int DRIVE_PWM_RIGHT2 = 3;

        public static final int DRIVE_CAN_LEFT1 = 2;
        public static final int DRIVE_CAN_LEFT2 = 3;
        public static final int DRIVE_CAN_RIGHT1 = 4;
        public static final int DRIVE_CAN_RIGHT2 = 5; 

        public static final int DRIVE_DIO_ENC_LEFT1 = 7;
        public static final int DRIVE_DIO_ENC_LEFT2 = 6;
        public static final int DRIVE_DIO_ENC_RIGHT1 =  9;
        public static final int DRIVE_DIO_ENC_RIGHT2 =  8;
        public static final double DRIVE_DISTANCE_PER_PULSE = 1.22/28.04; //default

        public static final double DRIVE_PIVOT_SPEED = 0.2;
        public static final double DRIVE_SPEED_MULTIPLIER = 0.8;
    }
    public static final class Lighting {
        public static final int RING_DIO = 2;
    }
    public static final class Controls {
        public static final boolean useGuitar = false;

        public static final int BUTTON_OVERRIDE_DISTANCE = 9;
        public static final int JOYSTICK_USB = 0;
        public static final int GUITAR_USB = 1;
        public static final int BUTTON_SHOOT_FLYWHEEL = 1;
        public static final int BUTTON_INTAKE_ROLLER = 2;
        public static final int BUTTON_INTAKE_RETRACT = 3;
        public static final int BUTTON_INTAKE_EXTEND = 4;
        public static final int BUTTON_INTAKE_REV = 5;
        public static final int BUTTON_PIVOT_TO_TARGET = 6;
        public static final int BUTTON_HANGER_DOWN = 7;
        public static final int BUTTON_HANGER_UP = 8;
        public static final int BUTTON_INTAKE_TO_SHOOT = 11;
        public static final int BUTTON_SHOOTER_RESET = 10;
        public static final int BUTTON_PRINT_LOG = 10;
        public static final int BUTTON_FINE_TUNE = 9;

        public static final int BUTTON_INTAKE_EXTEND_GUITAR = 8;
        public static final int BUTTON_INTAKE_RETRACT_GUITAR = 9;
        public static final int BUTTON_RECORD_HIT_GUITAR = 2;
        public static final int BUTTON_INTAKE_REV_GUITAR = 3;
        public static final int BUTTON_HANGER_UP_GUITAR = 4;
        public static final int BUTTON_HANGER_DOWN_GUITAR = 5;
    }
    public static final class Pneumatics{
        public static final int SOLENOID_PCM_1 = 0; //Out
        public static final int SOLENOID_PCM_2 = 1; //In
    }
    public static final class Hanger {
        public static final int SOLINOID_1_1 = 2;
        public static final int SOLINOID_1_2 = 3;

        public static final int SOLINOID_2_1 = 4;
        public static final int SOLINOID_2_2 = 5;

        public static final int HANGER_SERVO_PWM = 8;
        public static final double HANGER_SERVO_POS_OPEN = 0;
        public static final double HANGER_SERVO_POS_CLOSED = 180;

        public static final double HANGER_TOP = -40.0;
        public static final double HANGER_BOTTOM = 0.2;
    }
    public static final class Shooter {
        public static final double SHOOTER_SPEED = 1.0;
        public static final int SHOOTER_PWM_0 = 0;
        public static final int SHOOTER_PWM_1 = 1;
        public static final int SERVO_PWM = 7;
        public static final int SERVO_PWM_2 = 8;
        public static final boolean ADJUST_HOOD = true;

        public static final double MIN_SPEED = 0.46;
        public static final double MAX_SPEED = 0.62;

        public static final double MIN_DIST = 95;
        public static final double MAX_DIST = 230;
    }
    public static final class Vision {
        public static final double VISION_TARGET_HEIGHT = 104.0; //inches
        public static final double VISION_CAMERA_HEIGHT = 36.0; //inches
        public static final double VISION_CAMERA_ANGLE = 31.5; //degrees
    }
    public static final class Intake {
        public static final int INTAKE_ROLLER_CAN = 9;
        public static final int INTAKE_DOG_HOUSE_PWM = 2;
        public static final int INTAKE_SOLENOID_PCM = 0;
        public static final int INTAKE_SOLENOID_PCM_2 = 1;
        public static final double INTAKE_ROLLER_SPEED = -0.5;
        public static final double INTAKE_DOGHOUSE_SPEED = -1.0;

        
    }
    public static final class BTS {
        public static final int BTS_ROLLER_PWM = 3;
        public static final double BTS_SPEED = 1.0;
    }
}
