package action;

import com.opensymphony.xwork2.ActionSupport;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import service.UserService;

/** 
 * Created by kinthon on 17-3-31. 
 */  
public class UserAction extends ActionSupport {
    private String username;
    private String password;
    private String phone;
    private String mail;
    private int id;
    private User user;
    @Autowired  
    private UserService userService;
  
    public String add()  
    {  
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setMail(mail);
        userService.add(user);
        return SUCCESS;  
    }

    public String load(){
        user = userService.findUserById(id);
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}