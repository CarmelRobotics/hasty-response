package frc.robot.commands;


import frc.robot.Constants;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

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
  private final BTS m_bts;
  private double speed;
  private double speed1;
  private Joystick m_joy;
  private JoystickButton m_override;
  private JoystickButton m_resetServo;
  protected Timer time;
  boolean setServoPos = false;
  public Shoot(Shooter shooter, Intake intake, BTS bts) {
    m_shooter = shooter;
    m_intake = intake;
    m_bts = bts;
    time = new Timer();
    m_joy = new Joystick(0);
    m_override = new JoystickButton(m_joy, 9);
    m_resetServo = new JoystickButton(m_joy, 10);
  }

  @Override
  public void initialize() {
    time.start();
    setServoPos = false;
  }
  
  public void runShoot() { //seperated to reduce reduncency with auto.
    if (time.hasElapsed(1.0)) {
      m_intake.enableDoghouse();
      m_bts.setRoller(Constants.BTS.BTS_SPEED);
    }
    System.out.println("time running command is: " + time.get());
    System.out.println("shooting");
    
    double distance = m_shooter.getDistance(); //inches
    if (m_override.get() == false) {
      if (distance < 105) {
        m_shooter.setServoPosition(0.78);
        speed = 0.7;
        System.out.println("under 105");
        //setServoPos = true;
      }else if (distance > 300) {
        m_shooter.setServoPosition(0.2);
        speed = 1.0;
        System.out.println("over 300");
        //setServoPos = true;
      }else {
        System.out.println("neither");
        double percent = (distance-100.0)/200.0;
        speed = (percent*(1.0/3.0)) + (2.0/3.0+.06);
        m_shooter.setServoPosition(-(percent*.6)+.76);
      }
      System.out.println("Using the maths");
    }
    else if(m_resetServo.get() == true){
      m_shooter.setServoPosition(.75);
      speed = .7;
      System.out.println("Good luck! Shootin from the hip.");
    }
    else{
      speed = .7;
    }

    speed1 = Math.sqrt(19.6*distance)/34.22;
    System.out.println("speed: " + speed);
    System.out.println("overriding?" + m_override.get());

    m_shooter.shoot(speed);

  }
  public void stopAll() {
    m_shooter.stopShoot();
    m_intake.disableDoghouse();
    m_bts.setRoller(0.0);
    time.stop();
    time.reset();

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
    return false;
  }
  
  
}

