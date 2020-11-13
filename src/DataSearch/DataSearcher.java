package DataSearch;

import java.util.*;
import java.io.*;
import Datas.*;
import java.text.SimpleDateFormat;

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
         * From now on we will 3 pairs of switch/case blocks to consider, for each criteria,
         * whether it's activated or not.
         */

        int cdate = datecriteria.compareTo("NONE");
        int cdata = datacriteria.compareTo("NONE");
        int cstat = satscriteria.compareTo("NONE");

        /**
         * The first switch if for the date criteria.
         */

        switch (cdate) {
            /**
             * This is the case when the usor don't want to add a date criteria to his
             * research to we take in account all the mesure of hdate.
             */
            case 0:
                for(Date d : getHdate().keySet()){
                ldate.addAll(getHdate().get(d));
                }
            break;

            /**
             * This is the case where the usor wants to add a date criteria on his research.
             * Then we will have to check all the values of hdate corresponding to the interval
             * [date1, date2] (it's asked to the usor to put a date1 that is before the date2).
             */
        
            default:
                int i = datecriteria.indexOf("/");
                //If the usor has not typed 2 dates separated by "/", then we break the case, 
                // Ask the usor to type the dates on the correect format.
                if(i == -1){
                    System.out.println("Please type two dates that are separated by the / character");
                    break;
                }
                String sdate1 = datecriteria.substring(0, i);
                String sdate2 = datecriteria.substring(i+1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = sdf.parse(sdate1);
                Date date2 = sdf.parse(sdate2);
                Set<Date> setkeys = getHdate().keySet();
                Date[] lk = setkeys.toArray(new Date[setkeys.size()]);
                sort(lk, 0, lk.length - 1);;
                //We check if the date2 is after the first mesure date.
                // If not, then it has no sense to look ad the mesures taken on the interval [date1,date2].
                if(CompareDate(lk[0], date2) ==1){
                    System.out.println("The date2 must be after the first mesure date");
                    break;
                }
                //Another test, we check if the date1 is before the last mesure date.
                //If not, then it has no sense to look ad the mesures taken on the interval [date1,date2].
                else if(CompareDate(date1,lk[lk.length-1]) == 1){
                    System.out.println("The date1 must be before the last mesure date");
                    break;
                }
                int flag1 = -1;                    
                int flag2 = -1;
                for(Date s : lk){
                    if(flag1 == -1){
                        flag1 = CompareDate(s,date1);
                    }
                    if(flag2 == -1){
                        flag2 = CompareDate(s,(date2));
                    }
                    if(flag1 == 1 && flag2 == -1){
                        ldate.addAll(hdate.get(s));
                    }
                    if(flag1 == 1 && flag2 == 1){
                        break;                    
                    }  
                }    
            break;
        }
        /**
         * The second switch is for the data type criteria.
         */

        switch (cdata) {
            case 0:
                for(String key : getHdata().keySet()){
                    ldata.addAll(getHdata().get(key));
                }
                
                break;
        
            default:
                String type = datacriteria.toLowerCase();
                if(type.equals("image")){
                    ldata = getHdata().get("image");
                }
                else if(type.equals("double")){
                    ldata = getHdata().get("double");
                }
                else if(type.equals("localisation")){
                    ldata = getHdata().get("localisation");
                }
                else{
                    System.out.println("Invalid type of data");
                }
                break;
        }
        
        switch (cstat) {
            case 0:
                for(String key : getHsat().keySet()){
                lsat.addAll(getHsat().get(key));
                }
                break;
        
            default:
                String[] sats = satscriteria.split("/");
                for(String nsat : sats){
                    if(getHsat().containsKey(nsat)){
                        lsat.addAll(getHsat().get(nsat));
                    }
                }
                if(lsat.size() == 0){
                    System.out.println("Invalid name of Sat");
                } 
                break;
        }
        
        System.out.printf("There are %d files according to the statellite criteria \n", lsat.size());
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

    public void merge(Date[] arr, int l, int m, int r){
        
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        Date L[] = new Date[n1];
        Date R[] = new Date[n2];
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (CompareDate(L[i],R[j]) ==-1) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    void sort(Date[] arr, int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
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
