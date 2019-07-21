import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    static void CompararFor1While2(ArrayList<ArrayList<String>> tokensString1fors, ArrayList<ArrayList<String>> tokensString2whiles
    ,ArrayList<ArrayList<Integer>> tokensTypes1fors, ArrayList<ArrayList<Integer>> tokensType2whiles){
        ArrayList<ArrayList> info_for = new ArrayList<>();
        ArrayList<ArrayList> info_while = new ArrayList<>();
        ArrayList<String> temporal_for = new ArrayList<>();
        ArrayList<String> temporal_while = new ArrayList<>();
        for (int i = 0; i < tokensString1fors.size(); i++){
            for(int j = 3; j < 13; j++){
                temporal_for.add(tokensString1fors.get(i).get(j));
            }
            info_for.add(new ArrayList(temporal_for));
            temporal_for.clear();
        }

        for (int i = 0; i < tokensString2whiles.size(); i++){
            temporal_while.add(tokensString2whiles.get(i).get(tokensString2whiles.get(i).size() - 3));
            temporal_while.add(tokensString2whiles.get(i).get(tokensString2whiles.get(i).size() - 2));
            temporal_while.add(tokensString2whiles.get(i).get(tokensString2whiles.get(i).size() - 1));
            temporal_while.add(";");
            temporal_while.add(tokensString2whiles.get(i).get(2));
            temporal_while.add(tokensString2whiles.get(i).get(3));
            temporal_while.add(tokensString2whiles.get(i).get(4));
            temporal_while.add(";");
            for (int j = 6; j < tokensString2whiles.get(i).size(); j++){
                if(tokensString2whiles.get(i).get(j).equals(temporal_while.get(0)) && (tokensString2whiles.get(i).get(j+1).equals("++") || tokensString2whiles.get(i).get(j+1).equals("--"))){
                    temporal_while.add(tokensString2whiles.get(i).get(j));
                    temporal_while.add(tokensString2whiles.get(i).get(j+1));
                    break;
                }
            }
            info_while.add(new ArrayList(temporal_while));
            temporal_while.clear();
        }
        int iguales = 0;
        for(int i = 0; i< info_while.size();i++){
            if(info_for.contains(info_while.get(i))){
                info_for.remove(info_while.get(i));
                iguales++;
            }
        }
        System.out.println("iguales: " + iguales);
    }

    public static void main(String[] args) throws Exception {
        try{
            // crear un analizador léxico que se alimenta a partir de la entrada (archivo  o consola)
            Java8Lexer lexer1;
            Java8Lexer lexer2;
            if (args.length>0){
                lexer1 = new Java8Lexer(CharStreams.fromFileName(args[0]));
                lexer2 = new Java8Lexer(CharStreams.fromFileName(args[1]));
            }
            else{
                lexer1 = new Java8Lexer(CharStreams.fromStream(System.in));
                lexer2 = new Java8Lexer(CharStreams.fromStream(System.in));
            }
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

            if(tokens1.getTokens().size() < tokens2.getTokens().size()){
                CommonTokenStream tokens_t = new CommonTokenStream(lexer1);
                tokens1 = tokens2;
                tokens2 = tokens_t;
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

            for(int i = 0; i < variables1.size(); i++){
                for(int j = posicion1.get(i); j > 0; j--){
                    if (tokensString1.get(j).equals(variables1.get(i))){
                        tokensString1whiles.get(i).add(tokensString1.get(j));
                        tokensString1whiles.get(i).add(tokensString1.get(j + 1));
                        tokensString1whiles.get(i).add(tokensString1.get(j + 2));
                    }
                }

            }

            for(int i = 0; i < variables2.size(); i++){
                for(int j = posicion2.get(i); j > 0; j--){
                    if (tokensString2.get(j).equals(variables2.get(i))){
                        tokensString2whiles.get(i).add(tokensString2.get(j));
                        tokensString2whiles.get(i).add(tokensString2.get(j + 1));
                        tokensString2whiles.get(i).add(tokensString2.get(j + 2));
                    }
                }

            }

//            System.out.println(tokensString1whiles.toString());
//            System.out.println(variables1.toString());
//            System.out.println(tokensString2whiles.toString());
//            System.out.println(variables2.toString());
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
            System.out.println("Cantidad tokens entrada 1: " + size1);
            System.out.println("Cantidad tokens entrada 2: " + size2);
            System.out.println("Tokens iguales: " + iguales_text);
            System.out.println("Tipos de tokens iguales: " + iguales_type);
            System.out.println("Jaccard Tokens:  " + ((float)(iguales_text)/(float)(size1 + size2-iguales_text)));
            System.out.println("Jaccard tipos de tokens:  " + ((float)(iguales_type)/(float)(size1 + size2-iguales_type)));
            CompararFor1While2(tokensString1fors, tokensString2whiles, tokensType1fors, tokensType2whiles);
        } catch (Exception e){
            System.err.println("Error (Test): " + e);
        }

    }


}

