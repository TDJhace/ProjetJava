package ControlCenter;

import java.util.Scanner;

import Datas.DataCenter;
import SatConception.Family.*;

public class UIMain {
    public static void main(String[] args) {

        // First, we create an instance of Datacenter, which will contains all the
        // satellite and datas
        DataCenter allDatas = new DataCenter();

        // Now we can begin to create satellites by adding them to allDatas
        allDatas.addSat(new Fam1("SAT1"));
        allDatas.addSat(new Fam1("SAT2"));
        allDatas.addSat(new Fam2("SAT"));

        // Now it begins with all the scanner part
        String satName = "";
        String compName = "";
        String typeInstruction = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello. Welcome in the ISAE Satellites Management UI.");
        System.out.println("Enter a command below.");
        System.out.println("For command examples, enter EXAMPLES. Enter EXIT when the work is done.");

        while (sc.hasNext()) {
            String instruction = sc.next();
            if (instruction.equals("EXIT")) {
                // The EXIT instruction closes the scanner and quit the program
                sc.close();
                System.out.println("Good Bye !");
                break;
            } else if (instruction.equals("EXAMPLES")) {
                System.out.println("\nHere are a few command examples :");
                System.out.println("FAM1SAT1:IMAGER1:ON");
                System.out.println("FAM2SAT:RANDOMDOUBLE:DATA");
                System.out.println("FAM1SAT2:IMAGER2:OFF\n");
                System.out.println("Enter a command below.");
            } else {
                instruction += ": ";

                Scanner s = new Scanner(instruction).useDelimiter(":");

                // We obtains the values of the command
                satName = s.next();
                compName = s.next();
                typeInstruction = s.next();

                s.close();

                // We can begin the process
                allDatas.teleOperation(satName, compName, typeInstruction);
            }
        }
        sc.close();

        // I just print the Datas List at the end of the process in order to verify the
        // good functionning.
        System.out.println(allDatas.getDatas());
    }
}