package Controller;

//import org.eclipse.jetty.http.HttpTester.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Message;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */ 
public class SocialMediaController {
    AccountService accountService;  // class variable
    MessageService messageService;  // class variable

    public SocialMediaController(){     // constructor
        this.accountService = new AccountService();
        this.messageService = new MessageService(); 
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::patchMessageById);
        app.get("accounts/{account_id}/messages", this::getAllAccountMessageHandler);

        //app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    

    private void registerHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);              
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }
    /* */
    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account getAccount = accountService.getAccount(account);            
        if(getAccount != null){
            ctx.json(mapper.writeValueAsString(getAccount));
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageText = messageService.postMessage(message);      
        if(messageText != null){
            ctx.json(mapper.writeValueAsString(messageText));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());      
    }

    private void getMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnedMessage = messageService.getMessageById(message_id);
        if(returnedMessage != null){
        ctx.json(mapper.writeValueAsString(returnedMessage)); 
        }else{
            ctx.status(200);
        }  
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnedMessage = messageService.deleteMessageById(message_id);
        if(returnedMessage != null){
            ctx.json(mapper.writeValueAsString(returnedMessage));
        }else{
            // all good
        }

    }

    private void patchMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageChanged = messageService.patchMessage(message, message_id);    
        if(messageChanged != null){
            ctx.json(mapper.writeValueAsString(messageChanged));
        }else{
            ctx.status(400);
        }
    }

    private void getAllAccountMessageHandler(Context ctx){
        ctx.json(messageService.getMessageByAccount(Integer.parseInt(ctx.pathParam("account_id"))));  // MessageService doesnt exist yet
    }

}