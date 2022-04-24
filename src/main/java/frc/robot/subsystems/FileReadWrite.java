package frc.robot.subsystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FileReadWrite extends SubsystemBase {
    private File log;
    private ArrayList<Boolean> success = new ArrayList<Boolean>();
    private ArrayList<Double> speeds = new ArrayList<Double>();
    private ArrayList<Double> backPercents = new ArrayList<Double>(); 
    private ArrayList<Double> distances = new ArrayList<Double>(); 
    public FileReadWrite() {
        log = new File("/home/lvuser/log.txt");
        
    }
    public void addDataShoot(double speed, double backPercent, double distance) {
        speeds.add(speed);
        backPercents.add(backPercent);
        distances.add(distance);
        success.add(false);
        updateLog();
    }
    public void addDataSuccess() {
        int lastIndex = success.size()-1;
        success.set(lastIndex, true);
        updateLog();
    }
    public void updateLog() {
        try {
            System.out.println("new log");
            FileWriter log_writer = new FileWriter(log);
            BufferedWriter writer = new BufferedWriter(log_writer);
            PrintWriter print_writer = new PrintWriter(writer);
            print_writer.println("SUCCESSFUL SHOTS");
            print_writer.println("----------------");
            for (int i = 0; i < speeds.size(); i++) {
                if (success.get(i).booleanValue()) {
                    print_writer.println("speed: " + speeds.get(i).toString() + " \tback percent: " + backPercents.get(i).toString() + "\tdistance: " + distances.get(i).toString());
                }
            }
            print_writer.println("UNSUCCESSFUL SHOTS");
            print_writer.println("----------------");
            for (int i = 0; i < speeds.size(); i++) {
                if (!success.get(i).booleanValue()) {
                    print_writer.println("speed: " + speeds.get(i).toString() + " \tback percent: " + backPercents.get(i).toString() + "\tdistance: " + distances.get(i).toString());
                }
            }
            print_writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: log.txt");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    public void printLog() {
        try {
            Scanner log_reader = new Scanner(log);
            while (log_reader.hasNextLine()) {
                System.out.println(log_reader.nextLine());
            }
            log_reader.close();
        }catch (FileNotFoundException e) {
            System.out.println("error: log file not found");
        }
    }
}
