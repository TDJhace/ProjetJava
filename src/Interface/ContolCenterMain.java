package Interface;

import Datas.DataCenter;
import SatConception.Family.Fam1;
import SatConception.Family.Fam2;

public class ContolCenterMain {
    public static void main(String[] args) {
        DataCenter allDatas = new DataCenter();
        allDatas.addSat(new Fam1("SAT1"));
        allDatas.addSat(new Fam1("SAT2"));
        allDatas.addSat(new Fam2("SAT"));
        window fenetre = new window(allDatas);
        fenetre.setVisible(true);
    }
}
