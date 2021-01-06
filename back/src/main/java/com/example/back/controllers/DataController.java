package com.example.back.controllers;

import com.example.back.models.*;
import com.example.back.repositories.*;
import com.example.back.responseModels.*;
import com.example.back.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;


@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class DataController {

    //TODO -- change repository to service
    UserRepository userRepository;
    TableRepository tableRepository;
    IdsRepository idRepository;
    TrelloListRepository trelloListRepository;
    CardRepository cardRepository;
    LabelRepository labelRepository;
    LabelIdRepository labelIdRepository;
    TaskRepository taskRepository;
    final static Logger logger = LoggerFactory.getLogger(DataController.class);


    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private UserServiceImpl userService;

    public DataController(UserServiceImpl service, UserRepository userRepository, TableRepository tableRepository,IdsRepository idRepository,TrelloListRepository trelloListRepository, CardRepository cardRepository, LabelRepository labelRepository, LabelIdRepository labelIdRepository, TaskRepository taskRepository) {
        this.userService = service;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.idRepository = idRepository;
        this.trelloListRepository = trelloListRepository;
        this.cardRepository = cardRepository;
        this.labelRepository = labelRepository;
        this.labelIdRepository = labelIdRepository;
        this.taskRepository = taskRepository;
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

    public Table findTableById(int id){
        ArrayList<Table> tables = (ArrayList<Table>) tableRepository.findAll();
        for (Table tab : tables){
            if (tab.getId()== id){
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

    public boolean canTableBeAccessed(String token, int tableId){
        Table table = findTableById(tableId);
        if (findUserByToken(token)==null){
            return false;
        }
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

    public TrelloList findListById(int id){
        ArrayList<TrelloList> lists = (ArrayList<TrelloList>) trelloListRepository.findAll();
        for (TrelloList tl : lists){
            if (tl.getId() == id){
                return tl;
            }
        }
        return null;
    }

    public boolean canListBeAccessed(String token, int listId){
        return canTableBeAccessed(token, findTableById(findListById(listId).getTable_id()).getId() );
    }

    public Card findCardById(int id){
        ArrayList<Card> cards = (ArrayList<Card>)cardRepository.findAll();
        for (Card c : cards){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public boolean canCardBeAccessed(String token, int cardId){
        return canListBeAccessed(token, findListById(findCardById(cardId).getList_id()).getId());
    }

    @PostMapping("/userByToken")
    public Response findUserByTokenRequest(@RequestBody ShortMessage token){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User us : users  ){
            if (us.getToken()!=null){
                if (us.getToken().equals(token.getParam1())){
                    return new Response(true,us.getLogin(),"");

                }
            }
        }
        return new Response(false,"","no user found");
    }

    @PostMapping("/tables/add")
    public Response addTable(@RequestHeader String token, @RequestBody ShortMessage message){

        Table table = new Table(message.getParam1());
        User user = findUserByToken(token);
        if (user != null){
            tableRepository.save(table);
            idRepository.save(new Id(user.getId(),table.getId()));
            return new Response(true,"added","");
        }
        return new Response(false,"","wrong token");
    }//tableName

    @PostMapping("/tables/delete")
    public Response deleteTable (@RequestHeader String token, @RequestBody ShortMessage message){
        Table table = findTableById(Integer.valueOf(message.getParam1()));
        if (canTableBeAccessed(token, (Integer.valueOf(message.getParam1())) )){
            ArrayList<Id> ids = (ArrayList<Id>) idRepository.findAll();
            for (Id id : ids){
                if (id.getTable_id()==table.getId()){
                    for (TrelloList tl : (ArrayList<TrelloList>) trelloListRepository.findAll()){
                        if (tl.getTable_id()==table.getId()){
                            trelloListRepository.delete(tl);
                        }
                    }
                    idRepository.delete(id);
                }

            }
            tableRepository.delete(table);
            return new Response(true,"deleted","");
        }
        return new Response(false,"","this user cannot access given table");
    }//tableId

    @PutMapping("/tables/changeName")
    public Response ChangeTableName(@RequestHeader String token, @RequestBody Message message){
        if( !canTableBeAccessed(token,Integer.valueOf(message.getParam1()))){
            return new Response(false,"","this user cannot access given table");
        }
        Table tab = findTableById(Integer.valueOf(message.getParam1()));
        tab.setName(message.getParam2());
        tableRepository.save(tab);
        return  new Response(true,"changed name to "+message.getParam2(),"");
    }//tableId  tableName

    @PostMapping("/tables/addUser")
    public Response addUserToTable(@RequestHeader String token,@RequestBody Message message){
        if (canTableBeAccessed(token,Integer.valueOf(message.getParam1()))){
             idRepository.save( new Id (findUserByLogin(message.getParam2()).getId(),findTableById(Integer.valueOf(message.getParam1())).getId())  );
            return new Response (true,"user added","");
        }
        return new Response (false,"","this user cannot access given table");
    }//tableId loginOfUserToAdd

    @PostMapping("/tables/deleteUser")
    public Response deleteUser(@RequestHeader String token, @RequestBody Message message){
        if (!canTableBeAccessed(token,Integer.valueOf(message.getParam1()))){
            return new Response(false,"","this user cannot access given table");
        }
        int id = findUserByLogin(message.getParam2()).getId();
        for (Id ids : idRepository.findAll()){
            if ( (ids.getUser_id() == id) &&  ( ids.getTable_id() == findTableById(Integer.valueOf(message.getParam1())).getId() ) ){
                idRepository.delete(ids);
                return new Response(true,"user " + message.getParam2() +" deleted from "+message.getParam1(),"");
            }
        }
        return new Response(false,message.getParam2()+" does not have acces to "+message.getParam1()+" so it cant be deleted","");
    }//tableId userToDeleteName

    @GetMapping("/userTables")
    public Iterable<Table> getUserTables(@RequestHeader String token){
        ArrayList <Table> tables = new ArrayList<Table>();
        User user = findUserByToken(token);
        for (Id ids : idRepository.findAll()){
            if (ids.getUser_id() == user.getId()){
                for (Table tabs : tableRepository.findAll()){
                    if (ids.getTable_id() == tabs.getId()){
                        tables.add(tabs);
                    }
                }
            }
        }
        return tables;
    }

    @PostMapping("/tables/getById")
    public Table getTableById(@RequestBody ShortMessage message){
        for (Table tab : tableRepository.findAll()){
            if (Integer.valueOf(message.getParam1()) == tab.getId() ){
                return tab;
            }

        }
        return null;
    }

    @PostMapping("/tables/getByName")
    public Table getTableByName(@RequestBody ShortMessage message){
        for (Table tab : tableRepository.findAll()){
            if (message.getParam1().equals(tab.getName()) ){
                return tab;
            }

        }
        return null;
    }

    @GetMapping("/trelloLists/all")
    public Iterable<TrelloList> getAllLists(){
        return trelloListRepository.findAll();
    }

    @PostMapping("/lists/add")
    public Response addList(@RequestHeader String token, @RequestBody Message message){
        User user = findUserByToken(token);
            if(user == null){
                return new Response(false,"","no user found");
            }
        if (canTableBeAccessed(token,Integer.valueOf(message.getParam1()))){
            TrelloList list = new TrelloList(message.getParam2(),Integer.valueOf(message.getParam1()));
            trelloListRepository.save(list);
            return new Response(false,"dodano","");
        }return new Response(false,"","user nie ma dostepu do tablicy");
    }//tableId, listName

    @PostMapping("/lists/delete")
    public Response deleteList(@RequestHeader String token, @RequestBody ShortMessage message){
        if (canListBeAccessed(token,Integer.valueOf(message.getParam1()))){
            ArrayList<TrelloList> lists = (ArrayList<TrelloList>)trelloListRepository.findAll();
            for (TrelloList tl : lists){
                if (tl.getId() == Integer.valueOf(message.getParam1())){
                    trelloListRepository.delete(tl);
                    return new Response(true, "list deleted","");
                }
            }
        }
        return new Response(false,"","user cannot access given list");
    }//listId

    public void deleteList(int id){
        for (TrelloList tl : (ArrayList<TrelloList>)trelloListRepository.findAll()){
            trelloListRepository.delete(tl);
        }
    }

    @PutMapping("/list/changeName")
    public Response changeListName(@RequestHeader String token,@RequestBody Message message){
        if (!( canListBeAccessed(token,Integer.valueOf(message.getParam1())) )){
            return new Response(false,"","user cannot access list");
        }
        TrelloList list = findListById(Integer.valueOf(message.getParam1()));
        list.setName(message.getParam2());
        trelloListRepository.save(list);
        return new Response(true,"changed name to "+message.getParam2(),"");
    }//listId listNewName

    @PostMapping("/lists")
    public Iterable<TrelloList> getTablesLists(@RequestBody ShortMessage message){
        ArrayList<TrelloList> toReturn = new ArrayList<TrelloList>();
        for (TrelloList tl : trelloListRepository.findAll()){
            if (tl.getTable_id() == Integer.valueOf(message.getParam1())){
                toReturn.add(tl);
            }
        }
        return toReturn;
    }//table_id

     @PostMapping("/getTableWithContentById")
     public Table2 getTableWithContentById(@RequestHeader String token, @RequestBody ShortMessage tableId){
        if (!(canTableBeAccessed(token, Integer.valueOf(tableId.getParam1())))){
            return null;
        }

        Table2 tableToReturn = new Table2(findTableById( Integer.valueOf(tableId.getParam1()) ));
        for (TrelloList tl : trelloListRepository.findAll()){
            if (tl.getTable_id() == Integer.valueOf(tableId.getParam1())){
                tableToReturn.lists.add(new List2(tl));
            }
        }

        for (List2 list2 : tableToReturn.lists ){
            for (Card c : cardRepository.findAll()){
                if (c.getList_id() == list2.getId()){
                    list2.cards.add(new Card2(c));
                }
            }
        }


//        for(List2 list2 : tableToReturn.lists){
//            for (Card2 c : list2.getCards()){
//                for (Task task : taskRepository.findAll()){
//                    if (task.getCardId()==c.getId()){
//                        c.tasks.add(new Task2(task));
//                    }
//
//                }
//            }
//        }

        return tableToReturn;
     }

     @GetMapping("/cards/list")
     public Iterable<Card> listCards(@RequestParam int id){
        ArrayList<Card> cards = new ArrayList<>();
        for (Card c : cardRepository.findAll()){
            if(c.getId() == id){
                cards.add(c);
            }
        }
        return cards;
     }

    @PostMapping("/card/add")
    public Response addCard(@RequestHeader String token, @RequestBody Message message){
        User user = findUserByToken(token);
        if(user == null){
            return new Response(false,"","no user found");
        }
            Card card = new Card(message.getParam2(),Integer.valueOf(message.getParam1()));
        return new Response(false,"","user cannot access table");

    }//listId cardName

    @PostMapping("/card/delete")
    public Response deleteCard(@RequestHeader String token, @RequestBody ShortMessage message){
        if (canCardBeAccessed(token,Integer.valueOf(message.getParam1()))){
            ArrayList<Card> cards = (ArrayList<Card>) cardRepository.findAll();
            for (Card c : cards){
                if (c.getId() == Integer.valueOf(message.getParam1())){
                    cardRepository.delete(c);
                    return new Response(true,"card deleted","");
                }
            }
        }
        return new Response (false,"","user cannot access it");
    }//cardId

    @PostMapping("/card/changeName")
    public Response changeCardName (@RequestHeader String token, @RequestBody Message message){
        if(canCardBeAccessed(token,Integer.valueOf(message.getParam1()))){
            Card card = findCardById(Integer.valueOf(message.getParam1()));
            card.setName(message.getParam2());
            cardRepository.save(card);
            return new Response(true,"changed card name to "+message.getParam2(),"");
        }return new Response(false,"","can't access given card");
    }//cardId cardNewName

    @PostMapping("/card/setDate")
    public Response setCardDate(@RequestHeader String token,@RequestBody Message message){
        if (canCardBeAccessed(token,Integer.valueOf(message.getParam1()))){
            Card card = findCardById(Integer.valueOf(message.getParam1()));
            String tab[] = message.getParam2().split("-");
            card.date = new Date(Integer.valueOf(tab[0])-1901,Integer.valueOf(tab[1])+11,Integer.valueOf(tab[2]));
            cardRepository.save(card);
            return new Response(true,"date set","");
        }
        return new Response(false,"","you cannot access this card");
    }//cardId  date

    @GetMapping("/cards/all")
    public Iterable<Card> getAllCards(){
        ArrayList<Card> cards = new ArrayList<>();
        for (Card c : cardRepository.findAll()){
            cards.add(c);
        }
        return cards;
    }

    @GetMapping("/cards/getById")
    public Card getCardById(@RequestBody ShortMessage msg){
        for (Card c : cardRepository.findAll()){
            if (Integer.valueOf(msg.getParam1()) == c.getId()){
                return c;
            }
        }
        return null;
    }

    @GetMapping("/tasks/getById")
    public Task2 getTaskById(@RequestBody ShortMessage msg){
        for (Task t : taskRepository.findAll()){
            if (Integer.valueOf(msg.getParam1()) == t.getId()){
                return  new Task2(t);
            }
        }
        return null;
    }

    @PostMapping("/tasks/add")
    public Task2 addTasks(@RequestHeader String token, @RequestBody Message msg){
        User user = findUserByToken(token);
        if (user == null){
            return null;
        }
        Task task = new Task (msg.getParam1(),false,Integer.valueOf(msg.getParam2()));
        return new Task2(task);
    }//title card_id

    @PostMapping("/tasks/delete")
    public Response deleteTask(@RequestHeader String token, @RequestBody ShortMessage msg){

        if(! (canCardBeAccessed(token,findCardById(Integer.valueOf(msg.getParam1())).getList_id())) )  {
            return new Response(false,"","couldn't delete task");
        }

        ArrayList<Task> tasks = (ArrayList<Task>) taskRepository.findAll();
        for (Task t : tasks){
            if (t.getId() == Integer.valueOf(msg.getParam1())){
                taskRepository.delete(t);
                return new Response(true,"task deleted succesfully","");
            }

        }
        return new Response(false,"","couldn't delete task");
    }//task_id


    @PostMapping("/label/add")
    public Label addLabel(@RequestHeader String token, @RequestBody Message msg){
        User user = findUserByToken(token);

        if (canCardBeAccessed(token,Integer.valueOf(msg.getParam1()))){
            if(user != null){
                Label label = new Label(msg.getParam2());
                labelRepository.save(label);
                labelIdRepository.save(new LabelId(user.getId(),label.getId()));
                return label;
            }


        }
        return null;
    }//cardId labelName

    @PostMapping("/label/changeName")
    public Response changeLabelName(@RequestHeader String token, @RequestBody Message msg){
        for (Label labels : labelRepository.findAll()){
           if (labels.getId() == Integer.valueOf(msg.getParam1())){
               labels.setName(msg.getParam2());
               labelRepository.save(labels);
               return new Response(true,"changed name","");
           }
        }
        return new Response(false,"","couldn't change name");
    }//label_id, new_label_name

    @PostMapping("/label/delete")
    public Response deleteLabel(@RequestHeader String token, @RequestBody ShortMessage msg){
        for (LabelId ids : labelIdRepository.findAll()){
            if (ids.getLabelId() == Integer.valueOf(msg.getParam1())){
                labelIdRepository.delete(ids);
            }
        }
        for (Label labels : labelRepository.findAll()){
            if (labels.getId() == Integer.valueOf(msg.getParam1())){
                labelRepository.delete(labels);
                return new Response(true,"deleted","");
            }
        }
        return new Response(true,"","couldn't delete");
    }//labelId

    @PostMapping("/label/addToCard")
    public Response addLabelToCard(@RequestHeader String token, @RequestBody Message msg){
        if  (!(canCardBeAccessed(token, Integer.valueOf(msg.getParam1())))){
            return new Response(false,"","failed to access card");
        }
        LabelId id = new LabelId(Integer.valueOf(msg.getParam2()),Integer.valueOf(msg.getParam1()));
        return  new Response(true,"added label to card","");
    }//cardId, labelId

    @PostMapping("/cards/modify")
    public Response modifyCard(@RequestHeader String token, @RequestBody CardModifier cm){
        if (1==1)
        {
            return new Response(true,"daniel jezeli to widzisz to mi powiedz!","daniel jezeli to widzisz to mi powiedz!");
        }
            for (Task t : taskRepository.findAll()){
                if(t.getId() == cm.getParam2()){
                    taskRepository.delete(t);
                }
            }
            for (Task2 t : cm.getParam1()){
                taskRepository.save(new Task(t,cm.getParam2()));
            }
            return new Response(true,"card modified","");
    }

    //TODO udostepnianie tablicy

}






