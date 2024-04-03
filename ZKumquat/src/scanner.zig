const TokenType = enum {
    // Single character tokens
    leftParen, rightParen,
    leftBrace, rightBrace,
    comma, dot, minus, plus,
    colon, semicolon, slash, star,

    // One or two character tokens
    bang, bang_equal,
    equal, equal_equal,
    greater, greater_equal,
    less, less_equal,

    // Literals
    identifier, string, number,

    // Keywords
    t_and, t_or, object, interface,
    t_if, t_else, fun, t_for, t_true,
    t_false, nil, print, t_return, super,
    this, t_const, t_while,

    t_error, eof
};

const Token = struct {
    token_type: TokenType,
    start: *u8,
    length: isize,
    line: isize,
};

const Scanner = struct {
    start: *u8,
    current: *u8,
    line: isize,
};

const scanner = Scanner;

fn initScanner(source: []const u8) !void {
    scanner = Scanner {
        .start = source[0],
        .current = source[0],
        .line = 1,
    };
}

fn isAlpha(c: u8) bool {
    return c >= 'a' and c <= 'z'
        or c >= 'A' and c <= 'Z'
        or c == '_';
}

fn isDigit(c: u8) bool {
    return c >= '0' and c <= '9';
}

fn isAtEnd() bool {
    return *scanner.current == '0';
}

fn advance() u8 {
    scanner.current += 1;
    return scanner.current[-1];
}

fn peek() u8 {
    return *scanner.current;
}

fn peekNext() u8 {
    if (isAtEnd()) return '0';
    return scanner.current[1];
}

fn match(expected: u8) bool {
    if (isAtEnd()) return false;
    if (*scanner.current != expected) return false;
    scanner.current += 1;
    return true;
}

//fn makeToken(token_type: TokenType) Token {

//}