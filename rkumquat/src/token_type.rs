pub enum TokenType {
    // Single char tokens
    LeftParen, RightParen, LeftBrace, RightBrace,
    Comma, Dot, Minus, Plus, Semicolon, Slash, Star,

    // One or two char tokens
    Bang, BangEqual, Equal, EqualEqual,
    Greater, GreaterEqual, Less, LessEqual,

    // Literals
    Identifier, String, Number,

    // Keywords
    And, Class, Else, False, Fun, For, If, Nil, Or,
    Print, Return, Super, This, True, Var, While,

    Eof,
}

impl ToString for TokenType {
    fn to_string(&self) -> String {
        match self {
            TokenType::LeftParen => "(".to_string(), 
            TokenType::RightParen => ")".to_string(), 
            TokenType::LeftBrace => "{".to_string(), 
            TokenType::RightBrace => "}".to_string(),
            TokenType::Comma => ",".to_string(), 
            TokenType::Dot => ".".to_string(), 
            TokenType::Minus => "-".to_string(), 
            TokenType::Plus => "+".to_string(), 
            TokenType::Semicolon => ";".to_string(), 
            TokenType::Slash => "/".to_string(), 
            TokenType::Star => "*".to_string(),
            TokenType::Bang => "!".to_string(), 
            TokenType::BangEqual => "!=".to_string(), 
            TokenType::Equal => "=".to_string(), 
            TokenType::EqualEqual => "==".to_string(),
            TokenType::Greater => ">".to_string(), 
            TokenType::GreaterEqual => ">=".to_string(), 
            TokenType::Less => "<".to_string(), 
            TokenType::LessEqual => "<=".to_string(),
            TokenType::Identifier => "id".to_string(), 
            TokenType::String => "str".to_string(), 
            TokenType::Number => "num".to_string(), 
            TokenType::And => "&".to_string(), 
            TokenType::Class => "class".to_string(), 
            TokenType::Else => "else".to_string(), 
            TokenType::False => "false".to_string(), 
            TokenType::Fun => "fun".to_string(), 
            TokenType::For => "for".to_string(), 
            TokenType::If => "if".to_string(), 
            TokenType::Nil => "nil".to_string(), 
            TokenType::Or => "or".to_string(),
            TokenType::Print => "print".to_string(), 
            TokenType::Return => "return".to_string(), 
            TokenType::Super => "super".to_string(), 
            TokenType::This => "this".to_string(), 
            TokenType::True => "true".to_string(), 
            TokenType::Var => "var".to_string(), 
            TokenType::While => "while".to_string(),
            TokenType::Eof => "eof".to_string(),
        }
    }
}