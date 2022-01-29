package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoFWD  extends CommandBase{
    private DriveTrain drive;
    public AutoFWD(DriveTrain dt){
        addRequirements(dt);
        drive = dt;
    }
    @Override
    public void initialize(){
        System.out.println("initializing autonomous");
        drive.setOdometryPosition();
    }
    public void execute (){
        //drive.setOdometryPosition();
    }
}

