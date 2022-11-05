package frc.robot.commands;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class AutoTrajectory extends RamseteCommand{
    private DriveTrain drive;
    private final static DifferentialDriveVoltageConstraint voltage_contraint = 
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
                Constants.Trajectory.KS,
                Constants.Trajectory.KV,
                Constants.Trajectory.KA
            ),
            Constants.Trajectory.KINEMATICS,
            4
        );
    private final static TrajectoryConfig config = 
        new TrajectoryConfig(
            Constants.Trajectory.MAX_SPEED,
            Constants.Trajectory.MAX_ACCELERATION
        )
        .setKinematics(Constants.Trajectory.KINEMATICS)
        .addConstraint(voltage_contraint);
    private final static Trajectory traj = 
        TrajectoryGenerator.generateTrajectory(
            new Pose2d(0,0,new Rotation2d(0)),
            List.of(
                new Translation2d(1,1),
                new Translation2d(2,-1)
            ),
            new Pose2d(3,0,new Rotation2d(0)),
            config
        );
    public AutoTrajectory(DriveTrain dt){
        super(
            traj,
            dt::getPose,
            new RamseteController(Constants.Trajectory.RAMSETEB, Constants.Trajectory.RAMSETE_ZETA),
            new SimpleMotorFeedforward(
                Constants.Trajectory.KS,
                Constants.Trajectory.KV,
                Constants.Trajectory.KA),
            Constants.Trajectory.KINEMATICS,
            dt::getWheelSpeeds,
            new PIDController(Constants.Trajectory.KP, 0, 0),
            new PIDController(Constants.Trajectory.KP, 0, 0),

            dt::tankDriveVolts,
            dt
            );
        dt.resetOdometry();
        addRequirements(dt);
        drive = dt;
    }
    
    @Override
    public void end(boolean interrupted) {
      drive.arcadeDrive(0,0,0);
    }
}

