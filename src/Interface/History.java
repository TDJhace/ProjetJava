package Interface;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Color;

public class History extends JTextPane {
    private static final long serialVersionUID = 1L;

    public History() {
        super();
    }

    // add a line written in color in the JTextPane History
    public void addColoredLine(String line, Color color) {
        StyledDocument doc = this.getStyledDocument();
        Style style = this.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), line, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
