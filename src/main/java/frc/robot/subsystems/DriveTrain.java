package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;


public class DriveTrain extends SubsystemBase
{
    //initialize speed controllers and their groups.
    private Field2d f_field;
    private CANSparkMax sp_left1;
    private CANSparkMax sp_left2;
    private CANSparkMax sp_right1;
    private CANSparkMax sp_right2;
    private double startEncoderVal_left;
    private double startEncoderVal_right;
    private MotorControllerGroup spg_left;
    private MotorControllerGroup spg_right;
    private DifferentialDrive dd_drive;
    // private Encoder enc_Left, enc_Right;
    public AHRS NAVX = new AHRS(I2C.Port.kOnboard);
    private DifferentialDriveOdometry o_odometry = new DifferentialDriveOdometry(new Rotation2d(0));
    boolean isSpark = false;
    
    //an array of speed controller pointers for spark max specific code
    //public ArrayList<CANSparkMax> sparkMotors; //NOTE: ONLY ACCESS IF isSpark IS TRUE
    
    public DriveTrain(){
        
        sp_left1 = new CANSparkMax(Constants.DriveTrain.DRIVE_CAN_LEFT1, MotorType.kBrushless);
        sp_left2 = new CANSparkMax(Constants.DriveTrain.DRIVE_CAN_LEFT2, MotorType.kBrushless);
        sp_right1 = new CANSparkMax(Constants.DriveTrain.DRIVE_CAN_RIGHT1, MotorType.kBrushless);
        sp_right2 = new CANSparkMax(Constants.DriveTrain.DRIVE_CAN_RIGHT2, MotorType.kBrushless);

        f_field = new Field2d();
        o_odometry.resetPosition(new Pose2d(new Translation2d(6,5), new Rotation2d(0.0)), new Rotation2d(0.0));
        f_field.setRobotPose(new Pose2d(new Translation2d(5,5), new Rotation2d(0.0)));
        spg_left = new MotorControllerGroup(sp_left1, sp_left2);
        spg_right = new MotorControllerGroup(sp_right1, sp_right2);
        dd_drive = new DifferentialDrive(spg_left, spg_right);
        startEncoderVal_left = getEncoderLeft();
        startEncoderVal_right = getEncoderRight();
        // enc_Left = new Encoder(Constants.DriveTrain.DRIVE_DIO_ENC_LEFT1, Constants.DriveTrain.DRIVE_DIO_ENC_LEFT2, false);
        // enc_Right = new Encoder(Constants.DriveTrain.DRIVE_DIO_ENC_RIGHT1, Constants.DriveTrain.DRIVE_DIO_ENC_RIGHT2, false);
        // enc_Left.setDistancePerPulse(Constants.DriveTrain.DRIVE_DISTANCE_PER_PULSE_LEFT);
        // enc_Right.setDistancePerPulse(Constants.DriveTrain.DRIVE_DISTANCE_PER_PULSE_RIGHT);
        // enc_Left.reset();
        // enc_Right.reset();
        
        NAVX.zeroYaw();
    }    
    public void arcadeDrive(double x, double y, double z){
      dd_drive.arcadeDrive(x, (y+(z*.5))); 
      System.out.println("running arcade drive");
    }
    double getEncoderLeft() {
      return ((sp_left1.getEncoder().getPosition() + sp_left2.getEncoder().getPosition())/2.0-startEncoderVal_left)*Constants.DriveTrain.DRIVE_DISTANCE_PER_PULSE;
    }
    double getEncoderRight() {
      return ((sp_right1.getEncoder().getPosition() + sp_right2.getEncoder().getPosition())/2.0-startEncoderVal_right)*Constants.DriveTrain.DRIVE_DISTANCE_PER_PULSE;
    }
    public double getEncoder() {
      return (((-getEncoderLeft())+getEncoderRight())/2.0);
    }
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
      SmartDashboard.putNumber("Encoder Left", getEncoderLeft());
      SmartDashboard.putNumber("Encoder Right", getEncoderRight());

      SmartDashboard.putNumber("Encoder Default Left", startEncoderVal_left);
      SmartDashboard.putNumber("Encoder Default Right", startEncoderVal_right);

      SmartDashboard.putNumber("Encoder Left 1 Raw", sp_left1.getEncoder().getPosition());
      SmartDashboard.putNumber("Encoder Left 2 Raw", sp_left2.getEncoder().getPosition());

      SmartDashboard.putNumber("Encoder Avg", getEncoder());
      o_odometry.update(NAVX.getRotation2d(), -getEncoderLeft(), getEncoderRight());
      SmartDashboard.putData("Field", f_field);
      f_field.setRobotPose(o_odometry.getPoseMeters());
      SmartDashboard.putBoolean("NAVX1 Connected", NAVX.isConnected());
     
    }
    
}
