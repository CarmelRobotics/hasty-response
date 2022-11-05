package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FileReadWrite;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends Shoot{
    public AutoShoot(Shooter shooter, Intake intake, BTS bts, FileReadWrite file){
        super(shooter, intake, file, bts);
        addRequirements(shooter, intake, bts);
    }
    @Override
    public void initialize(){
        this.time.reset();
        this.time.start();
        
        System.out.println("running auto shoot");
    }
    public void execute (){
        runShoot();
    }
    @Override
    public void end(boolean interrupted) {
      stopAll();
    }
    @Override
  public boolean isFinished() {
    return false;
  }
} 

