package com.company.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

  private String input;
  private List<Lexeme> output;
  private int cursorPosition;

  public Lexer(String input) {
    this.input = input;
  }

  public List<Lexeme> parse() throws LexerException {
    output = new ArrayList<Lexeme>();
    resetCupsor();
    int position = 0;
    char currentChar;
    while (position < input.length()) {
      currentChar = input.charAt(position);
      switch (currentChar) {
        case ' ':
          position++;
          continue;
        case '+':
          output.add(new Lexeme(Lexeme.Type.PLUS, position));
          break;
        case '-':
          output.add(new Lexeme(Lexeme.Type.MINUS, position));
          break;
        case '*':
          output.add(new Lexeme(Lexeme.Type.MUL, position));
          break;
        case '/':
          output.add(new Lexeme(Lexeme.Type.DIV, position));
          break;
        case '(':
          output.add(new Lexeme(Lexeme.Type.LEFT_BKT, position));
          break;
        case ')':
          output.add(new Lexeme(Lexeme.Type.RIGHT_BKT, position));
          break;
        default:
          if (Character.isDigit(currentChar)) {
            String currentNumber = Character.toString(currentChar);
            boolean separatorAdded = false;
            while ((position + 1 < input.length())) {
              char nextChar = input.charAt(position + 1);
              if (Character.isDigit(nextChar)) {
                position++;
                currentNumber += nextChar;
              } else {
                if (nextChar == '.') {
                  position++;
                  if (separatorAdded) {
                    throwException("Second separator in number", position);
                  } else if (!Character.isDigit(input.charAt(position + 1))) {
                    throwException("Number can not end with dot ", position);
                  } else {
                    separatorAdded = true;
                    currentNumber += nextChar;
                    continue;
                  }
                }
                break;
              }
            }
            output.add(new Lexeme(Lexeme.Type.NUMBER, currentNumber, position));
          } else {
            throwException("Unknown char", position);
          }
      }
      position++;
    }

    if (output.isEmpty()) {
      throwException("Empty input", -1);
    }
    output.add(new Lexeme(Lexeme.Type.END, -1));

    return output;
  }

  private void throwException(String text, int position) throws LexerException {
    output = null;
    throw new LexerException(text, position);
  }

  public void resetCupsor() {
    cursorPosition = 0;
  }

  public void moveToNext() {
    cursorPosition++;
  }

  public Lexeme getCurrent() {
    return output.get(cursorPosition);
  }

}
