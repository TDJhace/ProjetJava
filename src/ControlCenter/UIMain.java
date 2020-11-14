package ControlCenter;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Datas.*;
import Interface.window;
import SatConception.Family.*;

public class UIMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // First, we create an instance of Datacenter, which will contains all the
        // satellite and datas

        DataCenter allDatas = new DataCenter();
        DataSaver saver = new DataSaver();

        try {
            // We delete all the existing files in the directories
            allDatas.endProgram();
            saver.CreateSeq("FAM1SAT1");
            saver.CreateSeq("FAM1SAT2");
            saver.CreateSeq("FAM2SAT");

            // Now we can begin to create satellites by adding them to allDatas
            // Moreover, it creates the satellite CHANNELS directories if they don't exist
            // yet
            allDatas.addSat(new Fam1("SAT1"));
            allDatas.addSat(new Fam1("SAT2"));
            allDatas.addSat(new Fam2("SAT"));

            window w = new window(allDatas, saver);
            w.setVisible(true);
        } catch (Exception e) {
            System.out.println(
                    "An error occured with the files. Please delete all the files in the CHANNELS directory. The program exits automatically.");
            System.exit(0);
        }

        // Now it begins with all the scanner part
        String satName = "";
        String compName = "";
        String typeInstruction = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello. Welcome in the ISAE Satellites Management UI.");
        System.out.println("Enter a command below.");
        System.out.println("For command examples, enter EXAMPLES. Enter EXIT when the work is done.");

        while (sc.hasNext()) {

            // int data_size = allDatas.getDatas().size();
            String instruction = sc.next();

            if (instruction.equals("EXIT")) {
                // The EXIT instruction closes the scanner and quit the program
                sc.close();
                try {
                    allDatas.endProgram();
                } catch (Exception e) {
                    System.out.println("An error occured.");
                    e.printStackTrace();
                }
                System.out.println("Good Bye !");
                System.out.println("");
                System.out.println("Here is the list of obtained datas during the session :\n");
                break;
            } else if (instruction.equals("EXAMPLES")) {
                System.out.println("\nHere are a few command examples :");
                System.out.println("FAM1SAT1:IMAGER1:ON");
                System.out.println("FAM2SAT:RANDOM1:DATA");
                System.out.println("FAM1SAT2:IMAGER2:OFF\n");
                System.out.println("Enter a command below.");
            } else {
                instruction += ": ";

                Scanner s = new Scanner(instruction).useDelimiter(":");

                // We obtains the values of the command
                try {
                    satName = s.next();
                    compName = s.next();
                    typeInstruction = s.next();

                    // We can begin the process
                    System.out.println(allDatas.teleOperation(satName, compName, typeInstruction));
                } catch (NoSuchElementException e) {
                    System.out.println("Please write a correct command.");
                } catch (IOException e) {
                    System.out.println("An error occured with the CHANNEL files. The program exits automatically.");
                    e.printStackTrace();
                    System.out.println(e.getLocalizedMessage());
                    System.exit(0);
                } catch (InterruptedException e) {
                    System.out.println("A fatal error occured. The program exits automatically.");
                    System.exit(0);
                } catch (ClassNotFoundException e) {
                    System.err.println("Unknown error.");
                }

                s.close();

            }
        }
        sc.close();

        // I just print the Datas List at the end of the process in order to verify the
        // good functionning.
        System.out.println(allDatas.getDatas());
        System.out.println("\nNumber of datas : " + allDatas.getDatas().size());
        System.out.println("");
    }
}
