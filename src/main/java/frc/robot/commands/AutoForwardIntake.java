package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class AutoForwardIntake extends CommandBase {
    private DriveTrain drive;
    private Intake intake;
    private double speed = 0.5;
    private double distance; //in meters 
    private double initDistance;
    public AutoForwardIntake(DriveTrain dt, Intake i, double dist){
        addRequirements(dt);
        drive = dt;
        distance = dist;
        intake = i;
        initDistance = 0;
    }
    @Override
    public void initialize(){
        System.out.println("initializing autonomous");
        initDistance = drive.getEncoder();
    }
    public void execute (){
        intake.enableMotor(false);
        drive.arcadeDrive(0, -speed, .0);
    }
    @Override
    public void end(boolean interrupted) {
      drive.arcadeDrive(0,0,0);
      intake.disableMotor();
    }
    @Override
  public boolean isFinished() {
    return (drive.getEncoder()-initDistance) > distance;
  }
}
