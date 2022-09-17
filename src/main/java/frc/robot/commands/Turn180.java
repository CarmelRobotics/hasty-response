package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class Turn180 extends PIDCommand {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_drive;
    private boolean stop = false;
    private double initAngle = 0.0;
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public Turn180(DriveTrain d) {
        super(
            new PIDController(Constants.Auto.turnP, Constants.Auto.turnI, Constants.Auto.turnD),
            d::getAngleDif,
            180.0,
            output -> d.arcadeDrive(output, 0.0, 0.0),
            d
          );
        
        getController().enableContinuousInput(-180, 180);
        SmartDashboard.putData("Auto PID Controller", getController());

        m_drive = d;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(d);
        
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_drive.NAVX_initAngle = m_drive.NAVX.getAngle();
        
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0, 0, 0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return getController().atSetpoint();
    }
}
