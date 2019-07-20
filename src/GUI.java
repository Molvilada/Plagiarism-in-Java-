import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    private JButton realizarAnálisisButton;
    private JComboBox comboBox1;
    private JTextArea codigoAAnalizar1TextArea;
    private JTextArea codigoAAnalizar2TextArea;
    private JButton button1;

    public GUI() {
        realizarAnálisisButton.addMouseListener(new MouseAdapter() {
        });
        realizarAnálisisButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String entrada1 = codigoAAnalizar1TextArea.getText();
                String entrada2 = codigoAAnalizar2TextArea.getText();
                System.out.println(entrada1);
                System.out.println(entrada2);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analizador");
        frame.setContentPane(new GUI().comboBox1);
    }
}
