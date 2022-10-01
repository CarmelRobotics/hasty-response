package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class AutoMoveToPointAndIntake extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_drive;
    private final Intake m_intake;
    private double pointX;
    private double pointY;
    private double speed = 0.4;
    private double distance; //in meters 
    private double initDistance;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AutoMoveToPointAndIntake(DriveTrain d, Intake i, double x, double y) {
        m_drive = d;
        m_intake = i;
        pointX = x;
        pointY = y;
        
        initDistance = 0;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(d, i);
        
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        initDistance = m_drive.getEncoder();
        distance = Math.sqrt(Math.pow(pointX - m_drive.getPos().getX(), 2) + Math.pow(pointY - m_drive.getPos().getY(), 2) )-0.4;
        SmartDashboard.putNumber("Point Distance", distance);
    }
    @Override
    public void execute() {
        m_intake.enableMotor(false);
        m_drive.arcadeDrive(0, -speed, .0);


    }
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0,0,0);
        m_intake.disableMotor();  
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return Math.abs(m_drive.getEncoder()-initDistance)>Math.abs(distance);
    }
}
