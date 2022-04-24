package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase{
    private MotorController mc_roller;
    private MotorController mc_doghouse;
    private DoubleSolenoid ds_extender;
    private Timer timer;

    public boolean isDown = false;
    public Intake(){
        mc_roller = new CANSparkMax(Constants.Intake.INTAKE_ROLLER_CAN, MotorType.kBrushless);
        mc_doghouse = new VictorSP(Constants.Intake.INTAKE_DOG_HOUSE_PWM);
        timer = new Timer();
        ds_extender = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Intake.INTAKE_SOLENOID_PCM, Constants.Intake.INTAKE_SOLENOID_PCM_2);
    }
    public void extendIntake()
    {
        isDown = true;
        ds_extender.set(Value.kForward);
    }
    public void retractIntake()
    {
        isDown = false;
        ds_extender.set(Value.kReverse);
    }
    public void enableDoghouse() {
        mc_doghouse.set(Constants.Intake.INTAKE_DOGHOUSE_SPEED);
    }
    public void disableDoghouse() {
        mc_doghouse.disable();
    }
    public void enableMotor(boolean reverse){
        if (reverse) {
            mc_roller.set(-Constants.Intake.INTAKE_ROLLER_SPEED);
            mc_doghouse.set(-Constants.Intake.INTAKE_DOGHOUSE_SPEED);
        }else {
            mc_roller.set(Constants.Intake.INTAKE_ROLLER_SPEED);
            mc_doghouse.set(Constants.Intake.INTAKE_DOGHOUSE_SPEED);    
        }
        System.out.println("running mc roller");
    }
    public void disableMotor(){
        mc_roller.disable();
        mc_doghouse.disable();
    }
    public void disablePneumatics(){
        ds_extender.set(Value.kOff);
    }
    
}
