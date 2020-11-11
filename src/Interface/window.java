package Interface;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import Datas.DataCenter;

public class window extends JFrame {
    private static final long serialVersionUID = 1L;

    public window(DataCenter allDatas) {
        super("Control Center");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel) this.getContentPane();
        Buttons buttons = new Buttons(allDatas);
        contentPane.add(buttons.getButtonSat(), BorderLayout.NORTH);
        // JPanel panel = new JPanel();
        // panel.add(new JLabel("T/C History"), BorderLayout.NORTH);
        // panel.add(scroll());
        contentPane.add(scroll(), BorderLayout.CENTER);
    }

    // public JTabbedPane creates(ArrayList<Satellite> sats) {
    // JTabbedPane tabbedPane = new JTabbedPane();
    // for (Satellite sat : sats) {
    // tabbedPane.addTab(sat.getName(), creates(sat));
    // }
    // return tabbedPane;
    // }

    // public JPanel creates(Satellite sat) {
    // ArrayList<Comps> subsystem = sat.getSubsystems();
    // JPanel panel = new JPanel();
    // // JPanel left = new JPanel();
    // // JPanel right = new JPanel();
    // // BoxLayout boxLayout = new BoxLayout(left, BoxLayout.Y_AXIS);
    // // left.setLayout(boxLayout);
    // GridLayout gridLayout = new GridLayout(subsystem.size(), 4);
    // panel.setLayout(gridLayout);
    // for (Comps sub : subsystem) {
    // panel.add(new JLabel(sub.getName()));
    // panel.add(new JButton("ON"));
    // panel.add(new JButton("OFF"));
    // panel.add(new JButton("DATA"));
    // }
    // // panel.add(left, BorderLayout.WEST);
    // // panel.add(right, BorderLayout.EAST);
    // // panel.add(new JLabel("T/C History"), BorderLayout.SOUTH);
    // return panel;
    // }

    public JScrollPane scroll() {
        // JPanel panel = new JPanel();
        // for (int k = 0; k < 10; k++) {
        // JLabel jta = new JLabel("coucou" + k);
        // panel.add(jta);
        // }
        // JScrollPane scrollpane = new JScrollPane(panel);
        JTextPane jtp = new JTextPane();
        JLabel label = new JLabel("wesh");
        jtp.add(label);
        JScrollPane scrollpane = new JScrollPane(jtp);
        return scrollpane;
    }
}