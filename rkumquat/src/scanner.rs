use std::any::TypeId;

use crate::{error::error, token::Token, token_type::TokenType};

pub struct Scanner {
    source: String,
    tokens: Vec<Token>,
    start: usize,
    current: usize,
    line: usize,
}

impl Scanner {
    pub fn new(source: String) -> Self {
        Self {
            source,
            tokens: Vec::<Token>::new(),
            start: 0,
            current: 0,
            line: 1,
        }
    }

    pub fn scan_tokens(&mut self) -> &Vec<Token> {
        while !self.is_at_end() {
            self.start = self.current;
            self.scan_token();
        }
        let _ = &self.tokens.push(Token::new(TokenType::Eof, "".to_owned(), None, self.line));
        &self.tokens
    }

    fn scan_token(&mut self) {
        let c = self.advance();
        match c {
            '(' => self.add_token_helper(TokenType::LeftParen),
            ')' => self.add_token_helper(TokenType::RightParen),
            '{' => self.add_token_helper(TokenType::LeftBrace),
            '}' => self.add_token_helper(TokenType::RightBrace),
            ',' => self.add_token_helper(TokenType::Comma),
            '.' => self.add_token_helper(TokenType::Dot),
            '-' => self.add_token_helper(TokenType::Minus),
            '+' => self.add_token_helper(TokenType::Plus),
            ';' => self.add_token_helper(TokenType::Semicolon),
            '*' => self.add_token_helper(TokenType::Star),
            _ => error(self.line, "Unexpected character".to_owned()),
        }
    }

    fn advance(&mut self) -> char {
        self.current += 1;
        self.source.chars().nth(self.current).unwrap()
    }

    fn add_token_helper(&mut self, token_type: TokenType) {
        self.add_token(token_type, None);
    }

    fn add_token(&mut self, token_type: TokenType, literal: Option<TypeId>) {
        let text = &self.source[self.start..self.current];
        self.tokens.push(Token::new(token_type, text.to_owned(), literal, self.line));
    }

    fn is_at_end(&self) -> bool {
        self.current >= self.source.len()
    }
}