package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    public void initialize() {
        stop = false;
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        String debug = "";
        double tx = m_shooter.getTX();
        double abs_tx = Math.abs(tx);
        if (m_shooter.getTV()) {
            if (abs_tx > 1.2) {
                System.out.println("pivoting");
                double pivot_mod = abs_tx/60.0;
                if (tx > 0) {
                    m_drive.arcadeDrive(Constants.DriveTrain.DRIVE_PIVOT_SPEED_BASE+pivot_mod+0.08, 0, 0); // turn right
                    debug = "turn right";
                }else {
                    m_drive.arcadeDrive(-(Constants.DriveTrain.DRIVE_PIVOT_SPEED_BASE+pivot_mod+0.08), 0, 0); // turn left
                    debug = "turn left";
                }
            }else {
                debug = "seeing target";
                stop = true;
                m_drive.arcadeDrive(0, 0, 0);
    
            }
        }else {
            m_drive.arcadeDrive(0.65, 0, 0); // turn right
            debug = "target not found, spinning until target is seen";

        }
        SmartDashboard.putString("pivot debug", debug);

    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0, 0, 0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
