package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lighting extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer m_ledBuffer;
    public Lighting() {
        led = new AddressableLED(4);
        led.setLength(82);
        m_ledBuffer = new AddressableLEDBuffer(82);
        led.setData(m_ledBuffer);
        led.start();



    }
    public void setAimIndicator(boolean aim) {
            System.out.println("setting LEDs");
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, 255, 0, 0);
         }
         
         led.setData(m_ledBuffer);
       
    }
    public void disableAimIndicator(){
   
    }
}
