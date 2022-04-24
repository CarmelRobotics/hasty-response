// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Auto;
import frc.robot.commands.AutoMove;
import frc.robot.commands.Drive;
import frc.robot.commands.HangDown;
import frc.robot.commands.HangUp;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.IntakeClose;
import frc.robot.commands.IntakeOpen;
import frc.robot.commands.OverrideDistance;
import frc.robot.commands.PivotToTarget;
import frc.robot.commands.PivotToTargetNew;
import frc.robot.commands.PrintLog;
import frc.robot.commands.RecordBall;
import frc.robot.commands.ResetServo;
import frc.robot.commands.SetShooterServo;
import frc.robot.commands.Shoot;
import frc.robot.commands.ShootConstant;
import frc.robot.commands.ShootDavid;
import frc.robot.commands.setRingLight;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FileReadWrite;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Shooter;
import pabeles.concurrency.ConcurrencyOps.Reset;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Joystick j_joystick = new Joystick(Constants.Controls.JOYSTICK_USB);
  private final Joystick j_guitar = new Joystick(Constants.Controls.GUITAR_USB);
  private final DriveTrain m_driveTrain = new DriveTrain();
  private final Shooter m_shooter = new Shooter();
  private final Intake m_intake = new Intake();
  private final BTS m_bts = new BTS();
  private final Hanger m_hanger = new Hanger();
  private final Lighting m_lights = new Lighting();
  private final FileReadWrite m_fileIO = new FileReadWrite();
  private final Auto a_auto = new Auto(m_driveTrain, m_shooter, m_intake, m_bts, m_fileIO);
  private final ResetServo m_resetServo = new ResetServo(m_shooter);

  //joystick
  private final JoystickButton b_intakeExtend;
  private final JoystickButton b_intakeRetract;
  private final JoystickButton b_intakeSpin;
  private final JoystickButton b_intakeSpin_rev;
  private final JoystickButton b_hanger_up;
  private final JoystickButton b_hanger_down;
  //private final JoystickButton b_hanger_open;
 // private final JoystickButton b_hanger_closed;
  private final JoystickButton b_runShooter;
  private final JoystickButton b_overrideServo;
  private final JoystickButton b_pivotToTarget;

  //private final JoystickButton b_intakeToShoot;

  private final JoystickButton b_intakeExtend_guitar;
  private final JoystickButton b_intakeRetract_guitar;
  private final JoystickButton b_intakeSpin_rev_guitar;
  private final JoystickButton b_hanger_up_guitar;
  private final JoystickButton b_hanger_down_guitar;
  private final JoystickButton b_resetServoPos;
  private final JoystickButton b_printLog;
  private final JoystickButton b_recordHit;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    b_hanger_up = new JoystickButton(j_joystick, Constants.Controls.BUTTON_HANGER_UP);
    b_hanger_down = new JoystickButton(j_joystick, Constants.Controls.BUTTON_HANGER_DOWN);
    b_runShooter = new JoystickButton(j_joystick, Constants.Controls.BUTTON_SHOOT_FLYWHEEL);
    b_intakeExtend = new JoystickButton(j_joystick, Constants.Controls.BUTTON_INTAKE_EXTEND);
    b_intakeRetract = new JoystickButton(j_joystick, Constants.Controls.BUTTON_INTAKE_RETRACT);
    b_intakeSpin = new JoystickButton(j_joystick, Constants.Controls.BUTTON_INTAKE_ROLLER);
    b_intakeSpin_rev = new JoystickButton(j_joystick, Constants.Controls.BUTTON_INTAKE_REV);
    b_pivotToTarget = new JoystickButton(j_joystick, Constants.Controls.BUTTON_PIVOT_TO_TARGET);
    b_printLog = new JoystickButton(j_joystick, Constants.Controls.BUTTON_PRINT_LOG);
    b_recordHit = new JoystickButton(j_guitar, Constants.Controls.BUTTON_RECORD_HIT_GUITAR);
    // b_hanger_open = new JoystickButton(j_joystick, Constants.Controls.BUTTON_HANGER_OPEN);
    // b_hanger_closed = new JoystickButton(j_joystick, Constants.Controls.BUTTON_HANGER_CLOSE);
   // b_intakeToShoot = new JoystickButton(j_joystick, Constants.Controls.BUTTON_INTAKE_TO_SHOOT);
    b_overrideServo = new JoystickButton(j_joystick, Constants.Controls.BUTTON_OVERRIDE_DISTANCE);
    b_intakeExtend_guitar = new JoystickButton(j_guitar, Constants.Controls.BUTTON_INTAKE_EXTEND_GUITAR);
    b_intakeRetract_guitar = new JoystickButton(j_guitar, Constants.Controls.BUTTON_INTAKE_RETRACT_GUITAR);
    b_intakeSpin_rev_guitar = new JoystickButton(j_guitar, Constants.Controls.BUTTON_INTAKE_REV_GUITAR);
    b_hanger_up_guitar = new JoystickButton(j_guitar, Constants.Controls.BUTTON_HANGER_UP_GUITAR);
    b_hanger_down_guitar = new JoystickButton(j_guitar, Constants.Controls.BUTTON_HANGER_DOWN_GUITAR);
    b_resetServoPos = new JoystickButton(j_joystick, 10);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_lights.setDefaultCommand(new setRingLight(m_lights, true));
    m_driveTrain.setDefaultCommand(new Drive(m_driveTrain, j_joystick));
    m_shooter.setDefaultCommand(new SetShooterServo(m_shooter, j_joystick));
    b_runShooter.whileHeld(new ShootConstant(m_shooter, m_intake, m_bts, m_fileIO));
    b_intakeSpin.whileHeld(new IntakeBall(m_intake, false));
   // b_hanger_open.whenPressed(new HangerHook(m_hanger, Constants.Hanger.HANGER_SERVO_POS_OPEN));
    //b_hanger_closed.whenPressed(new HangerHook(m_hanger, Constants.Hanger.HANGER_SERVO_POS_CLOSED));
    b_overrideServo.whenPressed(new OverrideDistance(m_shooter));
    b_pivotToTarget.whileHeld(new PivotToTarget(m_shooter, m_driveTrain));
    b_resetServoPos.whenPressed(new ResetServo(m_shooter));
    b_printLog.whenPressed(new PrintLog(m_fileIO));
    b_recordHit.whenPressed(new RecordBall(m_fileIO));
    if (Constants.Controls.useGuitar) {
      //.whileHeld(new HangUp(m_hanger));
      //.whileHeld(new HangDown(m_hanger));
      //.whileHeld(new IntakeBall(m_intake, true));
      b_intakeExtend_guitar.whileHeld(new IntakeOpen(m_intake));
      b_intakeRetract_guitar.whileHeld(new IntakeClose(m_intake));
    }else {
      b_hanger_up_guitar.whileHeld(new HangUp(m_hanger));
      b_hanger_down_guitar.whileHeld(new HangDown(m_hanger));
      b_intakeSpin_rev_guitar.whileHeld(new IntakeBall(m_intake, true));
      b_intakeExtend.whenPressed(new IntakeOpen(m_intake));
      b_intakeRetract.whenPressed(new IntakeClose(m_intake));
    }

    //b_intakeToShoot.whileHeld(new IntakeToShoot(m_intake, m_bts));
  }
//This is a comment
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An AutonomousThingy will run in autonomous
    return a_auto;
  }
}
