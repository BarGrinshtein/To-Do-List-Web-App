package hit.bar.todolist.model;

public class TodoListExaption extends Exception {
    String ex;

    public TodoListExaption(String message) {
        super(message);
        this.ex = message;
    }

    public String printMassege(){
        return ex;
    }
}
