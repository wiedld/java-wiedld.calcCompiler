/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiedld.calccompiler;


/**
 *
 * @author wiedld
 */

public class CalcToken {

    static enum StaticToken {
        EOF,
        ADD,
        SUB,
        MUL,
        DIV,
        MOD
    }

    final static class Static extends CalcToken {
        public final StaticToken tok;

        Static(final StaticToken tok) {
            this.tok = tok;
        }
    }
     
    final static class Num extends CalcToken {
        public final int num;
        
        Num(final int num) {
            this.num = num;
        }
    }


}

