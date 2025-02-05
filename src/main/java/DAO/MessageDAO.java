package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    
    // needs create message, retreve all, retreve by id, delete by id, edit by id, retreive all from user

    public Message makeMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message (posted_by, message_text) VALUES (?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message.posted_by);
            ps.setString(2, message.message_text);

            ps.executeUpdate();
            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public List<Message> getById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public void editById(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text = ?, posted_by = ?, time_posted_epoch = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message.message_text);
            ps.setInt(2, message.posted_by);
            ps.setLong(3, message.time_posted_epoch);
            ps.setInt(4, message_id);

            ps.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            String sql = "SELECT * FROM message WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, account_id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

}
