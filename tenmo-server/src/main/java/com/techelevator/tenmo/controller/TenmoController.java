package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;


    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getBalance(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());

        int user_id = user.getId();
        return accountDao.getBalanceById(user_id);


    }


}
