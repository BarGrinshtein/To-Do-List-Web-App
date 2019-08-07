package hit.bar.todolist.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Users")
public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2333940704073021607L;
	
	
	@Column(name = "Name",nullable = false)
    String name;
	
    @Column(name = "Password",nullable = false)
    String password;

    @Id
    @Column(name = "ID")
    int id;
    
    @OneToMany (fetch = FetchType.EAGER,mappedBy = "user")
    private Collection<Item> items = new ArrayList<>();

    public User(String name ,int id ,String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(){}

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
