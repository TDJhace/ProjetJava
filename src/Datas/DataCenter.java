package Datas;

import java.util.ArrayList;

import SatConception.Satellite;

import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;

/**
 * The DataCenter class is the major class : it contains and refers all the
 * satellites and the datas associated.
 */
public class DataCenter {

    private ArrayList<Satellite> listSats;
    private ArrayList<Data> listDatas;
    private ArrayList<String> Procedures;

    /**
     * @param listSats  is an ArrayList of the Satellites in order to store them
     *                  somewhere
     * @param listDatas is an ArrayList that stored all the taken datas
     */
    public DataCenter() {
        this.listSats = new ArrayList<Satellite>();
        this.listDatas = new ArrayList<Data>();
        this.Procedures = new ArrayList<String>();
    }

    /**
     * Just a simple method to add a data to the DataCenter.
     * 
     * @param dat the data to be added
     */
    public void addData(Data dat) {
        if (dat != null) {
            this.listDatas.add(dat);
        }

    }

    public void addProcedure(String name) {
        if (name != null) {
            this.Procedures.add(name);
        }

    }

    public Boolean FindProcedure(String name) {
        for (String nom : this.Procedures){
            if (nom.equals(name)){
                return(true);
            }
        }
        return(false);
    }

    /**
     * Just a simple method to add a satellite to the DataCenter.
     * 
     * @param dat the data to be added
     */
    public void addSat(Satellite sat) throws IllegalArgumentException {

        if (sat != null) {
            for (Satellite existingSats : this.listSats) {
                // We first check that there is no satellite with the same name
                if (existingSats.getName().equals(sat.getName())) {
                    throw new IllegalArgumentException(
                            "A satellite with the same name already exists, please change name");
                }
            }
            this.listSats.add(sat);
        }

    }

    public ArrayList<Data> getDatas() {
        return this.listDatas;
    }

    @Override
    public String toString() {
        return "Satellites : " + listSats.toString() + "\nDatas : " + listDatas.toString();
    }

    /**
     * This method returns a satellite, given its name
     * 
     * @param satName the satellite name
     * @return the satellite associated to this name and stored in the DataCenter
     *         instance. If the name isn't associated to any of the satellites, this
     *         method returns null.
     */
    private Satellite getSatByName(String satName) {
        for (Satellite s : this.listSats) {
            if (s.getName().equals(satName)) {
                return s;
            }
        }
        return null;
    }

    public Boolean VerifSatExists(String satName){
        for (Satellite s : this.listSats) {
            if (s.getName().equals(satName)) {
                return(true);
            }
        }
        return false;
    }

    /**
     * This method is the method used to interact with the satellites.
     * 
     * @param satName         the name of the aimed satellite
     * @param compName        the name of the aimed component of the previous
     *                        satellite
     * @param typeInstruction a String containing the instruction : basically, "ON",
     *                        "OFF" or "DATA"
     */
    public void teleOperation(String satName, String compName, String typeInstruction) {

        Satellite aimedSat = this.getSatByName(satName);

        if (aimedSat == null) {
            // The satellite doesn't exist in the database
            System.out.println("Sorry, this satellite is not registered in the database.");
        } else {
            // The satellite exists in the database
            if (aimedSat.operation(compName, typeInstruction)) {
                System.out.println("OK");

                // If the satellite is OK, and that we want a measure, we can do it
                if (typeInstruction.equals("DATA")) {
                    this.addData(aimedSat.getData(compName));
                }

            } else {
                System.out.println("KO");
            }

        }

    }

    public Boolean teleOperationProcedure(String satName, String compName, String typeInstruction) {

        Satellite aimedSat = this.getSatByName(satName);

        if (aimedSat == null) {
            // The satellite doesn't exist in the database
            System.out.println("Sorry, this satellite is not registered in the database.");
            return false;
        } else {
            // The satellite exists in the database
            if (aimedSat.operation(compName, typeInstruction)) {
                // If the satellite is OK, and that we want a measure, we can do it
                if (typeInstruction.equals("DATA")) {
                    this.addData(aimedSat.getData(compName));
                }
                return true;

            } else {
                return false;
            }

        }
    }

    public String TraductionProcedure (String procedure){
        if (procedure.split("/").length != 1){
            String NouvelleProcedure = "";
            NouvelleProcedure += procedure.split("/")[0];
            for(int i = 1; i < procedure.split("/").length;i++){
                NouvelleProcedure += ":" + procedure.split("/")[i];
            }
            return NouvelleProcedure;
        }
        return procedure;
    }

    public Boolean VerifInstructionProcedure(String Instruction){
        if(Instruction.split(":").length == 2){                             //Verification du format de l'instruction
            if (this.VerifSatExists(Instruction.split(":")[0])){            //Verification de l'existence du satellite mentioné
                if (this.FindProcedure(Instruction.split(":")[1])){         //Verification de l'existence de la procedure
                    if (Instruction.split(":")[1].split("/").length!=1){    //Verifie si cette procedure concerne seulement une famille de satellite
                        if (this.getSatByName(Instruction.split(":")[0]).getFamily().equals(Instruction.split(":")[1].split("/")[1])){  //Verifie si la famille du satellite correspond bien a celle concernee par la procedure
                            return true;    //Si toutes les verifications sont biens validee, alors cette methode renvoie true
                        }
                    }
                    else{
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean Verifligne(String ligne){
        if (ligne.split(":").length == 2){
            return(true);
        }
        return(false);
    }

    public void StatutToOK(String statut){
        statut = "OK";
    }

    public void StatutToKO(String statut){
        statut = "KO";
    }

    public void teleProcedure(String name, String SatName){
        Boolean statut = false;
        String DerniereLigne = "";
        Boolean PrendreEnCompteCetteLigne = true ;
        try(BufferedReader br = new BufferedReader(new FileReader("src/Procedure/"+this.TraductionProcedure(name)+".txt"))) 
        {
            String line;
            while ((line = br.readLine()) != null) {
                if (PrendreEnCompteCetteLigne){                                                         //On ignore eventuellement cette ligne (apres un ORELSE ou ANDTHEN)
                    if (this.FindProcedure(line)){                                                      //Detection d'une eventuelle "sous-procedure"
                        if (line.split("/").length!=1){                                                 //On verifie si cette procedure concerne seulement une famille de satellite
                            if (this.getSatByName(SatName).getFamily().equals(line.split("/")[1])){         //On verifie que la sous-procedure s'applique bien a la famille de ce satellite
                                statut = this.SousProcedure(line, SatName);
                            }
                        } else {
                            statut = this.SousProcedure(line, SatName);

                        }
                    }
                    String compName = "";
                    String typeInstruction = "";
                    if (this.Verifligne(line)){
                        compName = line.split(":")[0];
                        typeInstruction = line.split(":")[1];
                        statut = this.teleOperationProcedure(SatName, compName, typeInstruction);
                    }
                    if (line.split(" ").length == 2){               //detecion eventuelle d'un REPEAT
                        if (line.substring(0,5).equals("REPEAT")){
                            int n = Integer.parseInt(line.split(" ")[1]);   //On extrait de la ligne le nombre de REPEAT a effectuer
                            for (int i = 0; i < n; i++){                    
                                if (this.FindProcedure(DerniereLigne)) {
                                    if (this.getSatByName(SatName).getFamily().equals(DerniereLigne.split("/")[1])){
                                        statut = this.SousProcedure(DerniereLigne, SatName);
                                    }
                                }
                                if (this.Verifligne(DerniereLigne)){
                                    compName = DerniereLigne.split(":")[0];
                                    typeInstruction = DerniereLigne.split(":")[0];
                                    statut = this.teleOperationProcedure(SatName, compName, typeInstruction);
                                }
                            }
                        }
                    }
                    if (line.equals("ANDTHEN")){                //On traite le cas ou la ligne contient "ANDTHEN"
                        if (statut){
                        }                
                        else {               //Si le statut est sur "KO" (ie la derniere operation etait un succes)
                            PrendreEnCompteCetteLigne = false;  //Alors on regle PrendreEnCompteCetteLigne sur false pour "ignorer" la prochaine ligne
                        }
                    }
                    if (line.equals("ORELSE")){                 //On traite le cas ou la ligne contient "ORELSE"
                        if (statut){               //Si le statut est sur "OK" (ie la derniere operation etait un succes)
                            PrendreEnCompteCetteLigne = false;  //Alors on regle PrendreEnCompteCetteLigne sur false pour "ignorer" la prochaine ligne
                        }
                    }
                    if (line.split("_").length==4){
                        if(line.split("_")[0].equals("REPEAT")){
                            if(this.VerifInstructionProcedure(line.split("_")[1])){
                                if(line.split("_")[2].equals("UNTIL")){
                                    if(line.split("_")[3].equals("SUCCESS")){
                                        statut = false;
                                        while(statut==false){
                                            if(this.getSatByName(SatName).getFamily().equals(line.split("_")[1].split("/")[1])){
                                                statut = this.SousProcedure(line.split("_")[1], SatName);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    DerniereLigne = line;                           //On garde en memoire cette ligne pour anticiper un eventuel REPEAT
                } else {                                        //Si cette ligne n'a pas ete prise en compte (a cause d'un ORELSE ou d'un ANDTHEN)
                    PrendreEnCompteCetteLigne = true;           //On replace PrendreEnCompteCetteLigne pour ne pas ignorer la ligne suivante
                }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if(statut){
            System.out.println("OK");
        }
        else {
            System.out.println("KO");
        }
    }   


public Boolean SousProcedure(String name, String SatName){
    Boolean statut = false;
    String DerniereLigne = "";
    Boolean PrendreEnCompteCetteLigne = true ;
    try(BufferedReader br = new BufferedReader(new FileReader("src/Procedure/"+this.TraductionProcedure(name)+".txt"))) 
    {
        String line;
        while ((line = br.readLine()) != null) {
            if (PrendreEnCompteCetteLigne){                                                         //On ignore eventuellement cette ligne (apres un ORELSE ou ANDTHEN)
                if (this.FindProcedure(line)){                                                      //Detection d'une eventuelle "sous-procedure"
                    if (line.split("/").length!=1){                                                 //On verifie si cette procedure concerne seulement une famille de satellite
                        if (this.getSatByName(SatName).getFamily().equals(line.split("/")[1])){         //On verifie que la sous-procedure s'applique bien a la famille de ce satellite
                            statut = this.SousProcedure(line, SatName);
                        }
                    } else {
                        statut = this.SousProcedure(line, SatName);

                    }
                }
                String compName = "";
                String typeInstruction = "";
                if (this.Verifligne(line)){
                    compName = line.split(":")[0];
                    typeInstruction = line.split(":")[1];
                    statut = this.teleOperationProcedure(SatName, compName, typeInstruction);
                }
                if (line.split(" ").length == 2){               //detecion eventuelle d'un REPEAT
                    if (line.substring(0,5).equals("REPEAT")){
                        int n = Integer.parseInt(line.split(" ")[1]);   //On extrait de la ligne le nombre de REPEAT a effectuer
                        for (int i = 0; i < n; i++){                    
                            if (this.FindProcedure(DerniereLigne)) {
                                if (this.getSatByName(SatName).getFamily().equals(DerniereLigne.split("/")[1])){
                                    statut = this.SousProcedure(DerniereLigne, SatName);
                                }
                            }
                            if (this.Verifligne(DerniereLigne)){
                                compName = DerniereLigne.split(":")[0];
                                typeInstruction = DerniereLigne.split(":")[0];
                                statut = this.teleOperationProcedure(SatName, compName, typeInstruction);
                            }
                        }
                    }
                }
                if (line.equals("ANDTHEN")){                //On traite le cas ou la ligne contient "ANDTHEN"
                    if (statut){
                    }                
                    else {                                  //Si le statut est sur false (ie la derniere operation etait un succes)
                        PrendreEnCompteCetteLigne = false;  //Alors on regle PrendreEnCompteCetteLigne sur false pour "ignorer" la prochaine ligne
                    }
                }
                if (line.equals("ORELSE")){                 //On traite le cas ou la ligne contient "ORELSE"
                    if (statut){                            //Si le statut est sur true (ie la derniere operation etait un succes)
                        PrendreEnCompteCetteLigne = false;  //Alors on regle PrendreEnCompteCetteLigne sur false pour "ignorer" la prochaine ligne
                    }
                }
                if (line.split("_").length==4){
                    if(line.split("_")[0].equals("REPEAT")){
                        if(this.VerifInstructionProcedure(line.split("_")[1])){
                            if(line.split("_")[2].equals("UNTIL")){
                                if(line.split("_")[3].equals("SUCCESS")){
                                    statut = false;
                                    while(statut==false){
                                        if(this.getSatByName(SatName).getFamily().equals(line.split("_")[1].split("/")[1])){
                                            statut = this.SousProcedure(line.split("_")[1], SatName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                DerniereLigne = line;                           //On garde en memoire cette ligne pour anticiper un eventuel REPEAT
            } else {                                        //Si cette ligne n'a pas ete prise en compte (a cause d'un ORELSE ou d'un ANDTHEN)
                PrendreEnCompteCetteLigne = true;           //On replace PrendreEnCompteCetteLigne pour ne pas ignorer la ligne suivante
            }
        }
    }
    catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    return statut;
}   
}
