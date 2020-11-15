package Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Datas.DataCenter;
import Datas.DataSaver;

public class window extends JFrame {
    private static final long serialVersionUID = 1L;

    // create a window with an instance of button, and an instance of history
    public window(DataCenter allDatas, DataSaver dsave) {
        super("Control Center");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 400);
        this.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel) this.getContentPane();
        History history = new History();
        Buttons buttons = new Buttons(allDatas, history, dsave);
        JScrollPane scroll = new JScrollPane(history);
        JLabel label = new JLabel("<html><body><b><u>T/C History</u></b></body></html>");
        scroll.setColumnHeaderView(label);
        comboBox comboBox = new comboBox(allDatas, history);
        contentPane.add(comboBox.getCbox(), BorderLayout.NORTH);
        contentPane.add(buttons.getButtonSat(), BorderLayout.CENTER);
        scroll.setPreferredSize(new Dimension(0, 200));
        contentPane.add(scroll, BorderLayout.SOUTH);
    }
}