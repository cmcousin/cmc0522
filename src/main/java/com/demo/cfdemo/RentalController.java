package com.demo.cfdemo;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentalController {

    @Autowired
    RentalService service;

    Gson gson = new Gson();

    @PostMapping (value = "/rental", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submitRental(@RequestBody RentalAgreementRequestDTO request){
        try{
            RentalAgreementResponseDTO result = service.processRental(request);
            return new ResponseEntity(gson.toJson(result), HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(gson.toJson(e.getMessage()));
        }
    }

    @GetMapping (value = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tool>> getInventory(){
        return new ResponseEntity(CfDemoApplication.inventory.values(), HttpStatus.OK);
    }
}
