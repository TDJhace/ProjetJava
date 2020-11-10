package DataSearch;

import java.util.*;
import java.io.*;
import Datas.*;

public class DataSearcher {
    
    private HashMap<Date, ArrayList<String>> hdate;
    private HashMap<String, ArrayList<String>> hsat;
    private HashMap<String, ArrayList<String>> hdata;


    public DataSearcher() throws Exception {
        this.hdate = new HashMap<Date,  ArrayList<String>>();
        this.hsat = new HashMap<String,  ArrayList<String>>();
        this.hdata = new HashMap<String,  ArrayList<String>>();
        
        File dir = new File("ProjetJava/DataFiles");
        File[] directoryListing = dir.listFiles();
        if(directoryListing.length == 0){
            throw new Exception("There must be at least one data file saved");
        }
        if (directoryListing != null) {
            for (File child : directoryListing) {
                FileInputStream fis = new FileInputStream(child.toString());
                ObjectInputStream ois = new ObjectInputStream(fis);
                Data dt = (Data) ois.readObject();
                this.addSat(dt.getSatName(), child.toString());
                this.addDate(dt.getTimeData(), child.toString());
                this.addData(dt.valType(), child.toString());
                ois.close();
            }
        }
    }



    public HashMap<Date, ArrayList<String>> getHdate() {
        return this.hdate;
    }

    public HashMap<String, ArrayList<String>> getHsat() {
        return this.hsat;
    }

    public HashMap<String, ArrayList<String>> getHdata() {
        return this.hdata;
    }

    public void addDate(Date date, String flname) {
        if (hdate.containsKey(date)) {
            hdate.get(date).add(flname);
        } else {
            ArrayList<String> listfl = new ArrayList<String>();
            listfl.add(flname);
            hdate.put(date, listfl);
        }
    }

    public void addSat(String sat, String flname){
        if (hsat.containsKey(sat)){
            hsat.get(sat).add(flname);
        }
        else{
            ArrayList<String> listfl = new ArrayList<String>();
            listfl.add(flname);
            hsat.put(sat, listfl);
        }
    }

    public void addData(String data, String flname){
        if (hdata.containsKey(data)){
            hdata.get(data).add(flname);
        }
        else{
            ArrayList<String> listfl = new ArrayList<String>();
            listfl.add(flname);
            hdata.put(data, listfl);
        }
    }

    public ArrayList<String> SearchData(String Criteria) throws Exception {
        ArrayList<String> lfiles = new ArrayList<String>();
        ArrayList<String> ldate = new ArrayList<String>();
        ArrayList<String> ldata = new ArrayList<String>();
        ArrayList<String> lsat = new ArrayList<String>();
        int[] listidx = new int[2];
        listidx[0] = Criteria.indexOf(";");
        listidx[1] = Criteria.indexOf(";", listidx[0]+1);
        
        if(listidx[0] == -1 || listidx[1] ==-1 ){
            throw new Exception("Invalid Criteria");
        }

        String datecriteria = Criteria.substring(0, listidx[0]);
        String datacriteria = Criteria.substring(listidx[0]+1, listidx[1]);
        String satscriteria = Criteria.substring(listidx[1]+1);

        
        if(datacriteria.equals("NONE") == false){
            String type = datacriteria.toLowerCase();
            //System.out.println(type);
            //System.out.println(type.equals("matrix"));
            if(type.equals("matrix")){
                ldata = this.getHdata().get("matrix");
            }
            else if(type.equals("double")){
                ldata = this.getHdata().get("double");
            }
            else{
                System.out.println("Invalid type of data");
            }
        }

        else if(datacriteria.equals("NONE")){
            for(String key : getHdata().keySet()){
                ldata.addAll(getHdata().get(key));
            }
        }

        if(datecriteria.equals("NONE") == false){
            int i = datecriteria.indexOf("/");
            String date1 = datecriteria.substring(0, i)+ " CET 2020";
            String date2 = datecriteria.substring(i+1)+ " CET 2020";
            Set<Date> setkeys = this.hdate.keySet();
            if(this.isDateIn(date1, setkeys ) && this.isDateIn(date2, setkeys )){
                Date[] lk = setkeys.toArray(new Date[setkeys.size()]);
                sortDatelist(lk);
                boolean flag1 = false;
                boolean flag2 = false;
                for(Date s : lk){
                    if(flag1 == false){
                        flag1 = s.toString().equals(date1);
                    }
                    if(flag2 == false){
                        flag2 = s.toString().equals(date2);
                    }

                    if(flag1 == true && flag2 == false){
                        ldate.addAll(hdate.get(s));
                    }
                    if(flag1 == true && flag2 == true){
                        break;
                    }  
                }
            }
            else{
                System.out.println("Please put a valid date");
            }         
        }

        else if(datecriteria.equals("NONE")){
            for(Date d : getHdate().keySet()){
                ldate.addAll(getHdate().get(d));
            }
        }
        
        if(satscriteria.equals("NONE") == false){
            
            String[] sats = satscriteria.split("/");
            for(String nsat : sats){
                if(getHsat().containsKey(nsat)){
                    lsat.addAll(getHsat().get(nsat));
                }
            } 
        }

        else if(satscriteria.equals("NONE")){
            for(String key : getHsat().keySet()){
                lsat.addAll(getHsat().get(key));
            }
        }

        System.out.println("How many files sats crit :"+lsat.size());
        System.out.println("How many files data crit :"+ldata.size());
        System.out.println("How many files date crit "+ldate.size());
        
        for(String fln : lsat){
            if(ldata.contains(fln) && ldate.contains(fln)){
                lfiles.add(fln);
            }
        }

        return lfiles;
                
    }

    public int CompareDate(Date d1, Date d2){

        if(d1.after(d2)){
            return 1;
        }
        else if(d1.before(d2)){
            return -1;
        }
        else{
            return 0;
        }
    }
    public boolean isDateIn(String s, Set<Date> st){
        boolean flag = false;
        int i;
        for(Date d : st){
            i = d.toString().compareTo(s);
            if(i == 0){
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    public void sortDatelist(Date[] ld){
        for (int j = 1; j < ld.length; j++) {
            Date current = ld[j];
            int i = j-1;
            while ((i > -1) && (CompareDate(ld[i],current) == 1)) {
                ld[i+1] = ld[i];
                i--;
            }
            ld[i+1] = current;
        }

    }

    public void displayList(ArrayList<String> l){
        for( String s : l){
            System.out.println(s);
        }
    }


    @Override
    public String toString() {
        return "{" +
            " hdate='" + getHdate() + "\n" +
            ", hsat='" + getHsat() + "\n" +
            ", hdata='" + getHdata() + "\n" +
            "}";
    }

}
