package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BTS;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FileReadWrite;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class Auto extends SequentialCommandGroup {
    public Auto(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, Turret turret, Lighting light, FileReadWrite fileIO) {
        //Auto2(drive, shooter, intake, bts, fileIO);
        //AutoTest(drive, shooter, intake, bts, fileIO);
        AutoFollowTraj(drive, shooter, intake, bts, fileIO, light, turret);
    }
    private void Auto1(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //Uses limelight to lock onto target: shoots two balls
        addCommands(new AutoIntakeOpen(intake),
            new AutoForwardIntake(drive, intake, .75, 1.5/*value must change later*/),
            new AutoMove(drive, -0.4, 0.5),
            new AutoTurnUntilTarget(drive, shooter),
            new PivotToTarget(shooter, drive),
            new ShootConstant(shooter, intake, bts, fileIO));
    }
    private void Auto2(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //Turns 180 degrees with the navx to look at the target: shoots two balls
        addCommands(new AutoIntakeOpen(intake),
            new AutoForwardIntake(drive, intake, .75, 2.5/*value must change later*/),
            new AutoMove(drive, -0.7, 0.5),
            new Turn180(drive),
            new AutoPivotToTarget(shooter, drive),
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
    private void AutoTest(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO) { //starts out up against the target, shoots to lower, then moves back: shoots one ball
        addCommands(
           new AutoMove(drive, -0.45, 0.5),
           // ball 1
           new AutoTurnToPoint(drive, 1.524, 7.798),
           new AutoMoveToPointAndIntake(drive, intake, 1.524, 7.798),

           //ball 2
        //    new AutoTurnToPoint(drive, 2.21, 4.42),
        //    new AutoMoveToPointAndIntake(drive, intake, 2.21, 4.42)

        new AutoTurnToPoint(drive, 1.524, 7.798+2),
        new AutoMoveToPointAndIntake(drive, intake, 1.524, 7.798+2)

        );
    }
    private void AutoTest(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO, Lighting light, Turret turret) {        addCommands(
        new AutoTurnToPoint(drive, 7.41755, 0.66129)


        );
    }
    private void AutoCompetition2(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO, Lighting light, Turret turret) {
        addCommands(
            new AutoMove(drive, -0.7, 0.8),

            new TurretPivot(shooter, turret, light),
            new Shoot(shooter, intake, fileIO, bts, 2.5)

        );
    }
    private void AutoCompetition(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO, Lighting light, Turret turret) {
        addCommands(
            new TurretPivot(shooter, turret, light),
            new Shoot(shooter, intake, fileIO, bts, 2.5),
            new IntakeOpen(intake),
            new AutoTurnToPoint(drive, 7.41755, 0.66129),
            new AutoMoveToPointAndIntake(drive, intake, 7.41755, 0.66129),

            new AutoMove(drive, -0.5, 0.5),
            
            new AutoTurnToPoint(drive, 5.173, 2.094),
            new AutoMoveToPointAndIntake(drive, intake, 5.173, 2.094),

            new AutoTurnUntilTarget(drive, shooter),
            new TurretPivot(shooter, turret, light),

            new Shoot(shooter, intake,fileIO, bts)
        );

    }
    private void AutoCompetition3(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO, Lighting light, Turret turret) {
        addCommands(
            new IntakeOpen(intake),
            new AutoTurnToPoint(drive, 7.41755, 0.66129),
            new AutoMoveToPointAndIntake(drive, intake, 7.41755, 0.66129),

            new AutoMove(drive, -0.5, 0.5),

            new AutoTurnToPoint(drive, 7.99234, 4.106),

            new TurretPivot(shooter, turret, light),

            new Shoot(shooter, intake,fileIO, bts)
        );

    }
    private void AutoFollowTraj(DriveTrain drive, Shooter shooter, Intake intake, BTS bts, FileReadWrite fileIO, Lighting light, Turret turret) {
        addCommands(
            new AutoTrajectory(drive)
        );
    }
}
