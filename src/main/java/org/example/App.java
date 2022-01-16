package org.example;

import org.example.configurations.MyConfig;
import org.example.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


/**
 * Hello world!
 */
public class App {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);


        List<User> allUsers = communication.getAllUsers();
        User user = new User( 3l,"James","Brown",(byte)15  );
        communication.saveUser(user);
        user.setName("Thomas");
        user.setLastName("Shelby");
        communication.updateUser(user);
        communication.deleteUser(user);
        System.out.println(communication.getResult());

    }
}
