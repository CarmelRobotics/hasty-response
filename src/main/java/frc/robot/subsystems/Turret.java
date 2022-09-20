// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
  private CANSparkMax sp_turret;
    private DigitalInput limit1;
    private DigitalInput limit2;
    private double left_limit;
    private double right_limit;
    private RelativeEncoder enc;
  /** Creates a new ExampleSubsystem. */
  public Turret() {
    sp_turret = new CANSparkMax(Constants.Shooter.LS_CAN,MotorType.kBrushed);
    limit1 = new DigitalInput(Constants.Shooter.DIO_LIM);
   // enc = sp_turret.getEncoder();
    limit2 = new DigitalInput(Constants.Shooter.DIO_LIM2);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  public void Calibrate(){
    while(!limit1.get()){
      sp_turret.set(0.1);
    }
    //left_limit = enc.getPosition();
    while(!limit2.get()){
      sp_turret.set(-0.1);
    }
   // right_limit = enc.getPosition();

  }
  public boolean checkLimit(){
    if(limit1.get()){
      return true;
    }
    else if(limit2.get()){
      return true;
    }
    else{
      return false;
    }
  }
  public void moveTurret(double x){
    sp_turret.set(x);
  }
}
