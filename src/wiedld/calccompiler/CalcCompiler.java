/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiedld.calccompiler;

import java.io.IOException;

/**
 *
 * @author wiedld
 */

public class CalcCompiler {

    public static void main(String[] args) throws NoSuchMethodException, IOException {
        String test = "3 5 + 78 -";
        
        CalcParser parser = new CalcParser(test);
        int x = parser.parse();
        System.out.println(x);
        System.out.println("working");

    }
    
}




