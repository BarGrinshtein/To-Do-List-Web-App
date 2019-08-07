package hit.bar.todolist.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Items")
public class Item {
	@Column(name = "Craetion_Date")
	String creationDate;
	@Column(name = "Expiration_Date")
	String ExpDate;
    @Column(name = "UserName")
    int userID;
    @Column(name = "Task_Name")
    String name;
    @Id
    @GenericGenerator(name = "gen",strategy = "increment")
    @GeneratedValue(generator="gen")
    @Column (name = "Task_Code" ,updatable = false, nullable = false)
    private int code;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user = null;

    public Item(){

    }

    public Item(int id, String name){
        this.userID = id;
        this.name = name;
    }
    
    

    public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getExpDate() {
		return ExpDate;
	}

	public void setExpDate(String expDate) {
		ExpDate = expDate;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
