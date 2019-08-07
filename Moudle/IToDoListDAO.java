package hit.bar.todolist.model;

import java.util.List;

public interface IToDoListDAO {

    void addItem(Item item);

    void deleteItem(int id);

    void updateItem(Item item);
    
    Item getItem(int id);



    boolean validationTest(int id, String password) throws TodoListExaption;

    User getUser(int id);

    void addUser(User user) throws TodoListExaption;

    void unsubscribeUser(int id);

    void updateUser(User user);

    List<Item> getUserItems(int id);
}
