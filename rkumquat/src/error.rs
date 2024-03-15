use crate::HAD_ERROR;

pub fn error(line: usize, msg: String) {
    report(line, "".to_string(), msg);
}
pub fn report(line: usize, at: String, msg: String) {
    eprintln!("[line {:?}] Error {:?}: {}", line, at, msg);
    unsafe { HAD_ERROR = true };
}