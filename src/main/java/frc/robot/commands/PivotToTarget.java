package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class PivotToTarget extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_shooter;
    private final DriveTrain m_drive;
    private boolean stop = false;
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public PivotToTarget(Shooter s, DriveTrain d) {
      m_shooter = s;
      m_drive = d;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(s);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double tx = m_shooter.getTX();
        if (Math.abs(tx) > 5) {
            System.out.println("pivoting");
            if (tx > 0) {
                m_drive.arcadeDrive(Constants.DriveTrain.DRIVE_PIVOT_SPEED, 0, 0); // turn right
            }else {
                m_drive.arcadeDrive(-Constants.DriveTrain.DRIVE_PIVOT_SPEED, 0, 0); // turn left
            }
        }else {
            stop = true;
            m_drive.arcadeDrive(0, 0, 0);
        }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0, 0, 0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return stop;
    }
}
