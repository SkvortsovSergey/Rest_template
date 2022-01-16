package org.example;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://91.241.64.178:7081/api/users";
    private String cookie;
    private String result;
    HttpHeaders headers = new HttpHeaders();



    public List<User> getAllUsers () {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        cookie = forEntity.getHeaders().get("Set-Cookie").get(0);
        headers.add("Cookie", cookie);
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });
        List<User> allUsers = responseEntity.getBody();
        System.out.println(cookie);
        return allUsers;
    }

    public User getUser (Long id) {
        User user = restTemplate.getForObject(URL + "/" + id, User.class);
        return user;
    }

    public void saveUser (User user) {

        HttpEntity<User> entity = new HttpEntity<>(user,headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
        result = responseEntity.getBody();
    }

    public void updateUser (User user) {

        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> responseEntity=restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        result += responseEntity.getBody();

    }

    public void deleteUser (User user) {
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> responseEntity=restTemplate.exchange(URL+"/"+user.getId(), HttpMethod.DELETE, entity, String.class);
        result += responseEntity.getBody();
    }

    public String getResult () {
        return result;
    }
}
