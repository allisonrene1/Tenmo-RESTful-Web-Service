package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

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
    public Account getBalance(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());

        int user_id = user.getId();
        return accountDao.getBalanceById(user_id);

    }
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> fetchAllUsers(){
        return userDao.getUsers();
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer createAndSendTransferRequest(@RequestBody Transfer transfer){
         return transferDao.sendTransfers(transfer);
     //   transferDao.
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersFromUsers (Principal principal){

        User user = userDao.getUserByUsername(principal.getName());
        return transferDao.getTransfersByUserId(user.getId());
    }
    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferDetails(@PathVariable int transferId){
        return transferDao.getTransferById(transferId);
    }



}
