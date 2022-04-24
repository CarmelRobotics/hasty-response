
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Shooter extends SubsystemBase {
  private PWMTalonFX mc_flyWheel_back;
  private PWMTalonFX mc_flyWheel2_front;
  // private TalonFX mc_flyWheel_back;
  // private TalonFX mc_flyWheel2_front;
  private Servo sv_servo_l;
  private Servo sv_servo_r;
  private NetworkTable nt_table;
  public boolean isOverridden;
  
  public Shooter() {
    sv_servo_l = new Servo(Constants.Shooter.SERVO_PWM);
    sv_servo_r = new Servo(Constants.Shooter.SERVO_PWM_2);
    isOverridden = false;
    mc_flyWheel_back = new PWMTalonFX(Constants.Shooter.SHOOTER_PWM_0);
    mc_flyWheel2_front = new PWMTalonFX(Constants.Shooter.SHOOTER_PWM_1);
    // mc_flyWheel_back = new TalonFX(Constants.Shooter.SHOOTER_PWM_0);
    // mc_flyWheel2_front = new TalonFX(Constants.Shooter.SHOOTER_PWM_1);
    nt_table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Target Distance", getDistance());
    SmartDashboard.putNumber("Target Y", getTY());
    // This method will be called once per scheduler run
  }
  public void setServoPosition(double value) { //value is 0 to 1
    sv_servo_l.set(value);
    sv_servo_r.set(value);
    //System.out.println("setting servo position to " + value);
  }
  public double getServoPosition() {
    return sv_servo_l.get();
  }
  public void shoot(double speed){
    mc_flyWheel_back.set(-speed);
    mc_flyWheel2_front.set(-speed);
    System.out.println("shooting");
  }
  public void shootAngled(double speed, double backPercent){
    mc_flyWheel_back.set(-speed*backPercent);
    System.out.println("Back Motor:" + -speed*backPercent);
    mc_flyWheel2_front.set(-speed);
    System.out.println("Front Motor" + -speed);
    System.out.println("shooting");
  }
  public void stopShoot(){
    mc_flyWheel_back.set(0);
    mc_flyWheel2_front.set(0);
  }
  public void getEncoderDifference(){
    
  }
  public double getTX() {
    NetworkTableEntry tx = nt_table.getEntry("tx");
    if (!tx.exists()) {
      System.out.println("ERROR: INVALID NETWORK TABLE ENTRY TX");
    }
    return tx.getDouble(0.0);
  }
  public double getTY() {
    NetworkTableEntry ty = nt_table.getEntry("ty");
    if (!ty.exists()) {
      System.out.println("ERROR: INVALID NETWORK TABLE ENTRY TY");
    }
    return ty.getDouble(10.0);
  }
  public boolean getTV() {
    NetworkTableEntry tv = nt_table.getEntry("tv");
    
    if (!tv.exists()) {
      System.out.println("ERROR: INVALID NETWORK TABLE ENTRY TV");
    }
    return tv.getNumber(0.0).intValue() == 1;
  }
  public double getDistance() {
    double targetOffsetAngle_Vertical = getTY();

    double angleToGoalDegrees = Constants.Vision.VISION_CAMERA_ANGLE + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (Math.PI/180.0);

    double distanceFromLimelightToGoalInches = (Constants.Vision.VISION_TARGET_HEIGHT-Constants.Vision.VISION_CAMERA_HEIGHT)/Math.tan(angleToGoalRadians);

    if (!getTV()) {
      return -1.0;
    }else {
      return distanceFromLimelightToGoalInches;
    }
  }
  public void shootRear(double back_speed){
    mc_flyWheel_back.disable();
    mc_flyWheel2_front.set(-back_speed);;
  }
  public void overrideCamera() {
    if(isOverridden){
      System.out.println("using algebra");
      isOverridden = false;
    }
    else{
      System.out.println("good luck!");
      isOverridden = true;
    }
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
