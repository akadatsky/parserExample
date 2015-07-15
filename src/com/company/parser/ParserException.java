package com.company.parser;

import com.company.lexer.LexerException;

public class ParserException extends LexerException {

  public ParserException(String detailMessage, int position) {
    super(detailMessage, position);
  }
}
