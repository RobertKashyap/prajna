package com.prajna.mentor_extension.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prajna.mentor_extension.Exchanges.GeminiResponseBody;
import com.prajna.mentor_extension.Service.GeminiService;

@RestController
public class GeminiController {

    @Autowired
    private GeminiService geminiService;


    @GetMapping("/getAiResponse")
    public ResponseEntity<GeminiResponseBody> getAiResponse(@RequestBody String input) throws JsonProcessingException {
        ResponseEntity<String> serviceResponse=geminiService.callGemini(input,"APIKEY");
        if (serviceResponse.getStatusCode().is2xxSuccessful()) {
            ObjectMapper om=new ObjectMapper();
            GeminiResponseBody responseBody=om.readValue(serviceResponse.getBody(), GeminiResponseBody.class);
            return new ResponseEntity<GeminiResponseBody>(responseBody, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
