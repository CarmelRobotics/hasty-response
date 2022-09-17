package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class AutoTurnToAngle extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_drive;
    private boolean stop = false;
    private double initAngle = 0.0;
    private double turnToAngle;
    public AutoTurnToAngle(DriveTrain d, int angle) { //degrees
      m_drive = d;
      turnToAngle = angle;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(d);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        initAngle = m_drive.NAVX.getAngle();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        double currentAngle = m_drive.NAVX.getAngle();
        if (currentAngle < turnToAngle) {
            m_drive.arcadeDrive(Constants.DriveTrain.DRIVE_TURN_AUTO, 0.0, 0.0);
        }else {
            stop = true;
            m_drive.arcadeDrive(0.0, 0.0, 0.0);
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