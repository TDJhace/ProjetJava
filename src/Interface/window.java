package Interface;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Datas.DataCenter;

public class window extends JFrame {
    private static final long serialVersionUID = 1L;

    // create a window with an instance of button, and an instance of history
    public window(DataCenter allDatas) {
        super("Control Center");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel) this.getContentPane();
        History history = new History();
        Buttons buttons = new Buttons(allDatas, history);
        JScrollPane scroll = new JScrollPane(history);
        JLabel label = new JLabel("<html><body><b><u>T/C History</u></b></body></html>");
        scroll.setColumnHeaderView(label);
        contentPane.add(buttons.getButtonSat(), BorderLayout.NORTH);
        contentPane.add(scroll, BorderLayout.CENTER);
    }
}