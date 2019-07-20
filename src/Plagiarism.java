import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.concurrent.locks.Condition;

public class Plagiarism extends Java8BaseListener {
    ArrayList<String> Tokens = new ArrayList<>();

    @Override
    public void enterCompilationUnit(Java8Parser.CompilationUnitContext ctx) {

    }
}
