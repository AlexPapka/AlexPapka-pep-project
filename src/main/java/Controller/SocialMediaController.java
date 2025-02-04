package Controller;

import org.eclipse.jetty.http.HttpTester.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

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

        app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = AccountService.addAccount(account);              // account service not made yet
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account getAccount = AccountService.getAccount(account);            // account service not made yet
        if(getAccount != null){
            ctx.json(mapper.writeValueAsString(getAccount));
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageText = MessageService.postMessage(message);      // message service not made yet
        if(messageText != null){
            ctx.json(mapper.writeValueAsString(messageText));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());      // messageService doesnt exist yet
    }

    private void getMessageById(Context ctx){
        ctx.json(messageService.getMessageById(ctx.pathParam("message_id")));   // messageService doesnt exist yet
    }

    private void deleteMessageById(Context ctx){
        // dont know yet
    }

    private void patchMessageById(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageChanged = MessageService.patchMessage(ctx.pathParam("message_id"));    // Message service Doesnt exist yet
        if(messageChanged != null){
            ctx.json(mapper.writeValueAsString(messageChanged));
        }else{
            ctx.status(400);
        }
    }

    private void getAllAccountMessageHandler(Context ctx){
        ctx.json(messageService.getMessageByAccount(ctx.pathParam("account_id")));  // MessageService doesnt exist yet
    }

}