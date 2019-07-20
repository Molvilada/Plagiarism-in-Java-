import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap; // import the HashMap class
import java.util.HashSet;
import java.util.Collections;

public class Main {
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
            Plagiarism analizis1 = new Plagiarism();
            Plagiarism analizis2 = new Plagiarism();
            walker1.walk(analizis1, tree1);
            walker2.walk(analizis2, tree2);
            System.out.println("Análisis finalizado");
            int iguales = 0, diferentes = 0;
//            System.out.println(tree1.toStringTree(parser1)); // imprime el arbol al estilo LISP
//            System.out.println(tree2.toStringTree(parser2)); // imprime el arbol al estilo LISP
            ArrayList<String> tokensString1 = new ArrayList<>();
            ArrayList<String> tokensString2 = new ArrayList<>();
            for(Object c : tokens1.getTokens()){
                Token t = (Token) c;
                tokensString1.add(t.getText());
            }
            for(Object c : tokens2.getTokens()){
                Token t = (Token) c;
                tokensString2.add(t.getText());
            }
//            HashMap<String, String> tokensIguales = new HashMap<String, String>();
//            for(int i = 0; i < tokensString2.size(); i++){
////                j = i;
////                while(j<tokensString1.size()){
////                    if(tokensString1.get(j).equals(tokensString2.get(i))){
//////                        if (tokensIguales.get(tokensString2.get(i)) == null) {
//////                            tokensIguales.put(tokensString2.get(i), tokensString2.get(i));
//////                        }
////                        iguales++;
////                        j=tokensString1.size();
////                    }else{
////                        j++;
////                    }
////                }
//            }
//            HashSet<String> hs = new HashSet<>();
//            hs.addAll(tokensString2);
//            tokensString2.clear();
//            tokensString2.addAll(hs);
            Collections.sort(tokensString1);
            Collections.sort(tokensString2);
            System.out.println(tokensString1.toString());
            System.out.println(tokensString2.toString());
            int l = 0, size1 = tokensString1.size(), size2 = tokensString2.size();
            while(tokensString2.size()>0){
                if(tokensString2.get(l).equals(tokensString1.get(l))){
                    tokensString1.remove(l);
                    tokensString2.remove(l);
                    iguales++;
                }else{
                    tokensString1.remove(l);
                }
            }
//            for (String i : tokensIguales.keySet()) {
//                System.out.println(i);
//            }
            System.out.println("iguales: " + iguales);
            System.out.println("size1: " + tokensString1.size());
            System.out.println("size2: " + tokensString2.size());
            System.out.println("Jaccard:  " + ((float)(iguales)/(float)(size1 + size2-iguales)));
        } catch (Exception e){
            System.err.println("Error (Test): " + e);
        }
    }
}

