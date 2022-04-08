package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class AutoTurnUntilTarget extends CommandBase {
    private DriveTrain drive;
    private Shooter shoot;
    
    public AutoTurnUntilTarget(DriveTrain dt, Shooter s){
        addRequirements(dt);
        drive = dt;
        this.shoot = s;
    }
    @Override
    public void initialize(){
        System.out.println("initializing autonomous");
    }
    public void execute (){
        drive.arcadeDrive(0.35, 0., 0.);
    }
    @Override
    public void end(boolean interrupted) {
      drive.arcadeDrive(0,0,0);
    }
    @Override
    public boolean isFinished() {
        return shoot.getTV();
    }
}
