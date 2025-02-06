package Service;

import DAO.AccountDAO;
import Model.Account;

//import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        if(accountDAO.UserExists(account.username) != null){
            return null;
        }
        accountDAO.register(account);
        return accountDAO.UserExists(account.username);
    }

    public Account getAccount(Account account){
        if(account.username == ""){
            return null;
        }
        Account userAccount = accountDAO.UserExists(account.username);
        if(userAccount == null){    // catches bad username 
            return null;
        }
        if(account.password == accountDAO.UserExists(account.username).password){
            return userAccount;
        }
        return null;
        
    }
}
