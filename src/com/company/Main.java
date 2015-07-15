package com.company;

import com.company.lexer.LexerException;
import com.company.parser.Parser;

public class Main {

    public static final String input = "(((2+3)*(4+1)))";

    public static void main(String[] args) {
        Parser parser = new Parser(input);
        String result = null;
        try {
            result = parser.parse();
        } catch (LexerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("result: " +  result);
    }
}
