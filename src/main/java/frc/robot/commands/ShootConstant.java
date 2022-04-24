package frc.robot.commands;


import frc.robot.Constants;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.FileReadWrite;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** An example command that uses an example subsystem. */
public class ShootConstant extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;
  private final Intake m_intake;
  private final BTS m_bts;
  private final FileReadWrite m_fileIO;
  private double speed;
  private double speed1;
  private Joystick m_joy;
  protected Timer time;
  boolean firstLoop = true;
  boolean setServoPos = false;
  public ShootConstant(Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) {
    m_shooter = shooter;
    m_intake = intake;
    m_bts = bts;
    m_fileIO = fileIO;
    time = new Timer();
    m_joy = new Joystick(0);

  }

  @Override
  public void initialize() {
    time.start();
    setServoPos = false;
    firstLoop = true;
    if (!m_intake.isDown) {
      m_intake.extendIntake();
    }
    
  }
  
  public void runShoot() { //seperated to reduce reduncency with auto.
    System.out.println("time running command is: " + time.get());
    System.out.println("shooting");
    
    double distance = m_shooter.getDistance(); //inches
      
    
    System.out.println("speed: " + speed);

    double speed = 0.0;
    double backPercent = 1.0;
    if (distance < Constants.Shooter.MIN_DIST) {
      speed = Constants.Shooter.MIN_SPEED;
    }else if (distance > Constants.Shooter.MAX_DIST) {
      speed = Constants.Shooter.MAX_SPEED;
    }else {
      speed = (((distance - Constants.Shooter.MIN_DIST) / (Constants.Shooter.MAX_DIST - Constants.Shooter.MIN_DIST)) * (Constants.Shooter.MAX_SPEED-Constants.Shooter.MIN_SPEED)) + Constants.Shooter.MIN_SPEED;
    }
    speed *= 0.98;
    double avg_dist = (Constants.Shooter.MIN_DIST + Constants.Shooter.MAX_DIST)/2.0;
    if (distance <= avg_dist) {
      backPercent = 1.0;
    } else {
      backPercent = (distance-avg_dist) / (Constants.Shooter.MAX_DIST-avg_dist) * (-0.1) + 1.0;
    }
    SmartDashboard.putNumber("Shooter Speed", speed);
    m_shooter.shootAngled(speed, backPercent);
    
    if (time.hasElapsed(1.0)) {
      m_intake.enableDoghouse();
      m_bts.setRoller(Constants.BTS.BTS_SPEED);
      if (firstLoop) {
        m_fileIO.addDataShoot(speed, backPercent, distance);
      }
      firstLoop = false;
    }
    if (time.hasElapsed(2.0)) {
      m_intake.disablePneumatics();
    }
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

