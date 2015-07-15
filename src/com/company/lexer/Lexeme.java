package com.company.lexer;

public class Lexeme {

  Type type;
  String value;
  int position;

  public Lexeme(Type type, int position) {
    this.type = type;
    this.position = position;
  }

  public Lexeme(Type type, String value, int position) {
    this.type = type;
    this.value = value;
    this.position = position;
  }

  @Override
  public String toString() {
    if (value != null) {
      return "Lexeme{" +
          "type=" + type +
          ", value='" + value + '\'' +
          '}';
    } else {
      return "Lexeme{" +
          "type=" + type +
          '}';
    }
  }

  public Type getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public int getPosition() {
    return position;
  }

  public enum Type {PLUS, MINUS, MUL, DIV, LEFT_BKT, RIGHT_BKT, END, NUMBER}

}


