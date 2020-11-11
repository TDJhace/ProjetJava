package Interface;

import java.awt.Color;
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

    public Buttons(DataCenter allDatas, History history) {
        JTabbedPane tabbedPane = new JTabbedPane();
        ArrayList<Satellite> sats = allDatas.getSats();
        for (Satellite sat : sats) {
            tabbedPane.addTab(sat.getName(), creates(sat, allDatas, history));
        }
        buttonSat = tabbedPane;
    }

    public JPanel creates(Satellite sat, DataCenter allDatas, History history) {
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
                    onListener(e, allDatas, sat, sub, history, "ON");
                }
            });
            JButton off = new JButton("OFF");
            panel.add(off);
            off.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onListener(e, allDatas, sat, sub, history, "OFF");
                }
            });
            JButton data = new JButton("DATA");
            panel.add(data);
            data.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onListener(e, allDatas, sat, sub, history, "DATA");
                }
            });
        }
        return panel;
    }

    public void onListener(ActionEvent e, DataCenter allDatas, Satellite sat, Comps sub, History history, String s) {
        String result = allDatas.teleOperation(sat.getName(), sub.getName(), s);
        if (result.equals("OK")) {
            history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n", Color.GREEN);
        } else {
            history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n", Color.RED);
        }
    }

    public JTabbedPane getButtonSat() {
        return this.buttonSat;
    }

}
