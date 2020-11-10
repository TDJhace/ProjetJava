package DataSearch;

import java.util.*;

//import java.io.*;
//import Datas.*;

public class DataSearcherMain {
    public static void main(String[] args) throws Exception {
        DataSearcher dtSearch = new DataSearcher();
        Scanner sc = new Scanner(System.in);
        ArrayList<String> lfiles = new ArrayList<>();
        System.out.println("Welcome to our data search software !");
        System.out.println("You can search any data made by a satellite, according to the following criteria");
        System.out.println("From date1 to date 2;data type; name of satellite");
        System.out.println("If you don't want to take one of the criteria on account");
        System.out.println("If you want to see an example of command please type EXAMPLE");
        while(sc.hasNext()){
            String instruction = sc.nextLine();
            if(instruction.equals("QUIT")){
                sc.close();
                System.out.println("Bye !");
                break;
            }
            else if(instruction.equals("EXAMPLE")){
                System.out.println("Tue Nov 10 15:26:31/Tue Nov 10 15:28:11;matrix;FAM1SAT1");
            }
            else{
                System.out.println(instruction);
                lfiles = dtSearch.SearchData(instruction);
                dtSearch.displayList(lfiles);
            }
        }
    }
}