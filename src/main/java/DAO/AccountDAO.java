package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;


public class AccountDAO {
    

    // need register and login

    public Account register(Account account){
        // check for account valititiy
        if(account.username == null || account.username == ""){
            return null;
        }
        if(UserExists(account.username) != null){
            return null;
        }
        if(account.password.length() < 4){
            return null;
        }

        // register account
        Connection connection = ConnectionUtil.getConnection();
        try{    
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.username);
            ps.setString(2, account.password);

            ps.executeUpdate();
            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    

    // need to be able to look up all accounts for a specific one
    public Account UserExists(String username){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE username = ? ;";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
