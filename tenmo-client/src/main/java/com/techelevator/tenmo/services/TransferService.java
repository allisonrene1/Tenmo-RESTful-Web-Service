package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();


    public Transfer[] listingAllTransfersById(){
        Transfer[] transfers = null;
        try{
            transfers = restTemplate.getForObject(API_BASE_URL + "transfers", Transfer[].class);
        }catch(RestClientException e){
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

}
