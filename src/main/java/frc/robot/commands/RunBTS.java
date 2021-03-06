package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BTS;

public class RunBTS extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private BTS m_bts;
    private double speed;
    
    public RunBTS(BTS subsystem, double sp) {
      m_bts = subsystem;
      speed = sp;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      System.out.println("run bts elevator");
      m_bts.setRoller(speed);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_bts.setRoller(0.0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
