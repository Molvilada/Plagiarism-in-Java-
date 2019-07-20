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
    ArrayList<String> Trees = new ArrayList<>();

    @Override
    public void enterBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
        super.enterBasicForStatement(ctx);
    }

    @Override
    public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
        super.enterWhileStatement(ctx);
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
