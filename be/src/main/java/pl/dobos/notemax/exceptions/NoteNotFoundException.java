package pl.dobos.notemax.exceptions;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(String message) {
      super(message);
    }
}
