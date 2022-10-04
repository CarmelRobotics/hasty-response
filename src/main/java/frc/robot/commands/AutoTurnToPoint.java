package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class AutoTurnToPoint extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_drive;
    private boolean stop = false;
    private double initAngle = 0.0;
    private double turnToAngle = 90;
    private double pointX;
    private double pointY;

    private int dir = 1; //-1 is left, 1 is right
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AutoTurnToPoint(DriveTrain d, double x, double y) {
        m_drive = d;
        pointX = x;
        pointY = y;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(d);
        
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        //m_drive.NAVX_initAngle = m_drive.o_odometry.getPoseMeters().getRotation().getRadians();
        turnToAngle = Math.atan2(pointY-m_drive.o_odometry.getPoseMeters().getY(), pointX-m_drive.o_odometry.getPoseMeters().getX());
        turnToAngle *= (180.0/Math.PI); //radians to degrees
        if (turnToAngle < 0) {turnToAngle += 360.0;}
        if (turnToAngle > 360.0) {turnToAngle -= 360.0;}

        if (turnToAngle > 180) {
            dir = -1;
        }else {
            dir = 1;
        }
        SmartDashboard.putNumber("Auto turn to angle", turnToAngle);
        SmartDashboard.putNumber("Auto Dir", dir);
        m_drive.NAVX_offset += (3 * -dir);
    }
    @Override
    public void execute() {
        m_drive.arcadeDrive(0.4 * (double) -dir, 0, 0);
        SmartDashboard.putNumber("angle dif value", m_drive.NAVX.getAngle());

    }
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0, 0, 0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      
      return m_drive.getAngleDif(turnToAngle) < 2;
    }
}
