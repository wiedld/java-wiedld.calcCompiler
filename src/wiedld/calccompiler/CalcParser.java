/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiedld.calccompiler;

//import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

/**
 *
 * @author wiedld
 */

final class CalcParser {
    final StringReader reader;
    final Stack<Integer> stack;
    
    public CalcParser(String text){        
        this.reader = new StringReader(text);
        this.stack = new Stack();
    }

    public final CalcToken readToken() throws IOException {
        int c = readCharNext();
                
        switch(c) {
            case -1:
                return new CalcToken.Static(CalcToken.StaticToken.EOF);
            case ' ':
                return readToken();
            case '+':
                return new CalcToken.Static(CalcToken.StaticToken.ADD);
            case '-':
                return new CalcToken.Static(CalcToken.StaticToken.SUB);
            case '*':
                return new CalcToken.Static(CalcToken.StaticToken.MUL);
            case '/':
                return new CalcToken.Static(CalcToken.StaticToken.DIV);
            case '%':
                return new CalcToken.Static(CalcToken.StaticToken.MOD);
        }
        
        // get the longer nums (23424), into a single string
        StringBuilder buf = new StringBuilder();
        buf.append((char) c);
        for (;;) {
            int d = readCharNext();
            if (d == ' ')    // hit a spaces, end of int
                break;
            buf.append((char) d);
        }
        String text = buf.toString();
        
        final int i = Integer.parseInt(text);
        return new CalcToken.Num(i);
    }
    
    final int readCharNext() throws IOException {
        for (;;) {
            int c = reader.read();
            // register end of text/string/num
            if (Character.isWhitespace(c))
                return ' ';
            return c;
        }
    }
    
    public final int parse() throws IOException {
        final CalcToken tok = readToken();
        
        if (tok instanceof CalcToken.Static) {
            final int term2;
            final int term1;
            switch (((CalcToken.Static) tok).tok) {
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
            }
        } else if (tok instanceof CalcToken.Num) {
            stack.push(((CalcToken.Num) tok).num);
            return parse();
        }
        
        throw new RuntimeException("This should never actually happen.");
    }
}
