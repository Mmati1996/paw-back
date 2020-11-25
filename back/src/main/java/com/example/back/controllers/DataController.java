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
    IdsRepository idRepository;


    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private UserServiceImpl userService;

    public DataController(UserServiceImpl service, UserRepository userRepository, TableRepository tableRepository,IdsRepository idRepository) {
        this.userService = service;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.idRepository = idRepository;
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

    public Table findTableByName(String name){
        ArrayList<Table> tables = (ArrayList<Table>) tableRepository.findAll();
        for (Table tab : tables){
            if (tab.getName().equals(name)){
                return tab;
            }

        }
        return null;
    }

    public User findUserByLogin(String login){
        for (User user : userRepository.findAll()){
            if (user.getLogin().equals(login)){
                return user;
            }
        }
        return null;
    }

    public boolean canTableBeAccessed(String token, String tableName){
        Table table = findTableByName(tableName);
        if ( table==null ){
            return false;
        }
        ArrayList<Id> ids = (ArrayList<Id>) idRepository.findAll();
        for (Id id : ids){
            if(  ( id.getUser_id() == findUserByToken(token).getId() ) && (id.getTable_id()) == table.getId()  ){
                return true;
            }
        }
        return false;
    }


    @PostMapping("/userByToken")
    public User findUserByTokenRequest(@RequestBody String token){
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

    @PostMapping("/tables/add")//tableName
    public Response addTable(@RequestHeader String token, @RequestParam String param1){

        Table table = new Table(param1);
        User user = findUserByToken(token);
        if (user != null){
            tableRepository.save(table);
            idRepository.save(new Id(user.getId(),table.getId()));
            return new Response(true,"added","");
        }
        return new Response(false,"","wrong token");
    }

    @PostMapping("/tables/delete")//tableName
    public Response deleteTable (@RequestHeader String token, @RequestParam String param1){
        Table table = findTableByName(param1);
        if (canTableBeAccessed(token, param1)){
            ArrayList<Id> ids = (ArrayList<Id>) idRepository.findAll();
            for (Id id : ids){
                if (id.getTable_id()==table.getId()){
                    idRepository.delete(id);
                }

            }
            tableRepository.delete(table);
            return new Response(true,"deleted","");
        }
        return new Response(false,"","this user cannot access given table");
    }

    @PostMapping("/tables/changeName")//tableToChange newName
    public Response ChangeTableName(@RequestHeader String token, @RequestBody Message message){
        if( !canTableBeAccessed(token,message.getParam1() ) ){
            return new Response(false,"","this user cannot access given table");
        }
        Table tab = findTableByName(message.getParam1());
        tab.setName(message.getParam2());
        tableRepository.save(tab);
        return  new Response(true,"changed name to "+message.getParam2(),"");
    }

    @PostMapping("/tables/addUser")//table userToAdd(login)
    public Response addUserToTable(@RequestHeader String token,@RequestBody Message message){
        if (canTableBeAccessed(token,message.getParam1())){
             idRepository.save( new Id (findUserByLogin(message.getParam2()).getId(),findTableByName(message.getParam1()).getId())  );
            return new Response (false,"user added","");
        }
        return new Response (false,"","this user cannot access given table");
    }

    @PostMapping("/tables/deleteUser")//table user(login)
    public Response deleteUser(@RequestHeader String token, @RequestBody Message message){
        if (!canTableBeAccessed(token,message.getParam1())){
            return new Response(false,"","this user cannot access given table");
        }
        int id = findUserByLogin(message.getParam2()).getId();
        for (Id ids : idRepository.findAll()){
            if ( (ids.getUser_id() == id) &&  ( ids.getTable_id() == findTableByName(message.getParam1()).getId() ) ){
                idRepository.delete(ids);
                return new Response(true,"user " + message.getParam2() +" deleted from "+message.getParam1(),"");
            }
        }
        return new Response(false,message.getParam2()+" does not have acces to "+message.getParam1()+" so it cant be deleted","");
    }
}