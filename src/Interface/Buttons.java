package Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Datas.DataCenter;
import SatConception.Comps;
import SatConception.Satellite;

public class Buttons {
    private JTabbedPane buttonSat;

    public Buttons(DataCenter allDatas) {
        JTabbedPane tabbedPane = new JTabbedPane();
        ArrayList<Satellite> sats = allDatas.getSats();
        for (Satellite sat : sats) {
            tabbedPane.addTab(sat.getName(), creates(sat, allDatas));
        }
        buttonSat = tabbedPane;
    }

    public JPanel creates(Satellite sat, DataCenter allDatas) {
        ArrayList<Comps> subsystem = sat.getSubsystems();
        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(subsystem.size(), 4);
        panel.setLayout(gridLayout);
        for (Comps sub : subsystem) {
            panel.add(new JLabel(sub.getName()));
            JButton on = new JButton("ON");
            panel.add(on);
            on.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    allDatas.teleOperation(sat.getName(), sub.getName(), "ON");
                }
            });
            JButton off = new JButton("OFF");
            panel.add(off);
            off.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    allDatas.teleOperation(sat.getName(), sub.getName(), "OFF");
                }
            });
            JButton data = new JButton("DATA");
            panel.add(data);
            data.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    allDatas.teleOperation(sat.getName(), sub.getName(), "DATA");
                }
            });
        }
        return panel;
    }

    public JTabbedPane getButtonSat() {
        return this.buttonSat;
    }

}
