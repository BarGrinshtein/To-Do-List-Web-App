package hit.bar.todolist.model;

import java.util.List;

public class Program {
    public static void main(String args[]){
        HibernateToDoListDAO hibernateToDoListDAO = HibernateToDoListDAO.getInstance();

        User user = new User("Bar G",440011,"771199");
        Item item = new Item(user.id,"Assignment");
        Item item1 = new Item(user.id,"Assignment2");
        simpleTest(item,item1,user,hibernateToDoListDAO);
        User user1 = new User("Ilan",440011,"ilanroz");
        item = new Item(user1.id,"Task");
        item1 = new Item(user1.id,"Task2");
        simpleTest(item,item1,user1,hibernateToDoListDAO);
        item.setName("haha");
        hibernateToDoListDAO.updateItem(item);
        
        List<Item> items = hibernateToDoListDAO.getUserItems(user.id);

        for (Item i:items)
            System.out.println(i.name + "" + i.userID);

        System.out.println("done");
    }

    private static void simpleTest(Item item, Item item1,User user,HibernateToDoListDAO hibernateToDoListDAO){
        try {
            hibernateToDoListDAO.addUser(user);
        }
        catch (TodoListExaption e){
            System.out.println(e.printMassege());
            return;
        }
        user.getItems().add(item);
        item.setUser(user);
        user.getItems().add(item1);
        item1.setUser(user);
        hibernateToDoListDAO.addItem(item);
        hibernateToDoListDAO.addItem(item1);
    }
}
