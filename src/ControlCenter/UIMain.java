package ControlCenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Datas.*;
import SatConception.Family.*;


public class UIMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // First, we create an instance of Datacenter, which will contains all the
        // satellite and datas
        DataCenter allDatas = new DataCenter();
        DataSaver saver = new DataSaver();

        // Now we can begin to create satellites by adding them to allDatas
        allDatas.addSat(new Fam1("SAT1"));
        saver.CreateSeq("FAM1SAT1");
        allDatas.addSat(new Fam1("SAT2"));
        saver.CreateSeq("FAM1SAT2");
        allDatas.addSat(new Fam2("SAT"));
        saver.CreateSeq("FAM2SAT");

        // Now it begins with all the scanner part
        String satName = "";
        String compName = "";
        String typeInstruction = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello. Welcome in the ISAE Satellites Management UI.");
        System.out.println("Enter a command below.");
        System.out.println("For command examples, enter EXAMPLES. Enter EXIT when the work is done.");

        while (sc.hasNext()) {

            int data_size = allDatas.getDatas().size();
            String instruction = sc.next();
        
            if (instruction.equals("EXIT")) {
                // The EXIT instruction closes the scanner and quit the program
                sc.close();
                System.out.println(saver.getSeq("FAM1SAT1"));
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
                int seq = saver.getSeq(satName);
                allDatas.teleOperation(satName, compName, typeInstruction);
                
                //If a data mesure is done correctly, then we update the sequence of the satellite that
                // has done the mesure.
                // And we save the mesure on a specif file, thanks to the dataSaver methods.
                if(data_size < allDatas.getDatas().size()){
                    ArrayList<Data> ldata = allDatas.getDatas();
                    saver.saveData(satName, seq, ldata.get(ldata.size()-1));
                    saver.updateSeq(satName);    
                }
            }
        }
        sc.close();

        // I just print the Datas List at the end of the process in order to verify the
        // good functionning.
        System.out.println(allDatas.getDatas());
    }
}
