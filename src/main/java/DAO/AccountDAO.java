package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    

    // need register and login

    public Account register(Account account){
        // check for account valititiy
        if(account.username == null){
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

    public Account login(Account account){
        Account userAccount = UserExists(account.username);
        if(userAccount == null){
            return null;
        }
        if(userAccount.password != account.password){
            return null;
        }
        return userAccount;
    }

    // need to be able to look up all accounts for a specific one
    // private to try to prevent others from stealing passwords or account info
    private Account UserExists(String username){
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
