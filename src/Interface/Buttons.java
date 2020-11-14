package Interface;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Datas.Data;
import Datas.DataCenter;
import Datas.DataSaver;
import SatConception.Comps;
import SatConception.Satellite;

public class Buttons {
    private JTabbedPane buttonSat;

    // create the JTabedPane of the Satellite in the Datacenter
    public Buttons(DataCenter allDatas, History history, DataSaver dSave) {
        JTabbedPane tabbedPane = new JTabbedPane();
        ArrayList<Satellite> sats = allDatas.getSats();
        for (Satellite sat : sats) {
            tabbedPane.addTab(sat.getName(), creates(sat, allDatas, history, dSave));
        }
        buttonSat = tabbedPane;
    }

    // create a grid of JLabel and JButton containing the subsystems of the
    // Satellite Sat
    public JPanel creates(Satellite sat, DataCenter allDatas, History history, DataSaver dSave) {
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
                    try {
                        Listener2(e, allDatas, sat, sub, history, button, dSave);
                    } catch (ClassNotFoundException e1) {

                        e1.printStackTrace();
                    } catch (IOException e1) {

                        e1.printStackTrace();
                    }
                }
            });
        }
        return panel;
    }

    // add a coloredline on history when a button is used
    public void Listener(ActionEvent f, DataCenter allDatas, Satellite sat, Comps sub, History history, String s,
            JLabel button) {

        // try {

        // String result = allDatas.teleOperation(sat.getName(), sub.getName(), s);
        // if (result.equals("OK")) {
        // history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n",
        // Color.GREEN);
        // button.setForeground(Color.GREEN);
        // } else {
        // history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n",
        // Color.RED);
        // button.setForeground(Color.RED);
        // }

        // } catch (NoSuchElementException e) {
        // System.out.println("Please write a correct command.");
        // } catch (IOException e) {
        // System.out.println("An error occured with the CHANNEL files. The program
        // exits automatically.");
        // e.printStackTrace();
        // System.out.println(e.getLocalizedMessage());
        // System.exit(0);
        // } catch (InterruptedException e) {
        // System.out.println("A fatal error occured. The program exits
        // automatically.");
        // System.exit(0);
        // } catch (ClassNotFoundException e) {
        // System.err.println("Unknown error.");
        // }

    }

    public void Listener2(ActionEvent f, DataCenter allDatas, Satellite sat, Comps sub, History history, JLabel button,
            DataSaver dSave) throws ClassNotFoundException, IOException {
        String s = "DATA";

        // try {
        // String result = allDatas.teleOperation(sat.getName(), sub.getName(), s);
        // if (result.equals("OK")) {

        // int seq = dSave.getSeq(sat.getName());
        // ArrayList<Data> ldata = allDatas.getDatas();
        // dSave.saveData(sat.getName(), seq, ldata.get(ldata.size() - 1));
        // dSave.updateSeq(sat.getName());
        // history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n",
        // Color.GREEN);
        // button.setForeground(Color.GREEN);
        // } else {
        // history.addColoredLine(sat.getName() + ":" + sub.getName() + ":" + s + "\n",
        // Color.RED);
        // button.setForeground(Color.RED);
        // }

        // } catch (NoSuchElementException e) {
        // System.out.println("Please write a correct command.");
        // } catch (IOException e) {
        // System.out.println("An error occured with the CHANNEL files. The program
        // exits automatically.");
        // e.printStackTrace();
        // System.out.println(e.getLocalizedMessage());
        // System.exit(0);
        // } catch (InterruptedException e) {
        // System.out.println("A fatal error occured. The program exits
        // automatically.");
        // System.exit(0);
        // } catch (ClassNotFoundException e) {
        // System.err.println("Unknown error.");
        // }
    }

    public JTabbedPane getButtonSat() {
        return this.buttonSat;
    }

}
