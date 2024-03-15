mod error;
mod scanner;
mod token_type;
mod token;

use std::{env, fs::{self, File}, io};

use scanner::Scanner;

static mut HAD_ERROR: bool = false;

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() > 1 {
        println!("Usage: kumquat [script]")
    } else if args.len() == 1 {
        run_file(args.get(0).unwrap())
    } else {
        run_prompt();
    }
}

fn run_file(path: &String) {
    let bytes = fs::read(path)
        .expect("Unable to open file!");
    run(String::from_utf8(bytes)
        .map_err(|non_utf8| String::from_utf8_lossy(non_utf8.as_bytes())
        .into_owned()).unwrap())
}

fn run_prompt() {
    let mut input = String::new();
    loop {
        println!("> ");
        let line = io::stdin().read_line(&mut input);
        if line.is_ok() {
            run(line.unwrap().to_string());
        }
    }
}

fn run(src: String) {
    let mut scanner = Scanner::new(src);
    let tokens = scanner.scan_tokens();
    for token in tokens {
        println!("{}", token.to_string());
    }
}