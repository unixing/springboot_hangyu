package com.springmvcandmysql.test;

import java.util.List;

import com.springmvcandmysql.mapper.UserMapper;
import com.springmvcandmysql.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * 
 * @ClassName:  TestMyBatis   
 * @Description: 测试ssm框架
 * @author: xyc 
 * @date:   2017年2月13日 下午7:53:09   
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:*.xml" })
public class TestSSM {
    
    @Autowired
    private UserMapper userMapper;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // 初始化SPring容器
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml",
        //        "applicationContext-mybatis.xml");
        // 从容器中获取SqlSessionFactory
        // SqlSessionFactory sqlSessionFactory =
        // applicationContext.getBean(SqlSessionFactory.class);

        // SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // this.userMapper = sqlSession.getMapper(UserMapper.class);
        //this.userMapper = applicationContext.getBean(UserMapper.class);
    }
    

    @Test
    public void test1() {
        
        List<User> queryAll = userMapper.queryAll();
        for (User user : queryAll) {
            System.out.println(user);
        }
    }
}