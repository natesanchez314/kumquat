package kumquat;


import java.util.HashMap;
import java.util.Map;

public class KumquatInstance {
  private KumquatClass kClass;
  private final Map<String, Object> fields = new HashMap<>();

  KumquatInstance(KumquatClass kClass) {
    this.kClass = kClass;
  }

  Object get(Token name) {
    if (fields.containsKey(name.lexeme)) return fields.get(name.lexeme);
    KumquatFunction method = kClass.findMethod(name.lexeme);
    if (method != null) return method.bind(this);
    throw new RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
  }

  Object set(Token name, Object value) {
    fields.put(name.lexeme, value);
    return value;
  }

  @Override
  public String toString() {
    return kClass.name + " instance";
  }
}
