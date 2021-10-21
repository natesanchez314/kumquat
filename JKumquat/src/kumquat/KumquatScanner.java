package kumquat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class KumquatScanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();
  private int start = 0;
  private int current = 0;
  private int line = 1;
  private static final Map<String, TokenType> keywords;

  static {
    keywords = new HashMap<>();
    keywords.put("and",    TokenType.AND);
    keywords.put("class",  TokenType.CLASS);
    keywords.put("else",   TokenType.ELSE);
    keywords.put("false",  TokenType.FALSE);
    keywords.put("for",    TokenType.FOR);
    keywords.put("fun",    TokenType.FUN);
    keywords.put("if",     TokenType.IF);
    keywords.put("none",   TokenType.NONE);
    keywords.put("or",     TokenType.OR);
    keywords.put("print",  TokenType.PRINT);
    keywords.put("return", TokenType.RETURN);
    keywords.put("super",  TokenType.SUPER);
    keywords.put("this",   TokenType.THIS);
    keywords.put("true",   TokenType.TRUE);
    keywords.put("var",    TokenType.VAR);
    keywords.put("const",  TokenType.CONST);
    keywords.put("while",  TokenType.WHILE);
    keywords.put("switch", TokenType.SWITCH);
    keywords.put("case",   TokenType.CASE);
  }

  KumquatScanner (String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    while (!isAtEnd()) {
      start = current;
      scanToken();
    }

    tokens.add(new Token(TokenType.EOF, "", null, line));
    return tokens;
  }

  private boolean isAtEnd() {
    return current >= source.length();
  }

  private void scanToken() {
    char c = advance();
    switch (c) {
      // Single character tokens
      case '(' -> addToken(TokenType.LEFT_PAREN);
      case ')' -> addToken(TokenType.RIGHT_PAREN);
      case '{' -> addToken(TokenType.LEFT_BRACE);
      case '}' -> addToken(TokenType.RIGHT_BRACE);
      case ',' -> addToken(TokenType.COMMA);
      case '.' -> addToken(TokenType.DOT);
      case '-' -> addToken(TokenType.MINUS);
      case '+' -> addToken(TokenType.PLUS);
      case ';' -> addToken(TokenType.SEMICOLON);
      case '*' -> addToken(TokenType.STAR);
      // One or two character tokens
      case '!' -> addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
      case '=' -> addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
      case '<' -> addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
      case '>' -> addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
      // Division or comment?
      case '/' -> {
        if (match('/')) {
          while (peek() != '\n' && !isAtEnd()) advance();
        } else {
          addToken(TokenType.SLASH);
        }
      }
      // Ignore these
      case ' ' -> {}
      case '\r' -> {}
      case '\t' -> {}
      case '\n' -> line++;
      // Literals
      case'"' -> handleString();
      default -> {
        if (isDigit(c)) {
          handleNumber();
        } else if (isAlpha(c)) {
          handleIdentifier();
        } else {
          Kumquat.error(line, "Unexpected character.");
        }
      }
    }
  }

  private char advance() {
    return source.charAt(current++);
  }

  private void addToken(TokenType type) {
    addToken(type, null);
  }

  private void addToken(TokenType type, Object literal) {
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }

  private boolean match(char expected) {
    if (isAtEnd()) return false;
    if (source.charAt(current) != expected) return false;
    current ++;
    return true;
  }

  private char peek() {
    if (isAtEnd()) return '\0';
    return source.charAt(current);
  }

  private void handleString() {
    while (peek() != '"' && !isAtEnd()) {
      if (peek() == '\n') line++;
      advance();
    }
    if (isAtEnd()) {
      Kumquat.error(line, "Unterminated string.");
      return;
    }
    // The closing ".
    advance();
    // Trim the surrounding quotes.
    String value = source.substring(start + 1, current - 1);
    addToken(TokenType.STRING, value);
  }

  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  private void handleNumber() {
    while (isDigit(peek())) advance();
    // Look for a fractional part.
    if (peek() == '.' && isDigit(peekNext())) {
      // Consume the "."
      advance();
      while (isDigit(peek())) advance();
    }
    addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
  }

  private char peekNext() {
    if (current + 1 >= source.length()) return '\0';
    return source.charAt(current + 1);
  }

  private void handleIdentifier() {
    while (isAlphaNumeric(peek())) advance();
    String text = source.substring(start, current);
    TokenType type = keywords.get(text);
    if (type == null) type = TokenType.IDENTIFIER;
    addToken(type);
  }

  private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') ||
        (c >= 'A' && c <= 'Z') ||
        c == '_';
  }

  private boolean isAlphaNumeric(char c) {
    return isAlpha(c) || isDigit(c);
  }
}
