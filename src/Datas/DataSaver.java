package Datas;

import java.io.*;
import java.util.regex.Matcher;

public class DataSaver {

    private static final String dir1 = "ProjetJava/DataFiles/";
    private static final String dir = "ProjetJava/SatSequence/";

    public DataSaver(){
    }
    
    public void saveData(String nameSat,int seq, Data dat) throws IOException{
        
        String path = dir1+"DATA"+nameSat+"000000"+seq+".bin";
        FileOutputStream fl = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fl);
        oos.writeObject(dat);
        oos.close();

    }

    public void CreateSeq(String nameSat) throws IOException{
        String path = dir+"DATA"+nameSat+"NEXTSEQ.bin";
        File dir = new File("ProjetJava/SatSequence");
        File[] directoryListing = dir.listFiles();
        int flag = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {
                path = path.replaceAll("/", Matcher.quoteReplacement(File.separator));
                String st = child.toString();
                flag = path.compareTo(st);
                if(flag == 0){
                    break;
                }
            }
            
            if(flag != 0 || directoryListing.length == 0){
                FileOutputStream fl = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fl);
                oos.writeInt(0);
                oos.close();
            }
        }
    }

    public void updateSeq(String nameSat)throws IOException, ClassNotFoundException {
        int seq = this.getSeq(nameSat);
        String path = dir+"DATA"+nameSat+"NEXTSEQ.bin";
        FileOutputStream fl = new FileOutputStream(path, false);
        ObjectOutputStream oos = new ObjectOutputStream(fl);
        seq ++;
        oos.writeInt(seq);
        oos.close();
    }

    public int getSeq(String nameSat)  throws IOException, ClassNotFoundException {
        String path = dir+"DATA"+nameSat+"NEXTSEQ.bin";
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        int seq = ois.readInt();
        ois.close();
        return seq;
    }
}
    

