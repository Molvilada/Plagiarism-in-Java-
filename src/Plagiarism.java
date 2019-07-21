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
    ArrayList<String> fors = new ArrayList<>();
    ArrayList<String> whiles = new ArrayList<>();

    public Plagiarism(Java8Parser parser) {
        this.parser = parser;
    }

    @Override
    public void enterBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
        ArrayList<String> childs = new ArrayList<>();
        String [] child = ctx.getText().split("");
        fors.add(ctx.getText());
        System.out.println(ctx.expression().getText());
        System.out.println(ctx.forInit().getText());
        System.out.println(ctx.forUpdate().getText());
        System.out.println(ctx.statement().getText());
        System.out.println(ctx.getPayload().getText());
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
