package ControlCenter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.util.NoSuchElementException;
// import java.util.Scanner;

import Datas.*;
import Interface.window;
import SatConception.Family.*;

public class UIMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // First, we create an instance of Datacenter, which will contains all the
        // satellite and datas

        DataCenter allDatas = new DataCenter();
        DataSaver saver = new DataSaver();

        try {
            // We delete all the existing files in the directories
            allDatas.endProgram();

            // update the data center and the data saver
            satelliteCreator("SatList/SatList.txt", allDatas, saver);

            allDatas.addProcedure("SCRIPTS/ISAESATS/IMAGESCRIPT");
            allDatas.addProcedure("SCRIPTS/ISAESATS/IMAGERSEQUENCE");
            allDatas.addProcedure("REDUNDANT");
            allDatas.addProcedure("IMAGESEQMAIN");
            allDatas.addProcedure("IMAGESEQBACKUP");
            allDatas.addProcedure("REPEAT_IMAGER1DATA_UNTIL_SUCCESS");

            // Now we can begin to create satellites by adding them to allDatas
            // Moreover, it creates the satellite CHANNELS directories if they don't exist
            // yet

            window w = new window(allDatas, saver);
            w.setVisible(true);

        } catch (Exception e) {
            System.out.println(
                    "An error occured with the files. Please delete all the files in the CHANNELS directory. The program exits automatically.");

            System.exit(0);
        }

        // Creons les Procedures de l'enonce (les fichiers texte sont deja pre-ecris et
        // stockes dans le dossier procedure, on ajoute juste le nom de ces procedure a
        // la liste du dataCenter)

        // // Now it begins with all the scanner part
        // String satName = "";
        // String compName = "";
        // String typeInstruction = "";

        // Scanner sc = new Scanner(System.in);
        // System.out.println("Hello. Welcome in the ISAE Satellites Management UI.");
        // System.out.println("Enter a command below.");
        // System.out.println("For command examples, enter EXAMPLES. Enter EXIT when the
        // work is done.");

        // while (sc.hasNext()) {

        // // int data_size = allDatas.getDatas().size();
        // String instruction = sc.next();

        // if (instruction.equals("EXIT")) {
        // // The EXIT instruction closes the scanner and quit the program
        // sc.close();
        // try {
        // allDatas.endProgram();
        // } catch (Exception e) {
        // System.out.println("An error occured.");
        // e.printStackTrace();
        // }
        // System.out.println("Good Bye !");
        // System.out.println("");
        // System.out.println("Here is the list of obtained datas during the session
        // :\n");
        // break;
        // } else if (instruction.equals("EXAMPLES")) {
        // System.out.println("\nHere are a few command examples :");
        // System.out.println("FAM1SAT1:IMAGER1:ON");
        // System.out.println("FAM2SAT:RANDOM1:DATA");
        // System.out.println("FAM1SAT2:IMAGER2:OFF\n");
        // System.out.println("Enter a command below.");
        // } else if (allDatas.VerifInstructionProcedure(instruction)) { // On verifie
        // si la commande concerne une
        // // Procedure existante est compatbible avec le
        // // satellite
        // allDatas.teleProcedure(instruction.split(":")[1], instruction.split(":")[0]);
        // // On lance la
        // // TeleOperation de la
        // // procedure
        // } else {
        // instruction += ": ";

        // Scanner s = new Scanner(instruction).useDelimiter(":");

        // // We obtains the values of the command
        // try {
        // satName = s.next();
        // compName = s.next();
        // typeInstruction = s.next();

        // // We can begin the process
        // System.out.println(allDatas.teleOperation(satName, compName,
        // typeInstruction));
        // } catch (NoSuchElementException e) {
        // System.out.println("Please write a correct command.");
        // } catch (IOException e) {
        // System.out.println("An error occured with the CHANNEL files. The program
        // exits automatically.");
        // e.printStackTrace();
        // System.out.println(e.getLocalizedMessage());
        // System.exit(0);
        // } catch (InterruptedException e) {
        // System.out.println("A fatal error occured. The program exits
        // automatically.");
        // System.exit(0);
        // } catch (ClassNotFoundException e) {
        // System.err.println("Unknown error.");
        // }

        // s.close();

        // }
        // }
        // sc.close();

        // // I just print the Datas List at the end of the process in order to verify
        // the
        // // good functionning.
        // System.out.println(allDatas.getDatas());
        // System.out.println("\nNumber of datas : " + allDatas.getDatas().size());
        // System.out.println("");
    }

    public static void satelliteCreator(String file, DataCenter allDatas, DataSaver saver) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int nbrSat = Integer.parseInt(reader.readLine());
        for (int k = 0; k < nbrSat; k++) {
            String name = reader.readLine();
            int family = Integer.parseInt(reader.readLine());
            saver.CreateSeq("FAM" + family + name);
            if (family == 1) {
                allDatas.addSat(new Fam1(name, "ISAESATS"));
            } else {
                if (family == 2) {
                    allDatas.addSat(new Fam2(name, "XSATS"));
                } else {
                    System.out.println("family " + family + " doesn't exist");
                }
            }
        }
        reader.close();
    }
}