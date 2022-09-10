package frc.robot.commands;


// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hanger;

/** An example command that uses an example subsystem. */
public class HangUp extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Hanger s;
 
  public HangUp(Hanger subsystem) {
    s = subsystem;
    addRequirements(subsystem);
    //test
  }


  @Override
  public void initialize() {

  }


  @Override
  public void execute() {
    s.moveUp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

