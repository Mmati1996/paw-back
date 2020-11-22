package com.example.back.controllers;

        import com.example.back.models.User;
        import com.example.back.repositories.UserRepository;
        import com.example.back.services.UserServiceImpl;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import java.security.SecureRandom;
        import java.util.ArrayList;
        import java.util.Base64;

@RestController
@CrossOrigin(origins = "*")
public class DataController {

    //TODO -- change repository to service
    UserRepository userRepository;
    //http://localhost:8080/user?id=1

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private UserServiceImpl service;

    public DataController(UserServiceImpl service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
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
    public ResponseEntity addUser(@RequestBody User user){
        //User user = new User(username,password);
        userRepository.save(user);
        //service.addUser(user);
        return new ResponseEntity("added",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestParam String login, @RequestParam String password){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User user : users  ){
            if((user.getLogin()).equals(login)){
                if ((user.getPassword()).equals(password)){
                    getUser(user.getId()).setToken(generateNewToken());
                    userRepository.save(user);
                    return new ResponseEntity(user.getToken(),HttpStatus.OK);
                }
                return new ResponseEntity("wrong password",HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity("no user found",HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity logout (@RequestParam String token){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User user : users  ){
            if (user.getToken().equals(token)){
                user.setToken("");
                userRepository.save(user);
                return new ResponseEntity("success",HttpStatus.OK);
            }
        }
        return new ResponseEntity("wrong token",HttpStatus.NOT_FOUND);
    }


    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}