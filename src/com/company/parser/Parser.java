package com.company.parser;


import com.company.lexer.Lexeme;
import com.company.lexer.Lexer;
import com.company.lexer.LexerException;

public class Parser {

  private String input;
  private Lexer lexer;

  public Parser(String input) {
    this.input = input;
  }

  public String parse() throws LexerException {
    String result;

    lexer = new Lexer(input);
    lexer.parse();

    result = Double.toString(expression());

    return result;
  }

  private double expression() throws ParserException {
    double result = token();
    while (isSignPlusMinus()) {
      if (lexer.getCurrent().getType() == Lexeme.Type.PLUS) {
        lexer.moveToNext();
        if (lexer.getCurrent().getType() == Lexeme.Type.LEFT_BKT ||
            lexer.getCurrent().getType() == Lexeme.Type.NUMBER) {
          result += token();
        } else {
          throw new ParserException("NUMBER or LEFT_BKT expected", lexer.getCurrent().getPosition());
        }
      }
      if (lexer.getCurrent().getType() == Lexeme.Type.MINUS) {
        lexer.moveToNext();
        if (lexer.getCurrent().getType() == Lexeme.Type.LEFT_BKT ||
            lexer.getCurrent().getType() == Lexeme.Type.NUMBER) {
          result -= token();
        } else {
          throw new ParserException("NUMBER or LEFT_BKT expected", lexer.getCurrent().getPosition());
        }
      }
    }
    return result;
  }

  private boolean isSignPlusMinus() {
    if (lexer.getCurrent().getType() == Lexeme.Type.PLUS ||
        lexer.getCurrent().getType() == Lexeme.Type.MINUS) {
      return true;
    } else {
      return false;
    }
  }

  private double token() throws ParserException {
    if (lexer.getCurrent().getType() == Lexeme.Type.END) {
      throw new ParserException("unexpected END found", -1);
    }
    double result = term();
    while (isSignMulDiv()) {
      if (lexer.getCurrent().getType() == Lexeme.Type.MUL) {
        lexer.moveToNext();
        if (lexer.getCurrent().getType() == Lexeme.Type.LEFT_BKT ||
            lexer.getCurrent().getType() == Lexeme.Type.NUMBER) {
          result *= token();
        } else {
          throw new ParserException("NUMBER or LEFT_BKT expected", lexer.getCurrent().getPosition());
        }
      }
      if (lexer.getCurrent().getType() == Lexeme.Type.DIV) {
        lexer.moveToNext();
        if (lexer.getCurrent().getType() == Lexeme.Type.LEFT_BKT ||
            lexer.getCurrent().getType() == Lexeme.Type.NUMBER) {
          result /= token();
        } else {
          throw new ParserException("NUMBER or LEFT_BKT expected", lexer.getCurrent().getPosition());
        }
      }
    }
    return result;
  }

  private boolean isSignMulDiv() {
    if (lexer.getCurrent().getType() == Lexeme.Type.MUL ||
        lexer.getCurrent().getType() == Lexeme.Type.DIV) {
      return true;
    } else {
      return false;
    }
  }

  private double term() throws ParserException {
    if (lexer.getCurrent().getType() == Lexeme.Type.END) {
      throw new ParserException("unexpected END found", -1);
    }
    double result;
    if (lexer.getCurrent().getType() == Lexeme.Type.NUMBER) {
      result = Double.parseDouble(lexer.getCurrent().getValue());
      lexer.moveToNext();
    } else {
      if (lexer.getCurrent().getType() == Lexeme.Type.LEFT_BKT) {
        lexer.moveToNext();
        result = expression();
        if (lexer.getCurrent().getType() == Lexeme.Type.RIGHT_BKT) {
          lexer.moveToNext();
        } else {
          throw new ParserException("RIGHT_BKT required", lexer.getCurrent().getPosition());
        }
      } else if (lexer.getCurrent().getType() == Lexeme.Type.NUMBER) {
        result = expression();
      } else {
        throw new ParserException("Unexpected char", lexer.getCurrent().getPosition());
      }
    }
    return result;
  }
}
