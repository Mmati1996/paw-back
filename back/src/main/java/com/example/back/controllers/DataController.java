package com.example.back.controllers;

import com.example.back.models.Id;
import com.example.back.models.Table;
import com.example.back.models.User;
import com.example.back.repositories.IdsRepository;
import com.example.back.repositories.TableRepository;
import com.example.back.repositories.UserRepository;
import com.example.back.responseModels.*;
import com.example.back.services.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "*")
public class DataController {

    //TODO -- change repository to service
    UserRepository userRepository;
    TableRepository tableRepository;
    IdsRepository idsRepository;


    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private UserServiceImpl userService;

    public DataController(UserServiceImpl service, UserRepository userRepository, TableRepository tableRepository,IdsRepository idsRepository) {
        this.userService = service;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.idsRepository =  idsRepository;
    }


    @GetMapping("/user")
    public User getUser(@RequestParam int id){
        return userRepository.findById(id).get();
    }

    //http://localhost:8080/users/all
    @GetMapping("/users/all")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public Response addUser(@RequestBody User user){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User us : users  ){
            if (us.getLogin().equals(user.getLogin()));{
                return new Response (false,"","login already exists");
            }
        }
        //User user = new User(username,password);
        userRepository.save(user);
        //service.addUser(user);
        return new Response (true,"added","");
    }

    @PostMapping("/login")
    public LoginResponse login (@RequestBody LoginMsg msg){
        String login = msg.getLogin();
        String password = msg.getPassword();
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User user : users  ){
            if((user.getLogin()).equals(login)){
                if ((user.getPassword()).equals(password)){
                    getUser(user.getId()).setToken(generateNewToken());
                    userRepository.save(user);
                    return new LoginResponse(true,new Token(user.getToken()),"");//loged in
                }
                return new LoginResponse(false,null,"wrong password");// wrong password, couldn't log in
            }
        }
        return new LoginResponse(false,null,"no user found");//no user, couldnt log in
    }

    @PostMapping("/logout")
    public Response logout (@RequestBody LogoutMsg msg){

        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User user : users  ){
            if (user.getToken().equals(msg.getToken())){
                user.setToken(null);
                userRepository.save(user);
                return new Response(true,"","");//logged out successfully
            }
        }
        return new Response(false,"","wrong token");//couldn't log out, token not found
    }


    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @GetMapping("/tables/all")
    public Iterable<Table> getAllTables(){
        return tableRepository.findAll();
    }

    @PostMapping("tables/add")
    public Response addTable(@RequestParam String token, @RequestParam String tableName){
        Table table = new Table(tableName);
        User user = findUserByToken(token);
        if (user != null){
            tableRepository.save(table);
            idsRepository.save(new Id(user.getId(),table.getId()));
            return new Response(true,"user added","");
        }
        return new Response(false,"","wrong token");
    }


    public User findUserByToken(String token){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User us : users  ){
            if (us.getToken()!=null){
                if (us.getToken().equals(token)){
                    return us;
                }
            }
        }
        return null;
    }
}