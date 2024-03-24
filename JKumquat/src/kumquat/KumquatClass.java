package kumquat;

import java.util.List;
import java.util.Map;

public class KumquatClass implements KumquatCallable{
  final String name;
  final KumquatClass superClass;
  private final Map<String, KumquatFunction> methods;

  KumquatClass(String name, KumquatClass superClass, Map<String, KumquatFunction> methods) {
    this.name = name;
    this.superClass = superClass;
    this.methods = methods;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int arity() {
    KumquatFunction initializer = findMethod("init");
    if (initializer == null) return 0;
    return initializer.arity();
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    KumquatInstance instance = new KumquatInstance(this);
    KumquatFunction initializer = findMethod("init");
    if (initializer != null) {
      initializer.bind(instance).call(interpreter, arguments);
    }
    return instance;
  }

  KumquatFunction findMethod(String name) {
    if (methods.containsKey(name)) {
      return methods.get(name);
    }
    if (superClass != null) {
      return superClass.findMethod(name);
    }
    return null;
  }
}
