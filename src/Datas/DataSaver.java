package Datas;

import java.io.*;
import java.util.regex.Matcher;

/**
 * This is the class that models the module whose duty is to save the data taken
 * by a satelitte each time it makes a mesure. It also manages the mesure
 * sequence of the satelittes.
 */

public class DataSaver {

    /**
     * the attribute DIR1 corresponds to the directory where all the data files are
     * stored.
     */
    private static final String DIR1 = "DataFiles/";
    /**
     * the attribute DIR1 corresponds to the directory where the files that contains
     * the sequence of the measures for each satellite.
     */
    private static final String DIR = "SatSequence/";

    public DataSaver() {
    }

    /**
     * Once a satellite is told to make a mesure, we wil save this data on a
     * specific file, on the repository ProjetJava/DataFiles.
     * 
     * @param nameSat the satellite that has done the mesure.
     * @param seq     the number of mesure he is doing.
     * @param dat     the data we collect from his mesure.
     * @throws IOException
     */
    public void saveData(String nameSat, int seq, Data dat) throws IOException {

        String path = DIR1 + "DATA" + nameSat + "000000" + seq + ".bin";
        FileOutputStream fl = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fl);
        oos.writeObject(dat);
        oos.close();

    }

    /**
     * This method creates for a satelitte the file /DATANAMESATNEXTSEQ in the
     * repository DIR, where the sequence is stored. If the file already exists,
     * then it does nothing (in oder to save the sequence that was already here).
     * 
     * @param nameSat the sat we want to create a file to store his sequence.
     * @throws IOException
     */

    public void CreateSeq(String nameSat) throws IOException {
        String path = DIR + "DATA" + nameSat + "NEXTSEQ.bin";
        File dir = new File("SatSequence/");
        File[] directoryListing = dir.listFiles();
        int flag = 0;
        if (directoryListing != null) {
            // We go through all the files of the directory to see wheter the sequence files
            // already exists or not.
            for (File child : directoryListing) {
                path = path.replaceAll("/", Matcher.quoteReplacement(File.separator));
                String st = child.toString();
                flag = path.compareTo(st);
                if (flag == 0) {
                    break;
                }
            }

            // If the file is not on the repository and the repository is empty
            // then we create a new sequence file for the satelitte.
            if (flag != 0 || directoryListing.length == 0) {
                FileOutputStream fl = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fl);
                oos.writeInt(0);
                oos.close();
            }
        }
    }

    /**
     * This method goes to the files where the sequence of a given satelitte is
     * stored, and it updates it when a mesure is done by the satellite. To get the
     * sequence we use the getSeq method defined below.
     * 
     * @param nameSat the name of the satelitte when want to update the sequence.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void updateSeq(String nameSat) throws IOException, ClassNotFoundException {
        int seq = this.getSeq(nameSat);
        String path = DIR + "DATA" + nameSat + "NEXTSEQ.bin";
        FileOutputStream fl = new FileOutputStream(path, false);
        ObjectOutputStream oos = new ObjectOutputStream(fl);
        seq++;
        oos.writeInt(seq);
        oos.close();
    }

    /**
     * This method gets the sequence from the file sequence of a given satellite.
     * 
     * @param nameSat the satelitte from whom we want to get the sequence.
     * @return the sequence stored in the sequence file.
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public int getSeq(String nameSat) throws IOException, ClassNotFoundException {
        String path = DIR + "DATA" + nameSat + "NEXTSEQ.bin";
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        int seq = ois.readInt();
        ois.close();
        return seq;
    }
}
