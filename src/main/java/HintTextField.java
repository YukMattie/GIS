import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

/**
 * HintTextField is a JTextField component that displays a hint when no text is entered and the
 * field does not have focus. When the field gains focus, the hint is removed and the text color
 * and font are changed to the default values.
 * @author Yuting Pan
 * @version 1.0
 */
public class HintTextField extends JTextField {

    private Font gainFont = new Font("Tahoma", Font.PLAIN, 11);
    private Font lostFont = new Font("Tahoma", Font.ITALIC, 11);
    private String hint;


    /**
     * Constructs a new HintTextField object with the specified hint text.
     *
     * @param hint The hint text to display when the field is empty and does not have focus.
     */

    public HintTextField(final String hint) {

        this.hint = hint;
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                setFont(gainFont);
                setForeground(Color.BLACK);
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                } else {
                    setText(getText());
                    setFont(gainFont);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint)|| getText().length()==0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });

    }


}
