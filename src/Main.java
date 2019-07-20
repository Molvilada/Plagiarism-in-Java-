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
            System.out.println("Cantidad tokens entrada 1: " + size1);
            System.out.println("Cantidad tokens entrada 2: " + size2);
            System.out.println("Tokens iguales: " + iguales_text);
            System.out.println("Tipos de tokens iguales: " + iguales_type);
            System.out.println("Jaccard Tokens:  " + ((float)(iguales_text)/(float)(size1 + size2-iguales_text)));
            System.out.println("Jaccard tipos de tokens:  " + ((float)(iguales_type)/(float)(size1 + size2-iguales_type)));
        } catch (Exception e){
            System.err.println("Error (Test): " + e);
        }
    }
}

