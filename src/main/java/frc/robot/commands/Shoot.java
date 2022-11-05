package frc.robot.commands;


import frc.robot.Constants;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.FileReadWrite;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import javax.annotation.processing.Filer;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;  

/** An example command that uses an example subsystem. */
public class Shoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;
  private final Intake m_intake;
  private final FileReadWrite m_file;
  private final BTS m_bts;
  private double speed;
  private double speed1;
  private Joystick m_joy;
  private JoystickButton m_override;
  private JoystickButton m_resetServo;
  protected Timer time;

  protected Timer autoTimer;
  boolean isAuto = false;
  boolean setServoPos = false;
  protected double autoTime;
  boolean firstLoop = false;
  public Shoot(Shooter shooter, Intake intake, FileReadWrite fileIO, BTS bts) {
    m_shooter = shooter;
    m_intake = intake;
    m_bts = bts;
    m_file = fileIO;
    time = new Timer();
    autoTimer = new Timer();
    m_joy = new Joystick(0);
    m_override = new JoystickButton(m_joy, 9);
    m_resetServo = new JoystickButton(m_joy, 10);

  }
  public Shoot(Shooter shooter, Intake intake, FileReadWrite fileIO, BTS bts, double autoTime) {
    m_shooter = shooter;
    m_intake = intake;
    m_bts = bts;
    m_file = fileIO;
    time = new Timer();
    autoTimer = new Timer();
    m_joy = new Joystick(0);
    m_override = new JoystickButton(m_joy, 9);
    m_resetServo = new JoystickButton(m_joy, 10);
    this.autoTime = autoTime;
    this.isAuto = true;
  }
  @Override
  public void initialize() {
    time.start();
    autoTimer.start();
    setServoPos = false;
  }
  
  public void runShoot() { //seperated to reduce reduncency with auto.
    
    System.out.println("shooting");
    System.out.println("distance: "+ m_shooter.getDistance());
    double distance = m_shooter.getDistance(); //inches
    if (m_override.get() == false) {
      if (distance < Constants.Shooter.MIN_DIST) {
        speed = Constants.Shooter.MIN_SPEED;
        System.out.println("under MIN");
        //setServoPos = true;
      }else if (distance > Constants.Shooter.MAX_DIST) {
        speed = Constants.Shooter.MAX_SPEED;
        System.out.println("over MAX");
        //setServoPos = true;s
      }else {
        System.out.println("neither");
        speed = (((distance - Constants.Shooter.MIN_DIST) / (Constants.Shooter.MAX_DIST - Constants.Shooter.MIN_DIST)) * (Constants.Shooter.MAX_SPEED-Constants.Shooter.MIN_SPEED)) + Constants.Shooter.MIN_SPEED;
        //m_shooter.setServoPosition(-(percent*.6)+.76);
      }
      System.out.println("Using the maths");
    }
    else if(m_resetServo.get() == true){
      //m_shooter.setServoPosition(.75);
      speed = .7;
      System.out.println("Good luck! Shootin from the hip.");
    }
    else{
      speed = .7;
    }

    speed1 = Math.sqrt(19.6*distance)/34.22;
    //speed *=1.03;
    //speed += m_joy.getRawAxis(3)*.4;
    // System.out.println("speed: " + speed);
    // System.out.println("overriding?" + m_override.get());
    m_shooter.shoot(speed);

    if (time.hasElapsed(1.0)) {
      m_intake.enableDoghouse();
      m_bts.setRoller(Constants.BTS.BTS_SPEED);

      if (firstLoop) {
        m_file.addDataShoot(speed, 0.0, distance);
        firstLoop = false;
      }
    }
    System.out.println("shoot speed: " + speed);
    SmartDashboard.putNumber("shooter speed: ", speed);
  }
  public void stopAll() {
    m_shooter.stopShoot();
    m_intake.disableDoghouse();
    m_bts.setRoller(0.0);
    time.stop();
    time.reset();
    autoTimer.stop();
    autoTimer.reset();
  }
  @Override
  public void execute() {
    runShoot();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    stopAll();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return autoTimer.hasElapsed(autoTime) && isAuto;
  }
  
  
}

