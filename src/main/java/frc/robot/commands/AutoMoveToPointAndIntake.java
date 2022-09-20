package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

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
        distance = Math.sqrt(Math.pow(pointX - 0, 2) + Math.pow(pointY - 0, 2) )-0.3;
        SmartDashboard.putNumber("Point Distance", distance);
        initDistance = 0;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(d, i);
        
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        initDistance = m_drive.getEncoder();

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
