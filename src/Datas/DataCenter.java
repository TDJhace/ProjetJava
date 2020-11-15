package Datas;

import java.util.ArrayList;
import java.io.*;

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

    /**
     * Ajoute une procedure a la liste des procedures pree-enregistrees
     * 
     * @param name : nom de la procedure
     */
    public void addProcedure(String name) {
        if (name != null) {
            this.Procedures.add(name);
        }

    }

    /**
     * Verifie si une procedure appartient a la liste des procedures
     * pree-enregistrees
     * 
     * @param name :nome de la procedure
     * @return : true si cette procedure est reconnue, false sinon
     */
    public Boolean FindProcedure(String name) {
        for (String nom : this.Procedures) {
            if (nom.equals(name)) {
                return (true);
            }
        }
        return (false);
    }

    /**
     * Just a simple method to add a satellite to the DataCenter.
     * 
     * @param dat the data to be added
     * @throws Exception
     */
    public void addSat(Satellite sat) throws Exception {

        if (sat != null) {
            for (Satellite existingSats : this.listSats) {
                // We first check that there is no satellite with the same name
                if (existingSats.getName().equals(sat.getName())) {
                    throw new IllegalArgumentException(
                            "A satellite with the same name already exists, please change name");
                }
            }
            this.listSats.add(sat);

            // We also create the CHANNELS directory corresponding to the created satellite
            String satDir = "src/CHANNELS/" + sat.getName() + "/";
            File file = new File(satDir);
            if (!file.exists() && !file.mkdir()) {
                throw new Exception(
                        "A problem occured during the creation of the satellite directory. Please delete any existing File in the CHANNELS directory.");
            }

        }

    }

    public ArrayList<Data> getDatas() {
        return this.listDatas;
    }

    public ArrayList<Satellite> getSats() {
        return this.listSats;
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

    public Boolean VerifSatExists(String satName) {
        for (Satellite s : this.listSats) {
            if (s.getName().equals(satName)) {
                return (true);
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
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public String teleOperation(String satName, String compName, String typeInstruction)
            throws IOException, InterruptedException, ClassNotFoundException {

        // We use tha data center as a copy of the statement of the global system
        // For example, below, we use the infos in the datacenter to be sure that a
        // correct satname was given

        // Moreover, the obtained datas are still stored in the instance of DataCenter
        // calling this method

        Satellite aimedSat = this.getSatByName(satName);

        if (aimedSat == null) {
            // The satellite doesn't exist in the database
            return "Sorry, this satellite is not registered in the database.";
        } else {
            // The given name is correct, we can create a channel file
            String satDir = "src/CHANNELS/" + satName + "/";

            File uplinkFile = new File(satDir + "UPLINK");
            uplinkFile.createNewFile();

            PrintWriter writer = new PrintWriter(uplinkFile);
            writer.println(compName);
            writer.println(typeInstruction);
            writer.close();

            File downlinkFile = new File(satDir + "DOWNLINK");

            while (!downlinkFile.exists()) {
                Thread.sleep(100); // in order to wait for the downlink file to be created and to block all the
                                   // rest of the commands in the control center
            }

            // Thread.sleep(100); // I put a delay in execution here in order to be sure
            // that the file had enough
            // // time to be well created
            // // In fact, I had issues without this delay

            FileReader in = new FileReader(satDir + "DOWNLINK");
            BufferedReader bin = new BufferedReader(in);
            String status;
            do {
                status = bin.readLine();
            } while (status == null);

            bin.close();
            downlinkFile.delete();

            // We now take care of the data (if there is one to get)
            try {
                if (typeInstruction.equals("DATA") && status.equals("OK")) {
                    Thread.sleep(100);
                    Data obtainedData;
                    ObjectInputStream inData = new ObjectInputStream(new FileInputStream(satDir + "DATALINK"));
                    obtainedData = (Data) inData.readObject();
                    inData.close();

                    // just creating an instance of File to delete the DATALINK file
                    File datalink = new File(satDir + "DATALINK");
                    datalink.delete();

                    // adding the data
                    this.addData(obtainedData);
                }
            } catch (NullPointerException e) {
                System.out.println(
                        "Small System Error. The datas weren't comprised. The execution is nonetheless stopped.");
                e.printStackTrace();
                System.exit(0);
            }

            return status;

        }

    }

    /**
     * This function just deletes all the existing files (not the directories !) in
     * the CHANNELS directory.
     * 
     * @throws Exception
     */
    public void endProgram() throws Exception {

        File referenceFile = new File("src/CHANNELS/");
        String[] listFile = referenceFile.list();

        for (String filename : listFile) {
            File delFile = new File("src/CHANNELS/" + filename);
            if (delFile.exists()) {
                delFile.delete();
            }
        }

    }

    /**
     * Certains noms de procedures contiennent des "/", or cela pose un probleme car
     * java interprete les "/" dans un nom de fichier comme un changement de dossier
     * Cela peut causer des problemes lors de l'ouverture de ces fichier Pour palier
     * a ce probleme, les fichiers textes contenant les lignes de la procedures
     * n'auront pas forcement le meme nom que la procedure : Si le nom de la
     * procedure comporte des "/", ils seront remplaces par des "-" dans le nom du
     * fichier text Donc a chaque fois qu'on fera appel a un fichier text (pour
     * l'ouvrir), on utiliser non pas le nom de la procedure (comportant
     * eventuellement des "/"), mais le nom de la procedure apres "traduction"
     * (apres modification par cette methode)
     * 
     * @param procedure : nom de la procedure
     * @return : le nom de la procedure sans changement si il ne comporte pas de
     *         "/", sinon, remplace les "/" par des "-".
     */
    public String TraductionProcedure(String procedure) {
        if (procedure.split("/").length != 1) { // detecte si il y a des "/" dans le nom de la procedure
            String NouvelleProcedure = "";
            NouvelleProcedure += procedure.split("/")[0];
            for (int i = 1; i < procedure.split("/").length; i++) {
                NouvelleProcedure += "-" + procedure.split("/")[i];
            }
            return NouvelleProcedure;
        }
        return procedure;
    }

    /**
     * pour une instruction du type : SATELLITE:PROCEDURE, verifie : Si le satellite
     * existe Si la procedure est bien pre-enregistree Si la procedure ne concerne
     * qu'une famille de satellite Si, dans le cas ou la procedure ne concerne
     * qu'une famille de satellite, que cette famille est bien celle du satellite
     * 
     * @param Instruction : instruction rentree par l'utlisateur dans le terminal
     * @return true si la procedure existe, le satellite aussi, et qu,ils sont
     *         compatbles, false sinon
     */
    public Boolean VerifInstructionProcedure(String Instruction) {
        if (Instruction.split(":").length == 2) { // Verification du format de l'instruction
            if (this.VerifSatExists(Instruction.split(":")[0])) { // Verification de l'existence du satellite mentioné
                if (this.FindProcedure(Instruction.split(":")[1])) { // Verification de l'existence de la procedure
                    if (Instruction.split(":")[1].split("/").length != 1) { // Verifie si cette procedure concerne
                                                                            // seulement une famille de satellite
                        if (this.getSatByName(Instruction.split(":")[0]).getFamily()
                                .equals(Instruction.split(":")[1].split("/")[1])) { // Verifie si la famille du
                                                                                    // satellite correspond bien a celle
                                                                                    // concernee par la procedure
                            return true; // Si toutes les verifications sont biens validee, alors cette methode renvoie
                                         // true
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Lors du traitement d'une procedure, permet de Savoir si une ligne concerne
     * une operation de base (ON, OFF ou DATA pour un composant)
     * 
     * @param ligne : nom de l'instruction contenue dans une ligne d'une procedure
     * @return : true si le format de l'instruction correspond a une operation de
     *         base (ON,OFF ou DATA), false sinon
     */
    public Boolean Verifligne(String ligne) {
        if (ligne.split(":").length == 2) {
            return (true);
        }
        return (false);
    }

    public void StatutToOK(String statut) {
        statut = "OK";
    }

    public void StatutToKO(String statut) {
        statut = "KO";
    }

    /**
     * Cette methode traite l'appel d'une procedure par un utilisateur. Apres
     * verification dans le main, cette methode est invoquee si et seulement si la
     * procedure est bien pre-enregistree dans le data center, et quelle est bien
     * compabible avec le satellite associe Le principe de la methode est le suivant
     * : Ouvrir le fichier text correspondant a la procedure, et le lire ligne par
     * ligne : Chaque ligne est analysee pour savoir si elle indique une opperation
     * classique (ON,OFF ou DATA pour un composant), l'appel a une autre procedure,
     * ou une operation telle que REPEAT, ORELSE, ANDTHEN... Au debut de la methode,
     * un Boolean statut est cree, initialise a false, et indique si la derniere
     * operation de la procedure est un echec (statut=false) ou un succes
     * (statut=true) statut est donc mis a jour lors de la lecture de chaque ligne
     * Plusieurs methodes, telles que SousProcedure ou teleOperationProcedure, qui
     * ne peuvent etre invoquees que par cette methode, permettent de realiser les
     * instructions contenues dans la procedure traitee par cette methode Ces
     * "sous-methodes" sont respectivement tres similaires a teleProcedure et
     * teleOperation, a la difference qu'elles n'ecrivent pas de KO ou de OK, et
     * retournent un Boolean pour indiquer si elles representent un succes ou un
     * echec (Le Boolean retourner sert a mettre a jour statut) Comme l'enonce le
     * demande, le succes de la procedure correspond seulement au succes de sa
     * derniere instruction (i.e derniere ligne), c'est pourquoi c'est seulement à
     * la fin de la methode, apres la lecture complete du fichier texte et
     * l'execution de chaque instruction, que selon statut, on affichera OK ou KO
     * 
     * @param name    Nom de la procedure
     * @param SatName Nom Satellite concerne
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void teleProcedure(String name, String SatName, DataSaver dSave) throws ClassNotFoundException, InterruptedException {
        Boolean statut = false;
        String DerniereLigne = "";
        Boolean PrendreEnCompteCetteLigne = true;
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/Procedure/" + this.TraductionProcedure(name) + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (PrendreEnCompteCetteLigne) { // On ignore eventuellement cette ligne (apres un ORELSE ou ANDTHEN)
                    if (this.FindProcedure(line)) { // Detection d'une eventuelle "sous-procedure"
                        if (line.split("/").length != 1) { // On verifie si cette procedure concerne seulement une
                                                           // famille de satellite
                            if (this.getSatByName(SatName).getFamily().equals(line.split("/")[1])) { // On verifie que
                                                                                                     // la
                                                                                                     // sous-procedure
                                                                                                     // s'applique bien
                                                                                                     // a la famille de
                                                                                                     // ce satellite
                                statut = this.SousProcedure(line, SatName, dSave);
                            }
                        } else {
                            statut = this.SousProcedure(line, SatName, dSave); // Si la sous-procedure ne concerne pas une
                                                                        // famille de satellite en particulier
                                                                        // (ex:REDUNDANT), alors on peut l'appliquer au
                                                                        // satellite

                        }
                    }
                    String compName = "";
                    String typeInstruction = "";
                    if (this.Verifligne(line)) { // On Verifie si l'instruction de la ligne concerne une operation de
                                                 // base (ON, OFF, DATA) pour un composant valide
                        compName = line.split(":")[0];
                        typeInstruction = line.split(":")[1];
                        statut = this.teleOperation(SatName, compName, typeInstruction).equals("OK"); // Si c'est le
                        if(typeInstruction.equals("DATA")){
                            if(statut){
                                int seq = dSave.getSeq(SatName);
                                ArrayList<Data> ldata = this.getDatas();
                                dSave.saveData(SatName, seq, ldata.get(ldata.size() - 1));
                                dSave.updateSeq(SatName);
                            }
                        }                                                                           // cas, on fait
                                                                                                      // appel a
                                                                                                      // teleOperationProcedure
                                                                                                      // pour executer
                                                                                                      // cette
                                                                                                      // opperation
                    }
                    if (line.split(" ").length == 2) { // detecion eventuelle d'un REPEAT
                        if (line.substring(0, 5).equals("REPEAT")) {
                            int n = Integer.parseInt(line.split(" ")[1]); // On extrait de la ligne le nombre de REPEAT
                                                                          // a effectuer
                            for (int i = 0; i < n; i++) {
                                if (this.FindProcedure(DerniereLigne)) {
                                    if (this.getSatByName(SatName).getFamily().equals(DerniereLigne.split("/")[1])) {
                                        statut = this.SousProcedure(DerniereLigne, SatName, dSave);
                                    }
                                }
                                if (this.Verifligne(DerniereLigne)) {
                                    compName = DerniereLigne.split(":")[0];
                                    typeInstruction = DerniereLigne.split(":")[0];
                                    statut = this.teleOperation(SatName, compName, typeInstruction).equals("OK");
                                    if(typeInstruction.equals("DATA")){
                                        if(statut){
                                            int seq = dSave.getSeq(SatName);
                                            ArrayList<Data> ldata = this.getDatas();
                                            dSave.saveData(SatName, seq, ldata.get(ldata.size() - 1));
                                            dSave.updateSeq(SatName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (line.equals("ANDTHEN")) { // On traite le cas ou la ligne contient "ANDTHEN"
                        if (statut) {
                        } else { // Si le statut est sur "KO" (ie la derniere operation etait un succes)
                            PrendreEnCompteCetteLigne = false; // Alors on regle PrendreEnCompteCetteLigne sur false
                                                               // pour "ignorer" la prochaine ligne
                        }
                    }
                    if (line.equals("ORELSE")) { // On traite le cas ou la ligne contient "ORELSE"
                        if (statut) { // Si le statut est sur "OK" (ie la derniere operation etait un succes)
                            PrendreEnCompteCetteLigne = false; // Alors on regle PrendreEnCompteCetteLigne sur false
                                                               // pour "ignorer" la prochaine ligne
                        }
                    }
                    DerniereLigne = line; // On garde en memoire cette ligne pour anticiper un eventuel REPEAT
                } else { // Si cette ligne n'a pas ete prise en compte (a cause d'un ORELSE ou d'un
                         // ANDTHEN)
                    PrendreEnCompteCetteLigne = true; // On replace PrendreEnCompteCetteLigne pour ne pas ignorer la
                                                      // ligne suivante
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if (statut) {
            return "OK";
        } else {
            return "KO";
        }
    }

    /**
     * Cette methode est tres similaire a teleProcedure, elle n'est invoquee
     * seulement si une procedure fait elle meme appel a une procedure Elle
     * n'affiche donc pas de OK ou KO (ce role est reserve a la procedure
     * principale) Elle retourne un Boolean, contrairement a teleProcedure qui de
     * retourne rien
     * 
     * @param name    : Nom de la sous-procedure
     * @param SatName : Nom Satellite concerne
     * @return statut : lorsqu'on le retourne (une fois avoir execute chaque
     *         instruction de la sous-procedure), il permet d'indiquer si
     *         l'exectution de cette sous-procedure est un succes ou un echec, et
     *         permet mettre a jour le statut de la procedure principale
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public Boolean SousProcedure(String name, String SatName, DataSaver dSave) throws ClassNotFoundException, InterruptedException {
        Boolean statut = false;
        String DerniereLigne = "";
        Boolean PrendreEnCompteCetteLigne = true;
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/Procedure/" + this.TraductionProcedure(name) + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (PrendreEnCompteCetteLigne) { // On ignore eventuellement cette ligne (apres un ORELSE ou ANDTHEN)
                    if (this.FindProcedure(line)) { // Detection d'une eventuelle "sous-procedure"
                        if (line.split("/").length != 1) { // On verifie si cette procedure concerne seulement une
                                                           // famille de satellite
                            if (this.getSatByName(SatName).getFamily().equals(line.split("/")[1])) { // On verifie que
                                                                                                     // la
                                                                                                     // sous-procedure
                                                                                                     // s'applique bien
                                                                                                     // a la famille de
                                                                                                     // ce satellite
                                statut = this.SousProcedure(line, SatName, dSave);
                            }
                        } else {
                            statut = this.SousProcedure(line, SatName, dSave);

                        }
                    }
                    String compName = "";
                    String typeInstruction = "";
                    if (this.Verifligne(line)) {
                        compName = line.split(":")[0];
                        typeInstruction = line.split(":")[1];
                        statut = this.teleOperation(SatName, compName, typeInstruction).equals("OK");
                        if(typeInstruction.equals("DATA")){
                            if(statut){
                                int seq = dSave.getSeq(SatName);
                                ArrayList<Data> ldata = this.getDatas();
                                dSave.saveData(SatName, seq, ldata.get(ldata.size() - 1));
                                dSave.updateSeq(SatName);
                            }
                        }
                    }
                    if (line.split(" ").length == 2) { // detecion eventuelle d'un REPEAT
                        if (line.substring(0, 5).equals("REPEAT")) {
                            int n = Integer.parseInt(line.split(" ")[1]); // On extrait de la ligne le nombre de REPEAT
                                                                          // a effectuer
                            for (int i = 0; i < n; i++) {
                                if (this.FindProcedure(DerniereLigne)) {
                                    if (this.getSatByName(SatName).getFamily().equals(DerniereLigne.split("/")[1])) {
                                        statut = this.SousProcedure(DerniereLigne, SatName, dSave);
                                    }
                                }
                                if (this.Verifligne(DerniereLigne)) {
                                    compName = DerniereLigne.split(":")[0];
                                    typeInstruction = DerniereLigne.split(":")[0];
                                    statut = this.teleOperation(SatName, compName, typeInstruction).equals("OK");
                                    if(typeInstruction.equals("DATA")){
                                        if(statut){
                                            int seq = dSave.getSeq(SatName);
                                            ArrayList<Data> ldata = this.getDatas();
                                            dSave.saveData(SatName, seq, ldata.get(ldata.size() - 1));
                                            dSave.updateSeq(SatName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (line.equals("ANDTHEN")) { // On traite le cas ou la ligne contient "ANDTHEN"
                        if (statut) {
                        } else { // Si le statut est sur false (ie la derniere operation etait un succes)
                            PrendreEnCompteCetteLigne = false; // Alors on regle PrendreEnCompteCetteLigne sur false
                                                               // pour "ignorer" la prochaine ligne
                        }
                    }
                    if (line.equals("ORELSE")) { // On traite le cas ou la ligne contient "ORELSE"
                        if (statut) { // Si le statut est sur true (ie la derniere operation etait un succes)
                            PrendreEnCompteCetteLigne = false; // Alors on regle PrendreEnCompteCetteLigne sur false
                                                               // pour "ignorer" la prochaine ligne
                        }
                    }
                    if (line.split("_").length == 4) {
                        if (line.split("_")[0].equals("REPEAT")) {
                            if (this.VerifInstructionProcedure(line.split("_")[1])) {
                                if (line.split("_")[2].equals("UNTIL")) {
                                    if (line.split("_")[3].equals("SUCCESS")) {
                                        statut = false;
                                        while (statut == false) {
                                            if (this.getSatByName(SatName).getFamily()
                                                    .equals(line.split("_")[1].split("/")[1])) {
                                                statut = this.SousProcedure(line.split("_")[1], SatName, dSave);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    DerniereLigne = line; // On garde en memoire cette ligne pour anticiper un eventuel REPEAT
                } else { // Si cette ligne n'a pas ete prise en compte (a cause d'un ORELSE ou d'un
                         // ANDTHEN)
                    PrendreEnCompteCetteLigne = true; // On replace PrendreEnCompteCetteLigne pour ne pas ignorer la
                                                      // ligne suivante
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return statut;
    }
}
