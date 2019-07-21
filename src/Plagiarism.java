import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.concurrent.locks.Condition;

public class Plagiarism extends Java8BaseListener {

    Java8Parser parser;
    ArrayList<String[]> fors = new ArrayList<>();
    ArrayList<String> whiles = new ArrayList<>();

    public Plagiarism(Java8Parser parser) {
        this.parser = parser;
    }

    @Override
    public void enterBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
//        ArrayList<String> childs = new ArrayList<>();
//        String [] child = new String[3];
//        child[0] = ctx.expression().getText();
//        child[1] = ctx.forInit().getText();
//        child[2] = ctx.forUpdate().getText();
//        fors.add(child);
//        for (int i = 0; i < fors.size(); i++){
//            for (int j = 0; j < 3; j++){
//                System.out.println(fors.get(i)[j]);
//            }
//        }
    }

    @Override
    public void exitBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
    }

    @Override
    public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
        whiles.add(ctx.getText());
    }

    @Override
    public void enterDoStatement(Java8Parser.DoStatementContext ctx) {
        super.enterDoStatement(ctx);
    }

    @Override
    public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
        super.enterIfThenStatement(ctx);
    }

    @Override
    public void enterIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
        super.enterIfThenElseStatement(ctx);
    }

    @Override
    public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
        super.enterSwitchStatement(ctx);
    }
}
