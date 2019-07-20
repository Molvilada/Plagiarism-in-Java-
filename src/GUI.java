import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

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
                FileWriter fichero1 = null;
                PrintWriter pw1 = null;
                FileWriter fichero2 = null;
                PrintWriter pw2 = null;
                String caso = (String) comboBox1.getSelectedItem();
                try{
                    fichero1 = new FileWriter("input/entrada.txt");
                    pw1 = new PrintWriter(fichero1);
                    fichero2 = new FileWriter("input/entrada2.txt");
                    pw2 = new PrintWriter(fichero2);
                    String entrada1 = codigoAAnalizar1TextArea.getText();
                    String entrada2 = codigoAAnalizar2TextArea.getText();
                    pw1.println(entrada1);
                    pw2.println(entrada2);
                } catch (Exception a) {
                    a.printStackTrace();
                } finally {
                    try {
                        // Nuevamente aprovechamos el finally para
                        // asegurarnos que se cierra el fichero.
                        if (null != fichero1)
                            fichero1.close();
                            fichero2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                switch (caso){
                    case "Jaccard":
                        break;
                    case "Estructura":
                        break;
                    case "Hibrido":
                        break;
                        default:
                            System.out.println("Entrada no valida");
                }
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
