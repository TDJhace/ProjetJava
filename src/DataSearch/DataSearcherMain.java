package DataSearch;

import java.io.*;
import Datas.*;

public class DataSearcherMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataSearcher dtSearch = new DataSearcher();
        File dir = new File("ProjetJava/DataFiles");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println(child.toString());
                FileInputStream fis = new FileInputStream(child.toString());
                ObjectInputStream ois = new ObjectInputStream(fis);
                Data dt = (Data) ois.readObject();
                dtSearch.addSat(dt.getSatName(), child.toString());
                dtSearch.addDate(dt.getTimeData().toString(), child.toString());
                dtSearch.addData(dt.valType(), child.toString());
                ois.close();
            }
        }
        System.out.println(dtSearch.toString());
    }
}
