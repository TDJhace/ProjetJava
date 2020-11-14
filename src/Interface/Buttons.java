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

    // create the JTabedPane of the Satellite in the Datacenter
    public Buttons(DataCenter allDatas, History history) {
        JTabbedPane tabbedPane = new JTabbedPane();
        ArrayList<Satellite> sats = allDatas.getSats();
        for (Satellite sat : sats) {
            tabbedPane.addTab(sat.getName(), creates(sat, allDatas, history));
        }
        buttonSat = tabbedPane;
    }

    // create a grid of JLabel and JButton containing the subsystems of the
    // Satellite Sat
    public JPanel creates(Satellite sat, DataCenter allDatas, History history) {
        ArrayList<Comps> subsystem = sat.getSubsystems();
        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(subsystem.size(), 4);
        panel.setLayout(gridLayout);
        for (Comps sub : subsystem) {
            JLabel button = new JLabel(sub.getName());
            panel.add(button);
            JButton on = new JButton("ON");
            panel.add(on);
            on.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Listener(e, allDatas, sat, sub, history, "ON", button);
                }
            });
            JButton off = new JButton("OFF");
            panel.add(off);
            off.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Listener(e, allDatas, sat, sub, history, "OFF", button);
                }
            });
            JButton data = new JButton("DATA");
            panel.add(data);
            data.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Listener(e, allDatas, sat, sub, history, "DATA", button);
                }
            });
        }
        return panel;
    }

    // add a coloredline on history when a button is used
    public void Listener(ActionEvent e, DataCenter allDatas, Satellite sat, Comps sub, History history, String s,
            JLabel button) {
        String result = allDatas.teleOperation(sat.getName(), sub.getName(), s);
        if (result.equals("OK")) {
            history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n", Color.GREEN);
            button.setForeground(Color.GREEN);
        } else {
            history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n", Color.RED);
            button.setForeground(Color.RED);
        }
    }

    public JTabbedPane getButtonSat() {
        return this.buttonSat;
    }

}
