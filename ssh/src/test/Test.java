package test;

import entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import service.UserService;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("/web/WEB-INF/applicationContext.xml");
        //spring测试
        TestService testService = (TestService) context.getBean("testService");
        testService.hello();
        //hibernate测试
        UserService userService = (UserService) context.getBean("userService");
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123");
        user.setPhone("18287778741");
        user.setMail("18287778741@163.com");
        userService.add(user);
    }
}
