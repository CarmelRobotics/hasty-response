package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FileReadWrite;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Auto extends SequentialCommandGroup {
    public Auto(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) {
        //Auto2(drive, shooter, intake, bts, fileIO);
        Auto4(drive, shooter, intake, bts, fileIO);
    }
    private void Auto1(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //Uses limelight to lock onto target: shoots two balls
        addCommands(new AutoIntakeOpen(intake),
            new AutoForwardIntake(drive, intake, 1.5/*value must change later*/),
            new AutoMove(drive, -0.4, 0.5),
            new AutoTurnUntilTarget(drive, shooter),
            new PivotToTarget(shooter, drive),
            new ShootConstant(shooter, intake, bts, fileIO));
    }
    private void Auto2(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //Turns 180 degrees with the navx to look at the target: shoots two balls
        addCommands(new AutoIntakeOpen(intake),
            new AutoForwardIntake(drive, intake, 1.5/*value must change later*/),
            new AutoMove(drive, -0.4, 0.5),
            new Turn180(drive),
            new ShootConstant(shooter, intake, bts, fileIO));
    }
    private void Auto3(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //starts out up against the target, shoots to lower, then moves back: shoots one ball
        addCommands(
            new ShootLower(shooter, intake, bts),
            new AutoMove(drive, -0.4, 1.5));
            
    }
    private void Auto4(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //starts out up against the target, shoots to lower, then moves back: shoots one ball
        addCommands(
            new AutoMove(drive, -.75, 1.5),
            new AutoIntakeOpen(intake),
            new ShootConstant(shooter, intake, bts, fileIO));
            
    }

}
