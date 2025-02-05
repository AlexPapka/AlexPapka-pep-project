package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        return accountDAO.register(account);
    }

    public Account getAccount(Account account){
        return accountDAO.login(account);
    }
}
