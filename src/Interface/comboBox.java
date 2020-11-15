package Interface;

import javax.swing.JComboBox;

import Datas.DataCenter;
import Datas.DataSaver;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class comboBox {
    private JComboBox<String> cbox;

    public comboBox(DataCenter allDatas, History history, DataSaver dSave) {
        cbox = new JComboBox<>(new String[] { "Procedures ?", "FAM1SAT1:SCRIPTS/ISAESATS/IMAGESCRIPT",
                "FAM1SAT2:SCRIPTS/ISAESATS/IMAGESCRIPT", "FAM1SAT1:REDUNDANT", "FAM1SAT2:REDUNDANT",
                "FAM2SAT:REDUNDANT" });
        cbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String procedure = (String) cbox.getSelectedItem();
                try {
                    if (!procedure.equals("Procedures ?"))
                        procedure(procedure, allDatas, history, dSave);
                } catch (ClassNotFoundException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void procedure(String procedure, DataCenter allDatas, History history, DataSaver dSave)
            throws ClassNotFoundException, InterruptedException {
        String s = allDatas.teleProcedure(procedure.split(":")[1], procedure.split(":")[0], dSave);
        if (s.equals("OK")) {
            history.addColoredLine(procedure + "\n", Color.GREEN);
        } else {
            history.addColoredLine(procedure + "\n", Color.RED);
        }
    }

    public JComboBox<String> getCbox() {
        return this.cbox;
    }

}
