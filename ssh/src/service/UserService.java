package service;

import entity.User;

public interface UserService {
    public void  add(User user);

    public User findUserById(int id);
}
