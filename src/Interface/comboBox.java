package Interface;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class comboBox {
    private JComboBox<String> cbox;

    public comboBox() {
        cbox = new JComboBox<>(new String[] { "Procedures ?", "1", "2" });
        cbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String procedure = (String) cbox.getSelectedItem();
                System.out.println(procedure);
            }
        });
    }

    public JComboBox<String> getCbox() {
        return this.cbox;
    }

}
