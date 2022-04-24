package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Drive extends CommandBase{
    private DriveTrain s_driveTrain;
    private Joystick j_joystick;
    NetworkTableEntry joystickX;
    NetworkTableEntry joystickY;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Drive(DriveTrain dt, Joystick j) {
    s_driveTrain = dt;
    j_joystick = j;
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("RPi");
    joystickX = table.getEntry("joystickX");
    joystickY = table.getEntry("joystickY");

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    joystickX.setDouble(j_joystick.getX());
    joystickY.setDouble(j_joystick.getY());
    s_driveTrain.arcadeDrive(j_joystick.getX(), j_joystick.getY(), 0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
    
}
