package DataSearch;

import java.util.*;

public class DataSearcher {
    
    private HashMap<String, ArrayList<String>> hdate;
    private HashMap<String, ArrayList<String>> hsat;
    private HashMap<String, ArrayList<String>> hdata;


    public DataSearcher() {
        this.hdate = new HashMap<String,  ArrayList<String>>();
        this.hsat = new HashMap<String,  ArrayList<String>>();
        this.hdata = new HashMap<String,  ArrayList<String>>();
    }



    public HashMap<String, ArrayList<String>> getHdate() {
        return this.hdate;
    }

    public HashMap<String, ArrayList<String>> getHsat() {
        return this.hsat;
    }

    public HashMap<String, ArrayList<String>> getHdata() {
        return this.hdata;
    }

    public void addDate(String date, String flname){
        if (hdate.containsKey(date)){
            hdate.get(date).add(flname);
        }
        else{
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


    @Override
    public String toString() {
        return "{" +
            " hdate='" + getHdate() + "\n" +
            ", hsat='" + getHsat() + "\n" +
            ", hdata='" + getHdata() + "\n" +
            "}";
    }


    

}
