package kumquat;

import java.util.List;

interface KumquatCallable {
  int arity();
  Object call(Interpreter interpreter, List<Object> arguments);
}
