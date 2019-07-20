import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    private JButton realizarAnalisisButton;
    private JComboBox comboBox1;
    private JTextArea codigoAAnalizar1TextArea;
    private JTextArea codigoAAnalizar2TextArea;
    private JPanel Jpanel;
    public GUI() {
        realizarAnalisisButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String entrada1 = codigoAAnalizar1TextArea.getText();
                String entrada2 = codigoAAnalizar2TextArea.getText();
                String caso = (String) comboBox1.getSelectedItem();
                System.out.println(entrada1);
                System.out.println(caso);
                System.out.println(entrada2);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analizador");
        frame.setContentPane(new GUI().Jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
