
package wiedld.calccompiler;

import java.io.IOException;

/**
 *
 * @author wiedld
 */

public class CalcCompiler {

    public static void main(String[] args) throws NoSuchMethodException, IOException {
        String test = "3 5 + 78 -";
        
        LR0Parser parser = new LR0Parser(test);
        int x = parser.parse();
        System.out.println(x);
        
    }
    
}




