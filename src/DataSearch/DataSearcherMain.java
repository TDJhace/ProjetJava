package DataSearch;

import java.util.*;

public class DataSearcherMain {
    public static void main(String[] args) throws Exception {
        //We create an instance of DataSearcher.
        DataSearcher dtSearch = new DataSearcher();
        Scanner sc = new Scanner(System.in);
        // And this is the list of the files corrsesponding to the criteria selection.
        ArrayList<String> lfiles = new ArrayList<>();
        System.out.println("Welcome to our data search software !");
        System.out.println("You can search any data made by a satellite, according to the following criteria");
        System.out.println("From date1 to date 2;data type; name of satellite");
        System.out.println("If you don't want to take one of the criteria on account, please type NONE");
        System.out.println("If you want to see an example of command please type EXAMPLE");
        System.out.println("If you need more information on how to type a command please ty INSTRUCTIONS");
        while(sc.hasNext()){
            String instruction = sc.nextLine();
            //The QUIT instruction closes the scanner and quits the program.
            if(instruction.equals("QUIT")){
                sc.close();
                System.out.println("Bye !");
                break;
            }
            else if(instruction.equals("INSTRUCTIONS")){
                System.out.println("The date format must be the following one :");
                System.out.println("year-month-day hour:minute:second");
                System.out.println("The date1 must be before the date 2");
                System.out.println("There are 3 available satellites : FAM1SAT1, FAM1SAT2 and FAM2SAT2");
            }
            else if(instruction.equals("EXAMPLE")){
                System.out.println("2020-11-12 21:00:00/2020-11-12 21:00:10;image;FAM1SAT1");
            }
            else{
                System.out.println(instruction);
                lfiles = dtSearch.SearchData(instruction);
                int sz = lfiles.size();
                
                // If the list of files is lower than 10, we print it on the terminal.
                if (sz <= 10 ){
                    System.out.printf("There are %d files according to yours criteria \n",lfiles.size());
                    dtSearch.displayList(dtSearch.results(lfiles));
                }
                
                //If there are to many files corresponding to the criteria research, we dont't give the results.
                // Instead, we suggest the usor to refine his criteria.
                else if((sz > 10)){
                    System.out.println("There are two many files corresponding to the criteria you've selected");
                    System.out.println("Please be more precise ");
                }
            }
        }
    }
}