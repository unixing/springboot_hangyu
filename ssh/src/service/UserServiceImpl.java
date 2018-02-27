package service;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UserServiceImpl implements UserService {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public void add(User user) {
        Session s = sessionFactory.openSession();
        Transaction tx= s.beginTransaction();
        //当数据库为oracle时需要给自增字段赋值
        //获取数据库中id最大值
        Object maxId = s.createQuery("select max(id) from User").uniqueResult();
        int maxIndex = 0;
        if (maxId!=null)
            maxIndex = (Integer)maxId;
        user.setId(maxIndex+1);
        s.save(user);
        tx.commit();
    }

    @Override
    public User findUserById(int id) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        User user = (User) s.getNamedQuery("queryUserById").setParameter(0,id).uniqueResult();
        return user;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
