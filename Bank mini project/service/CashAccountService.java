package service;

import java.math.BigDecimal;

import pojo.CashAccount;
import repository.TradeAccountRepository;

public class CashAccountService implements TradeAccountService {

    private TradeAccountRepository repository;

    @Override
    public void deposit(String id, BigDecimal amount) {
        CashAccount account = retrieveCashAccount(id);
        account.setCashBalance(account.getCashBalance().add(amount));
        updateCashAccount(account);
    }

    @Override
    public void withdraw(String id, BigDecimal amount) {
        CashAccount account = retrieveTradeAccount(id);
        account.setCashBalance(account.getCashBalance().subtract(amount));
        updateTradeAccount(account);
    }

    public CashAccountService(TradeAccountRepository repository) {
        this.repository = repository;
    }

    public void createCashAccount(CashAccount tradeAccount) {
        this.repository.createTradeAccount(tradeAccount);
    }

    public CashAccount retrieveCashAccount(String id) {
        return (CashAccount) this.repository.retrieveTradeAccount(id);
    }

    public void updateCashAccount(CashAccount tradeAccount) {
        this.repository.updateTradeAccount(tradeAccount);
    }

    public void deleteCashAccount(String id) {
        this.repository.deleteTradeAccount(id);
    }

}
