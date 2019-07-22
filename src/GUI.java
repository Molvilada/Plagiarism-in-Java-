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

    static void CompararFor1While2(ArrayList<ArrayList<String>> tokensString1fors, ArrayList<ArrayList<String>> tokensString2whiles
            ,ArrayList<ArrayList<Integer>> tokensTypes1fors, ArrayList<ArrayList<Integer>> tokensType2whiles){

        try{
            ArrayList<Float[]> estru_iguales = new ArrayList<>();
            for(int i = 0; i < tokensTypes1fors.size(); i++){
                Collections.sort(tokensTypes1fors.get(i));
            }
            for(int i = 0; i < tokensType2whiles.size(); i++){
                Collections.sort(tokensType2whiles.get(i));
            }
            int iguales_type = 0;
            int l = 0, size1 = tokensTypes1fors.size(), size2 = tokensType2whiles.size();

            for(int i = 0; i < tokensTypes1fors.size();i++){
                for(int j = 0; j < tokensType2whiles.size(); j++){
                    Float[] iguales = new Float[4];
                    ArrayList<Integer> temporal_fors = new ArrayList<>(tokensTypes1fors.get(i));
                    ArrayList<Integer> temporal_whiles = new ArrayList<>(tokensType2whiles.get(j));
                    for(int k = 0; k < temporal_whiles.size(); k++){
                        if(temporal_fors.contains(temporal_whiles.get(k))){
                            temporal_fors.remove(temporal_whiles.get(k));
                            iguales_type++;
                        }
                    }
                    float jaccard = (float)(iguales_type)/(float)(tokensTypes1fors.get(i).size() + tokensType2whiles.get(j).size() - iguales_type);
                    if(jaccard > 0.7){
                        iguales[0] = (float) iguales_type;
                        iguales[1] = (float) jaccard;
                        iguales[2] = (float) i;
                        iguales[3] = (float) j;
                        estru_iguales.add(iguales);
                    }
                    temporal_fors.clear();
                    temporal_whiles.clear();
                    iguales_type = 0;
                }
            }
            FileWriter fichero = new FileWriter("input/for_while.txt");
            PrintWriter pw1 = new PrintWriter(fichero);
            for (int i = 0; i < estru_iguales.size(); i++){
                float for_p = estru_iguales.get(i)[2];
                float while_p =  estru_iguales.get(i)[3];

                pw1.println(tokensString1fors.get((int)for_p));
                pw1.println(tokensString2whiles.get((int)while_p));
                pw1.println("--------------------------------------------------");
            }
            fichero.close();
            JOptionPane.showMessageDialog(null, "Las estructuras se encuentran en el archivo input/for_while",
                    "Aviso", JOptionPane.DEFAULT_OPTION);
        }catch (Exception ex){
            System.err.println("Error (Test): " + ex);
        }




    }

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
                try{
                    Java8Lexer lexer1;
                    Java8Lexer lexer2;
                    lexer1 = new Java8Lexer(CharStreams.fromFileName("input/entrada.txt"));
                    lexer2 = new Java8Lexer(CharStreams.fromFileName("input/entrada2.txt"));

                    // Identificar al analizador léxico como fuente de tokens para el sintactico
                    CommonTokenStream tokens1;
                    CommonTokenStream tokens2;
                    CommonTokenStream tokens_t1 =  new CommonTokenStream(lexer1);
                    CommonTokenStream tokens_t2 =  new CommonTokenStream(lexer2);

                    // Crear el objeto correspondiente al analizador sintáctico que se alimenta a partir del buffer de tokens
                    Java8Parser parser1 = new Java8Parser(tokens_t1);
                    Java8Parser parser2 = new Java8Parser(tokens_t2);
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
                    ArrayList<String> tokensString1 = new ArrayList<>();
                    ArrayList<ArrayList<String>> tokensString1fors = new ArrayList<>();
                    ArrayList<ArrayList<Integer>> tokensType1fors = new ArrayList<>();
                    ArrayList<ArrayList<String>> tokensString1whiles = new ArrayList<>();
                    ArrayList<ArrayList<Integer>> tokensType1whiles = new ArrayList<>();
                    ArrayList<String> tokensString2 = new ArrayList<>();
                    ArrayList<ArrayList<String>> tokensString2fors = new ArrayList<>();
                    ArrayList<ArrayList<Integer>> tokensType2fors = new ArrayList<>();
                    ArrayList<ArrayList<String>> tokensString2whiles = new ArrayList<>();
                    ArrayList<ArrayList<Integer>> tokensType2whiles = new ArrayList<>();
                    ArrayList<Integer> tokensType1 = new ArrayList<>();
                    ArrayList<Integer> tokensType2 = new ArrayList<>();
                    ArrayList<String> temporal = new ArrayList<>();
                    ArrayList<Integer> temporal2 = new ArrayList<>();

                    int k = 0;
                    int bandera_for = 0, llaves = 0, bandera_while = 0, bandera_variable = 0;
                    ArrayList<String> variables1 = new ArrayList<>();
                    ArrayList<String> variables2 = new ArrayList<>();
                    ArrayList<Integer> posicion1 = new ArrayList<>();
                    ArrayList<Integer> posicion2 = new ArrayList<>();

                    if(tokens_t1.getTokens().size() < tokens_t2.getTokens().size()){
                        tokens1 = tokens_t2;
                        tokens2 = tokens_t1;
                    }else{
                        tokens1 = tokens_t1;
                        tokens2 = tokens_t2;
                    }


                    for(Object c : tokens1.getTokens()){
                        Token t = (Token) c;
                        tokensString1.add(t.getText());
                        tokensType1.add(t.getType());
                        if (t.getText().equals("for")){
                            bandera_for = 1;
                        }
                        if (t.getText().equals("while")){
                            bandera_while = 1;
                            bandera_variable = 0;
                            posicion1.add(t.getTokenIndex());
                        }
                        if (bandera_for > 0){
                            temporal.add(t.getText());
                            temporal2.add(t.getType());
                            if (t.getText().equals("{")){
                                llaves++;
                            }
                            if (t.getText().equals("}")){
                                llaves--;
                                if(llaves == 0){
                                    tokensString1fors.add(new ArrayList<>(temporal));
                                    tokensType1fors.add(new ArrayList<>(temporal2));
                                    temporal.clear();
                                    temporal2.clear();
                                    bandera_for = 0;
                                }

                            }
                        }
                        if (bandera_while > 0){
                            if(t.getType() == 102 && bandera_variable == 0){
                                variables1.add(t.getText());
                                bandera_variable++;
                            }
                            temporal.add(t.getText());
                            temporal2.add(t.getType());
                            if (t.getText().equals("{")){
                                llaves++;
                            }
                            if (t.getText().equals("}")){
                                llaves--;
                                if(llaves == 0){
                                    tokensString1whiles.add(new ArrayList<>(temporal));
                                    tokensType1whiles.add(new ArrayList<>(temporal2));
                                    temporal.clear();
                                    temporal2.clear();
                                    bandera_while = 0;
                                }

                            }
                        }
                    }

                    bandera_for = 0; llaves = 0; bandera_while = 0; bandera_variable = 0;

                    for(Object c : tokens2.getTokens()){
                        Token t = (Token) c;
                        tokensString2.add(t.getText());
                        tokensType2.add(t.getType());
                        if (t.getText().equals("for")){
                            bandera_for = 1;
                        }
                        if (t.getText().equals("while")){
                            bandera_while = 1;
                            bandera_variable = 0;
                            posicion2.add(t.getTokenIndex());
                        }
                        if (bandera_for > 0){
                            temporal.add(t.getText());
                            temporal2.add(t.getType());
                            if (t.getText().equals("{")){
                                llaves++;
                            }
                            if (t.getText().equals("}")){
                                llaves--;
                                if(llaves == 0){
                                    tokensString2fors.add(new ArrayList<>(temporal));
                                    tokensType2fors.add(new ArrayList<>(temporal2));
                                    temporal.clear();
                                    temporal2.clear();
                                    bandera_for = 0;
                                }

                            }
                        }
                        if (bandera_while > 0){
                            if(t.getType() == 102 && bandera_variable == 0){
                                variables2.add(t.getText());
                                bandera_variable++;
                            }
                            temporal.add(t.getText());
                            temporal2.add(t.getType());
                            if (t.getText().equals("{")){
                                llaves++;
                            }
                            if (t.getText().equals("}")){
                                llaves--;
                                if(llaves == 0){
                                    tokensString2whiles.add(new ArrayList<>(temporal));
                                    tokensType2whiles.add(new ArrayList<>(temporal2));
                                    temporal.clear();
                                    temporal2.clear();
                                    bandera_while = 0;
                                }

                            }
                        }
                    }

                    System.out.println(tokensString1.toString());
                    System.out.println(tokensString2.toString());

                    for(int i = 0; i < variables1.size(); i++){
                        for(int j = posicion1.get(i); j > 0; j--){
                            if (tokensString1.get(j).equals(variables1.get(i))){
                                tokensString1whiles.get(i).add(tokensString1.get(j));
                                tokensString1whiles.get(i).add(tokensString1.get(j + 1));
                                tokensString1whiles.get(i).add(tokensString1.get(j + 2));
                                tokensType1whiles.get(i).add(tokensType1.get(j));
                                tokensType1whiles.get(i).add(tokensType1.get(j + 1));
                                tokensType1whiles.get(i).add(tokensType1.get(j + 2));
                            }
                        }

                    }

                    for(int i = 0; i < variables2.size(); i++){
                        for(int j = posicion2.get(i); j > 0; j--){
                            if (tokensString2.get(j).equals(variables2.get(i))){
                                tokensString2whiles.get(i).add(tokensString2.get(j));
                                tokensString2whiles.get(i).add(tokensString2.get(j + 1));
                                tokensString2whiles.get(i).add(tokensString2.get(j + 2));
                                tokensType2whiles.get(i).add(tokensType2.get(j));
                                tokensType2whiles.get(i).add(tokensType2.get(j + 1));
                                tokensType2whiles.get(i).add(tokensType2.get(j + 2));
                            }
                        }

                    }

                    Collections.sort(tokensString1);
                    Collections.sort(tokensString2);
                    Collections.sort(tokensType1);
                    Collections.sort(tokensType2);
                    int l = 0, size1 = tokensString1.size(), size2 = tokensString2.size();

                switch (caso) {
                    case "Jaccard":
                            // crear un analizador léxico que se alimenta a partir de la entrada (archivo  o consola)
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

                            }
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
                            break;
                    case "Estructura":
                        CompararFor1While2(tokensString1fors, tokensString2whiles, tokensType1fors, tokensType2whiles);
                        CompararFor1While2(tokensString2fors, tokensString1whiles, tokensType2fors, tokensType1whiles);
                        break;
                    case "Hibrido":
                        break;
                        default:
                            System.out.println("Entrada no valida");
                }} catch (Exception ex){
                    System.err.println("Error (Test): " + e);
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
