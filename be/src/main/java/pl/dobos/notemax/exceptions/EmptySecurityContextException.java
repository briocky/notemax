package pl.dobos.notemax.exceptions;

public class EmptySecurityContextException extends RuntimeException {

    public EmptySecurityContextException(String message) {
      super(message);
    }
}
