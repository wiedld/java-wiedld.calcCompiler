
package wiedld.calccompiler;

import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

/**
 *
 * @author wiedld
 */
final class LR0Parser {

    final StringReader reader;
    final Stack<Integer> stack;
    final Stack<CalcToken> ast;
    private int pos;

    public LR0Parser(String text) {
        this.reader = new StringReader(text);
        this.stack = new Stack();
        this.ast = new Stack();
    }

    public final CalcToken readToken() throws IOException {
        int c = readCharNext();

        switch (c) {
            case -1:
                pos++;
                return new CalcToken.Op(CalcToken.Type.EOF, pos);
            case ' ':
                return readToken();
            case '+':
                pos++;
                return new CalcToken.Op(CalcToken.Type.ADD, pos);
            case '-':
                pos++;
                return new CalcToken.Op(CalcToken.Type.SUB, pos);
            case '*':
                pos++;
                return new CalcToken.Op(CalcToken.Type.MUL, pos);
            case '/':
                pos++;
                return new CalcToken.Op(CalcToken.Type.DIV, pos);
            case '%':
                pos++;
                return new CalcToken.Op(CalcToken.Type.MOD, pos);
        }

        // get the longer nums (23424), into a single string
        StringBuilder buf = new StringBuilder();
        buf.append((char) c);
        for (;;) {
            int d = readCharNext();
            if (d == ' ') // hit a spaces, end of int
            {
                break;
            }
            buf.append((char) d);
        }
        String text = buf.toString();

        final int i = Integer.parseInt(text);
        pos++;
        return new CalcToken.Num(i, pos);
    }

    final int readCharNext() throws IOException {
        for (;;) {
            int c = reader.read();
            // register end of text/string/num
            if (Character.isWhitespace(c)) {
                return ' ';
            }
            return c;
        }
    }

    public final int parse() throws IOException {
        final CalcToken tok = readToken();

        ast.push(tok);

        final int term2;
        final int term1;
        switch (tok.getType()) {
            case EOF:
                final int soln = stack.pop();
                return soln;
            case ADD:
                term2 = stack.pop();
                term1 = stack.pop();
                stack.push(term1 + term2);
                return parse();
            case SUB:
                term2 = stack.pop();
                term1 = stack.pop();
                stack.push(term1 - term2);
                return parse();
            case MUL:
                term2 = stack.pop();
                term1 = stack.pop();
                stack.push(term1 * term2);
                return parse();
            case DIV:
                term2 = stack.pop();
                term1 = stack.pop();
                stack.push(term1 / term2);
                return parse();
            case MOD:
                term2 = stack.pop();
                term1 = stack.pop();
                stack.push(term1 % term2);
                return parse();
            case NUM:
                stack.push(((CalcToken.Num) tok).num);
                return parse();
        }

        throw new RuntimeException("This should never actually happen.");
    }

}
