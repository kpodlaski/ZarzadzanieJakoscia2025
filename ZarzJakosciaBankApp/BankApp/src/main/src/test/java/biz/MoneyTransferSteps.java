package biz;

import db.dao.DAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import model.User;
import model.exceptions.OperationIsNotAllowedException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyTransferSteps {

    AccountManager target;
    AuthenticationManager auth;
    BankHistory history;
    InterestOperator interest;

    DAO dao;

    @Given("App is fully ready to work")
    public void setUp(){
        dao = mock(DAO.class);
        interest = mock(InterestOperator.class);
        history = mock(BankHistory.class);
        auth = mock(AuthenticationManager.class);
        target = new AccountManager();
        target.dao = dao;
        target.history = history;
        target.auth = auth;
        target.interestOperator= interest;
    }

    @Given("We have user {string} with id: {int}")
    public void we_have_user_with_id(String name, Integer id) throws SQLException {
        User user = new User();
        user.setName( name);
        user.setId(id);
        when(dao.findUserByName(name)).thenReturn(user);
    }

    @Given("{string} have account: {int} with: {int} pln")
    public void haveAccountWithPln(String userName, int sourceAcc, int amount) throws SQLException {
        User u = dao.findUserByName(userName);
        Account acc = new Account();
        acc.setOwner(u);
        acc.setAmmount(amount);
        acc.setId(sourceAcc);
        when(dao.findAccountById(sourceAcc)).thenReturn(acc);
    }

    @Given("There is an account:{int} with {int} pln")
    public void thereIsAnAccountWithPln(int accId, int arg1) throws SQLException {
        Account acc = new Account();
        acc.setAmmount(arg1);
        acc.setId(accId);
        when(dao.findAccountById(accId)).thenReturn(acc);
    }

    @Given("Everything is authorised")
    public void everythingIsAuthorised() {
        when(auth.canInvokeOperation(any(), any())).thenReturn(true);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with amount: {double}")
    public void makeTransferFromAccToAccWithAmount(String userName, int srcId, int desId, double amount) throws SQLException, OperationIsNotAllowedException {
        User  u = dao.findUserByName(userName);
        target.internalPayment(u,amount, " ",  srcId,desId);
    }

    @Then("account:{int} value:{double} pln")
    public void accountValuePln(int accId, double amount) throws SQLException {
        Account acc = dao.findAccountById(accId);
        assertEquals(amount,acc.getAmmount(),.001);
    }

}
