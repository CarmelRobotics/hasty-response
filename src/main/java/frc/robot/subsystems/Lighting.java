package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lighting extends SubsystemBase {
    DigitalOutput aim_true;
    DigitalOutput aim_false;
    public Lighting() {
        aim_true = new DigitalOutput(Constants.Lighting.RED_DIO);
        aim_false = new DigitalOutput(Constants.Lighting.GREEN_DIO);
    }
    public void setAimIndicator(boolean aim) {
        aim_true.set(aim);
        aim_false.set(!aim);
    }
    public void disableAimIndicator(){
        aim_false.set(false);
        aim_true.set(false);
    }
}
