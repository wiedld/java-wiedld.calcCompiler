
package wiedld.calccompiler;

/**
 *
 * @author wiedld
 */
public abstract class CalcToken {

    int pos;
    int sublevel_up;

    static enum Type {

        EOF,
        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        NUM
    }

    public final Type type;

    CalcToken(Type type, final int pos) {
        this.type = type;
        this.pos = pos;
        this.sublevel_up = findSublevel();
    }

    public Type getType() {
        return type;
    }

    private int findSublevel() {
//            CalcToken..previous = LR0Parser.stack.peek();
        return 1; //To change body of generated methods, choose Tools | Templates.
    }

   
    public static class Op extends CalcToken {

        public Op(Type type, int pos) {
            super(type, pos);
        }
    }
    
    final static class Num extends CalcToken {

        public final int num;

        Num(int num, int pos) {
            super(Type.NUM, pos);
            this.num = num;
            this.pos = pos;
        }
    }

}
