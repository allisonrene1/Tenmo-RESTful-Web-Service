package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TransferAccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Transfer[] listingAllTransfersById(){
        Transfer[] transfers = null;
        try{
            transfers = restTemplate.getForObject(API_BASE_URL + "transfers", Transfer[].class);
        }catch(RestClientException e){
            System.out.println(e.getMessage());
        }
        return transfers;
    }

    public Account getAccountInformation() {
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "account",
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }
    public BigDecimal getBalance(){
        BigDecimal balance = null;
        try{
        ResponseEntity<BigDecimal> response =
                restTemplate.exchange(API_BASE_URL + "account",
                        HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
        balance = response.getBody();
    } catch (RestClientResponseException | ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
        return balance;
    }
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(API_BASE_URL + "users",
                            HttpMethod.GET, makeAuthEntity(), User[].class);
           User[] usersArray = response.getBody();
           if(usersArray != null){
               users = List.of(usersArray);
           }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }
    public Transfer postTransferRequest(Transfer transfer) {
        Transfer newTransfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfers",
                            HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
             newTransfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return newTransfer;
    }
    public User[] fetchUserArray(){
        User[] users = null;
        ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class);
        users = response.getBody();

        return users;
    }
    public List<Transfer> getAllTransfersFromUsers(){
        List<Transfer> transfers = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            Transfer[] transfersArray = response.getBody();
            if(transfersArray != null){
                transfers = Arrays.asList(transfersArray);
            }
        }
        catch (RestClientResponseException | ResourceAccessException e) {
            System.out.println(e.getMessage());
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }
    public Transfer getTransferById(int transferId){
        Transfer transfer = null;
        try{
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL +
                    "transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        }
        catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }



    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }


}
