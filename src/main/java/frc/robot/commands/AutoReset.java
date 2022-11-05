package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoReset  extends CommandBase{
    private DriveTrain drive;
    Timer t;
    public AutoReset(DriveTrain dt){
        addRequirements(dt);
        drive = dt;
        t = new Timer();
    }
    @Override
    public void initialize(){
        System.out.println("initializing autonomous");
        
    }
    public void execute (){
        drive.resetPOS();

    }
    @Override
    public void end(boolean interrupted) {
      drive.arcadeDrive(0,0,0);
    }
    @Override
  public boolean isFinished() {
    return t.hasElapsed(0.1);
  }
}

