package DataSearch;

import java.util.*;
import java.io.*;
import Datas.*;

/**
 * This the class of the data searching center.
 * It contains all the method in order to search
 * the data files given some criteria.
 */

public class DataSearcher {
    /**
     * Here are the three attributes of the DataSearcher class.
     * Each of them is a HashMap, whose keys will be related to 
     * a criteria that a user can activate when he is searching 
     * for some specific data.
     * hdate is for the date criteria, for each specific date when a
     * mesure was taken, we will make correspond the file of the data.
     * (key date, value ArrayList of the file names).
     * hsat is for the satellite criteria, for each satelitte on the
     * control center, we will store all the mesure it has taken.
     * (key the name of the satelitte, value ArrayList of the file names).
     * hdata is for the data type criteria, for each type of data (here we only
     * have double or matrix), we store the data files from this type.
     * (key the data type, value value ArrayList of the file names).
     */
    private HashMap<Date, ArrayList<String>> hdate;
    private HashMap<String, ArrayList<String>> hsat;
    private HashMap<String, ArrayList<String>> hdata;

    /**
     * This the constructor of the DataSearcher class.
     * The idea is to go through all the data files that are saved
     * on the specific directory ProjetJava/DataFiles, and to read the
     * data that is written on it.
     * Then we feed to the HashMap attributes, the informations they need 
     * according to the criteria we can select on our searching engine.
     * @throws Exception if there is no files in the directory.
     */
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

    /**
     * Since the attributes are private, we define the getters in case 
     * we need to call an attribute on a method.
     * @return hdate
     */

    public HashMap<Date, ArrayList<String>> getHdate() {
        return this.hdate;
    }

    /**
     * Since the attributes are private, we define the getters in case 
     * we need to call an attribute on a method.
     * @return hsat
     */

    public HashMap<String, ArrayList<String>> getHsat() {
        return this.hsat;
    }

    /**
     * Since the attributes are private, we define the getters in case 
     * we need to call an attribute on a method.
     * @return hdata
     */


    public HashMap<String, ArrayList<String>> getHdata() {
        return this.hdata;
    }

    /**
     * This is a simple add to an HashMap method in order to add values on hdate.
     * @param date the date (key)
     * @param flname the string of the file name (value)
     */

    public void addDate(Date date, String flname) {
        if (hdate.containsKey(date)) {
            hdate.get(date).add(flname);
        } else {
            ArrayList<String> listfl = new ArrayList<String>();
            listfl.add(flname);
            hdate.put(date, listfl);
        }
    }
    /**
     * This is a simple add to an HashMap method in order to add values on hdsat.
     * @param sat the name of the satelitte (key)
     * @param flname the string of the file name (value)
     */

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

    /**
     * This is a simple add to an HashMap method in order to add values on hdata.
     * @param data the type of data(key)
     * @param flname the string of the file name (value)
     */

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

    /**
     * This is the main method of our DataSearcher class.
     * we take as parameter a String :
     * @param Criteria which contains the information of the criteria the user
     * wants to select on his research. The format of this is "date1/date2;datatype;namesat1/namesat2".
     * @return The ArrayList of all the file names that correspond to the criteria the user
     * has selected. 
     * @throws Exception if the criteria is written in an invalid format.
     */

    public ArrayList<String> SearchData(String Criteria) throws Exception {
        ArrayList<String> lfiles = new ArrayList<String>();
        ArrayList<String> ldate = new ArrayList<String>();
        ArrayList<String> ldata = new ArrayList<String>();
        ArrayList<String> lsat = new ArrayList<String>();
        /**
         * We use the indexof method to get the position of the 
         * character ";", that is used to separe the different type
         * of criteria.
         */
        int[] listidx = new int[2];
        listidx[0] = Criteria.indexOf(";");
        listidx[1] = Criteria.indexOf(";", listidx[0]+1);
        
        if(listidx[0] == -1 || listidx[1] ==-1 ){
            /**
             * Trows an Exception if the format of the criteria is invalid
             * (ie whitout 2 indexes for the two ";" characters)
             */
            throw new Exception("Invalid Criteria");
        }
        /**
         * We use the substring method to separate each of the different criteria.
         */
        String datecriteria = Criteria.substring(0, listidx[0]);
        String datacriteria = Criteria.substring(listidx[0]+1, listidx[1]);
        String satscriteria = Criteria.substring(listidx[1]+1);

        /**
         * From now on we will 3 pairs of if/elseif blocks to consider, for each criteria,
         * whether it's activated or not.
         */

        /**
         * Here is the case were the data criteria is activated.
         */
        if(datacriteria.equals("NONE") == false){
            //We use the toLowerCase in oder to avoid errors if the criteria is type with Maj activated.
            String type = datacriteria.toLowerCase();
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
            Date[] lk = setkeys.toArray(new Date[setkeys.size()]);
            sortDatelist(lk);
            if(CompareStringDate(lk[0].toString(), date2)){
                System.out.println("The date must be before the first mesure date");
            }
            else if(CompareStringDate(date1,lk[lk.length-1].toString())){
                System.out.println("The date must be before the last mesure date");
                for(Date d : getHdate().keySet()){
                    ldate.addAll(getHdate().get(d));
                }
            }
            else{
                boolean flag1 = false;
                boolean flag2 = false;
                for(Date s : lk){
                    if(flag1 == false){
                        flag1 = CompareStringDate(s.toString(),(date1));
                    }
                    if(flag2 == false){
                        flag2 = CompareStringDate(s.toString(),(date2));
                    }

                    if(flag1 == true && flag2 == false){
                        ldate.addAll(hdate.get(s));
                    }
                    if(flag1 == true && flag2 == true){
                        break;                    }  
                }
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
            if(lsat.size() == 0){
                System.out.println("Invalid name of Sat");
            } 
        }

        else if(satscriteria.equals("NONE")){
            System.out.println(satscriteria);
            for(String key : getHsat().keySet()){
                lsat.addAll(getHsat().get(key));
            }
        }

        System.out.printf("There are %d files according to the statellite critria \n", lsat.size());
        System.out.printf("There are %d files according to the data criteria \n", ldata.size());
        System.out.printf("There are %d files according to the date criteria \n",ldate.size());
        
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

    public static boolean CompareStringDate(String s1, String s2){
        boolean flag = false;
        String[] ls1 = s1.split(" ");
        String[] ls2 = s2.split(" ");
        int m1 = DataSearcher.getMonth(ls1[1]);
        int m2 = DataSearcher.getMonth(ls2[1]);
        String[] time1 = ls1[3].split(":");
        String[] time2 = ls2[3].split(":");
        if(m1 > m2){
            return true;
        }
        else if(m1 < m2){
            return false;
        }
        else{
            int day1 = Integer.parseInt(ls1[2]);
            int day2 = Integer.parseInt(ls2[2]);
            if(day1 > day2){
                return true;
            }
            else if(day1 < day2){
                return false;
            }
            else{
                int h1 = Integer.parseInt(time1[0]);
                int h2 = Integer.parseInt(time2[0]);
                if(h1 > h2){
                    return true;
                }
                else if(h1 < h2){
                    return false;
                }
                else{
                    int min1 = Integer.parseInt(time1[1]);
                    int min2 = Integer.parseInt(time2[1]);
                    if(min1 > min2){
                        return true;
                    }
                    else if(min1 < min2){
                        return false;
                        }
                    else{
                        int sec1 = Integer.parseInt(time1[2]);
                        int sec2 = Integer.parseInt(time2[2]);
                        if(sec1 > sec2){
                            return true;
                        }
                        else if(sec1 < sec2){
                            return false;
                        }
                        else{
                            System.out.println("They are the same date");
                        }

                    }
                }
            }      
        }

    return flag;
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

    public static int getMonth(String s){
        if(s.equals("Jan")){
            return 1;
        }
        else if(s.equals("Feb")){
            return 2;
        }
        else if(s.equals("Mar")){
            return 3;
        }
        else if(s.equals("Apr")){
            return 4;
        }
        else if(s.equals("May")){
            return 5;
        }
        else if(s.equals("Jun")){
            return 6;
        }
        else if(s.equals("Aug")){
            return 8;
        }
        else if(s.equals("Sep")){
            return 9;
        }
        else if(s.equals("Oct")){
            return 10;
        }
        else if(s.equals("Nov")){
            return 11;
        }
        else if(s.equals("Dec")){
            return 12;
        }
        else{
            System.out.println("Please put a valid format for your month");
            return 0;
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
