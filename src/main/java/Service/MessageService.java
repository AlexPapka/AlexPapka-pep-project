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
        return messageDAO.makeMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getMessages();
    }

    public List<Message> getMessageById(int message_id){
        return messageDAO.getById(message_id);
    }

    public void deleteMessageById(){
        // idk yet
    }

    public Message patchMessage(Message message, int message_id){
        if(messageDAO.getById(message_id) != null){
            if(message.message_text != null && message.message_text.length() < 255 ){
                messageDAO.editById(message_id, message);

            }else{
                return null;
            }
        }else{
            return null;
        }
        return message;
    }

    public List<Message> getMessageByAccount(int account_id){
        return messageDAO.getByAccountId(account_id);
    }

}


