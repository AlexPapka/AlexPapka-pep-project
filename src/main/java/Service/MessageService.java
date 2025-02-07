package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message postMessage(Message message){
        if(message.message_text == ""){
            return null;
        }
        return messageDAO.makeMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getById(message_id);
    }

    public Message deleteMessageById(int message_id){
        Message markedForDeath = messageDAO.getById(message_id);
        if(markedForDeath != null){
            messageDAO.deleteById(message_id);
            return markedForDeath;
        }
        else{
            return null;
        }
        
    }

    public Message patchMessage(Message message, int message_id){
        Message changedMessage;
        if(messageDAO.getById(message_id) != null){
            if(message.message_text != "" && message.message_text.length() < 255 ){
                messageDAO.editById(message_id, message);
                changedMessage = messageDAO.getById(message_id);
                return changedMessage;

            }else{
                return null;
            }
        }else{
            return null;
        }
        
    }

    public List<Message> getMessageByAccount(int account_id){
        return messageDAO.getByAccountId(account_id);
    }

}


