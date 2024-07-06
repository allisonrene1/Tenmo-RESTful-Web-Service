package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;

public interface AccountDao {

    Account getBalanceById(int user_id);

    BigDecimal getbalance(User user);



}
