package hit.bar.todolist.model;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import hit.bar.todolist.model.*;
import javax.servlet.http.HttpSession;

public class HibernateToDoListDAO implements IToDoListDAO {

    private static HibernateToDoListDAO hibernateToDoListDAO = null;

    SessionFactory factory = null;
    enum Methods {Add , Delete , Update}

    private HibernateToDoListDAO(){
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }
    
    //Adding items to the itames table.
    @Override
    public void addItem(Item item) {
        try {
            commitAction(item, Methods.Add);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //Deleting items from the table.
    @Override
    public void deleteItem(int id) {
        Session session = factory.openSession();
        Criteria criteria =  session.createCriteria(Item.class);
        criteria.add(Restrictions.eq("code",id));
        List<Item> item = criteria.list();
        try {
            commitAction(item.get(0), Methods.Delete);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            commitAction(item, Methods.Update);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) throws TodoListExaption {
        try {
            commitAction(user, Methods.Add);
        }
        catch (TodoListExaption e){
            throw e;
        }
    }

    @Override
    public void unsubscribeUser(int id) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id",id));
        List<User> user = criteria.list();
        try {
            commitAction(user.get(0), Methods.Delete);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            commitAction(user, Methods.Update);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void commitAction(Object obj , Methods method) throws TodoListExaption{
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            if(obj instanceof User) {
                switch (method) {
                    case Add:
                        try {

                            checkID(((User) obj).getId(),((User)obj).password);
                            session.save((User) obj);
                        }
                        catch (TodoListExaption e){
                            throw e;
                        }
                        break;
                    case Delete:
                        session.delete((User)obj);
                        break;
                    case Update:
                        session.update((User)obj);
                        break;
                    default:
                        break;
                }
            }
            else {
                switch (method) {
                    case Add:
                        session.save((Item)obj);
                        break;
                    case Delete:
                        session.delete((Item)obj);
                        break;
                    case Update:
                        session.update((Item)obj);
                        break;
                    default:
                        break;
                }
            }
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
    }


    @Override
    public List<Item> getUserItems(int id) {
        List<Item> items = null;
        Session session = factory.openSession();
        try {
            Criteria criteria = session.createCriteria(Item.class);
            criteria.add(Restrictions.eq("userID",id));
            items = criteria.list();
        }
        catch (HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
            return items;
        }
    }

    public static HibernateToDoListDAO getInstance(){
        if(hibernateToDoListDAO == null)
            hibernateToDoListDAO = new HibernateToDoListDAO();
        return hibernateToDoListDAO;
    }

    private void  checkID(int id , String password) throws TodoListExaption{
        Session session = factory.openSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("id",id));
            List<User> users = criteria.list();
            if(!users.isEmpty())
                throw new TodoListExaption("ID");

            Criteria cr = session.createCriteria(User.class);
            cr.add(Restrictions.eq("password",password));
            users = cr.list();
            if(!users.isEmpty())
                throw new TodoListExaption("Password");
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean validationTest(int id, String password) throws TodoListExaption {

        boolean exist = true;
        Session session = factory.openSession();
        List<User> users = null;
        try{
            Criteria cr = session.createCriteria(User.class);
            cr.add(Restrictions.eq("id",id));
            cr.add(Restrictions.eq("password",password));
            users = cr.list();
            if(users.isEmpty())
                exist = false;
        }
        catch (HibernateException e){
            throw new TodoListExaption("There is a connection failure with the database server, check if the MAMP server is up.");
        }
        finally {
            session.close();
        }
        return exist;
    }

    @Override
    public User getUser(int id) {
        List<User> users = null;
        Session session = factory.openSession();
        try {
            Criteria cr = session.createCriteria(User.class);
            cr.add(Restrictions.eq("id",id));
            users = cr.list();
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return users.get(0);
    }

	@Override
	public Item getItem(int id) {
		Session session = factory.openSession();
		List<Item> items = null;
		try {
		   Criteria cr = session.createCriteria(Item.class);
		   cr.add(Restrictions.eq("code", id));
		   items = cr.list();
		}
		catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
		if(items == null || items.size()<1)
			return null;
		return items.get(0);
	}
}
