package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class PivotToTargetNew extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_shooter;
    private final DriveTrain m_drive;
    private boolean stop = false;

    private boolean turnLeft = true;
    private double left_angle = 0.0;

    private boolean turnRight = false;
    private double right_angle = 0.0;

    private boolean turnMid = false;

    private double navx_start = 0.0;

    private String debugState = "";

    private Timer timer; //avoids error with limelight
    private boolean runTimer = false;
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public PivotToTargetNew(Shooter s, DriveTrain d) {
      m_shooter = s;
      m_drive = d;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(s);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        turnLeft = true;
        turnRight = false;
        turnMid = false;
        runTimer = false;
        navx_start = m_drive.getAngle();
        timer.reset();
    }
    void runDebug() {
        if (turnLeft) {
            debugState = "turning left";
        }else if (turnRight) {
            debugState = "turning right";
        }else if (turnMid) {
            debugState = "turning to mid";
        }
        if (runTimer) {
            debugState = "running timer at: " + timer.get();
        }
        SmartDashboard.putString("pivot debug state", debugState);
        SmartDashboard.putNumber("pivot left angle", left_angle);
        SmartDashboard.putNumber("pivot right angle", right_angle);

    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        runDebug();
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
        double currentAngle = navx_start-m_drive.getAngle();
        if (turnLeft) {
            m_drive.arcadeDrive(-Constants.DriveTrain.DRIVE_PIVOT_SPEED, 0, 0);
        }else if (turnRight) {
            m_drive.arcadeDrive(Constants.DriveTrain.DRIVE_PIVOT_SPEED, 0, 0);
        }else if (turnMid) {
            double mid_angle = (left_angle+right_angle) / 2.0;
            if (Math.abs(currentAngle-mid_angle) > 2.0) {
                if (currentAngle > mid_angle) {
                    m_drive.arcadeDrive(Constants.DriveTrain.DRIVE_PIVOT_SPEED, 0, 0); // turn right
                }else {
                    m_drive.arcadeDrive(-Constants.DriveTrain.DRIVE_PIVOT_SPEED, 0, 0); // turn left
                }
            }else {
                m_drive.arcadeDrive(0, 0, 0);
                stop = true;
            }
        }else {
            stop = true;
        }
        if (!m_shooter.getTV()) {
            if (!runTimer) {
                runTimer = true;
                timer.start();
            }else {
                if (timer.get() > 0.15) {
                    timer.stop();
                    timer.reset();
                    runTimer = false;
                    if (turnLeft) {
                        turnLeft = false;
                        turnRight = true;
                        left_angle = currentAngle;
                    }else {
                        turnRight = false;
                        turnMid = true;
                        right_angle = currentAngle;
                    }
                }
            }
        }else {
            timer.stop();
            timer.reset();
            runTimer = false;
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
