import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

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
                switch (caso) {
                    case "Jaccard":
                        try{
                            // crear un analizador léxico que se alimenta a partir de la entrada (archivo  o consola)
                            Java8Lexer lexer1;
                            Java8Lexer lexer2;
                            lexer1 = new Java8Lexer(CharStreams.fromFileName("input/entrada.txt"));
                            lexer2 = new Java8Lexer(CharStreams.fromFileName("input/entrada2.txt"));
                            // Identificar al analizador léxico como fuente de tokens para el sintactico
                            CommonTokenStream tokens1 = new CommonTokenStream(lexer1);
                            CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
                            // Crear el objeto correspondiente al analizador sintáctico que se alimenta a partir del buffer de tokens
                            Java8Parser parser1 = new Java8Parser(tokens1);
                            Java8Parser parser2 = new Java8Parser(tokens2);
                            ParseTree tree1 = parser1.compilationUnit(); // Iniciar el analisis sintáctico en la regla inicial: compilationUnit
                            ParseTree tree2 = parser2.compilationUnit();
                            ParseTreeWalker walker1 = new ParseTreeWalker();
                            ParseTreeWalker walker2 = new ParseTreeWalker();
                            Plagiarism analisis1 = new Plagiarism(parser1);
                            Plagiarism analisis2 = new Plagiarism(parser2);
                            walker1.walk(analisis1, tree1);
                            walker2.walk(analisis2, tree2);
                            System.out.println("Análisis finalizado");
                            int iguales_type = 0, iguales_text = 0;
//            System.out.println(tree1.toStringTree(parser1)); // imprime el arbol al estilo LISP
//            System.out.println(tree2.toStringTree(parser2)); // imprime el arbol al estilo LISP
                            ArrayList<String> tokensString1 = new ArrayList<>();
                            ArrayList<String> tokensString2 = new ArrayList<>();
                            ArrayList<Integer> tokensType1 = new ArrayList<>();
                            ArrayList<Integer> tokensType2 = new ArrayList<>();
                            for(Object c : tokens1.getTokens()){
                                Token t = (Token) c;
                                tokensString1.add(t.getText());
                                tokensType1.add(t.getType());
                            }
                            for(Object c : tokens2.getTokens()){
                                Token t = (Token) c;
                                tokensString2.add(t.getText());
                                tokensType2.add(t.getType());
                            }
                            Collections.sort(tokensString1);
                            Collections.sort(tokensString2);
                            Collections.sort(tokensType1);
                            Collections.sort(tokensType2);
                            int l = 0, size1 = tokensString1.size(), size2 = tokensString2.size();
                            while(tokensType2.size()>0){
                                if(tokensType2.get(l) == tokensType1.get(l)){
                                    tokensType1.remove(l);
                                    tokensType2.remove(l);
                                    iguales_type++;
                                }else{
                                    if (tokensType1.contains(tokensType2.get(l))){
                                        tokensType1.remove(l);
                                    } else if (tokensType2.contains(tokensType1.get(l))){
                                        tokensType2.remove(l);
                                    }
                                    else {
                                        tokensType1.remove(l);
                                        tokensType2.remove(l);
                                    }
                                }
//                System.out.println("1 "+ tokensString1.toString());
//                System.out.println("2 "+tokensString2.toString());
                            }
//
                            while(tokensString2.size()>0){
                                if(tokensString2.get(l).equals(tokensString1.get(l))){
                                    tokensString1.remove(l);
                                    tokensString2.remove(l);
                                    iguales_text++;
                                }else{
                                    if (tokensString1.contains(tokensString2.get(l))){
                                        tokensString1.remove(l);
                                    } else if (tokensString2.contains(tokensString1.get(l))){
                                        tokensString2.remove(l);
                                    }
                                    else {
                                        tokensString1.remove(l);
                                        tokensString2.remove(l);
                                    }
                                }
                            }
                            String tokens_entrada1 = "Cantidad tokens entrada 1: " + size1;
                            String tokens_entrada2 = "Cantidad tokens entrada 2: " + size2;
                            String tokens_iguales = "Tokens iguales: " + iguales_text;
                            String tokens_tipo_iguales = "Tipos de tokens iguales: " + iguales_type;
                            String tokens_jaccard = "Jaccard Tokens:  " + ((float)(iguales_text)/(float)(size1 + size2-iguales_text));
                            String tokens__tipos_jaccard = "Jaccard tipos de tokens:  " + ((float)(iguales_type)/(float)(size1 + size2-iguales_type));
                            JOptionPane.showMessageDialog(null, tokens_entrada1 + "\n" + tokens_entrada2 + "\n" +
                                    tokens_iguales + "\n" + tokens_tipo_iguales + "\n" + tokens_jaccard + "\n" + tokens__tipos_jaccard,
                                    "Resultado", JOptionPane.DEFAULT_OPTION);
                            System.out.println("Cantidad tokens entrada 1: " + size1);
                            System.out.println("Cantidad tokens entrada 2: " + size2);
                            System.out.println("Tokens iguales: " + iguales_text);
                            System.out.println("Tipos de tokens iguales: " + iguales_type);
                            System.out.println("Jaccard Tokens:  " + ((float)(iguales_text)/(float)(size1 + size2-iguales_text)));
                            System.out.println("Jaccard tipos de tokens:  " + ((float)(iguales_type)/(float)(size1 + size2-iguales_type)));
                        } catch (Exception ex){
                            System.err.println("Error (Test): " + e);
                        }
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

    public static void main(String[] args){
        JFrame frame = new JFrame("Analizador");
        frame.setContentPane(new GUI().Jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
