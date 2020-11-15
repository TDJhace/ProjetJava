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