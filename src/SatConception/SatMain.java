package SatConception;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SatMain {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome in the Satellite Main Program.");
        System.out.println("You basically have nothing to do.");
        System.out.println("Just write below EXIT when your job is done.");

        Scanner sc = new Scanner(System.in);
        File referenceFile = new File("src/CHANNELS/");
        String[] listSat = referenceFile.list();
        // listSat contains all the directory names in CHANNELS

        while (true) {
            // if (sc.hasNext()) {
            // System.out.println("yes");
            // if (sc.next().equals("EXIT")) {
            // sc.close();
            // break;
            // }
            // }

            for (String satName : listSat) {
                String satDir = "src/CHANNELS/" + satName + "/UPLINK";
                File testFile = new File(satDir);
                if (testFile.canRead()) {
                    FileReader in = new FileReader(filename);
                    BufferedReader bin = new BufferedReader(in);
                }
            }
        }

    }

}
