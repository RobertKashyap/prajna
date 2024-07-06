package com.prajna.mentor_extension.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prajna.mentor_extension.DTO.FileFormat;
import com.prajna.mentor_extension.Exchanges.Dashboard;
import com.prajna.mentor_extension.Exchanges.GeminiRequestBody;

@Service
public class GeminiService {

    private static String SYSTEM_INSTRUCTION = "You are machine that takes in input:user code content and solution code content labelled by \"user code\" and \"solution code\",your outputJsonStructure: "+"{\"status\":{\"noOfQueries\":\"fill this with integer 1\",\"inlineCompletions\":\"fill this with integer sizeOf array inlineSuggestion\",\"overallQuality\":\"fill this with integer Score*10\"},\"inlineSuggestion\":[{\"lineNumber\":\"integer, Replace with line number for the suggestion in the user code, count line number = 1 from package, +1 for every newline\",\"text\":\"String, fill with the code suggestion for the user code at that line with comparision to solution code, only give an idea of what can be done to remove issues or errors, dont provide solution code\"},// Add all suggesstions as array],\"checkStyle\":{\"checkboxes\":[null,(Code Reusability),null,(hiding Secrets and private variables),null,(file code Structure),null,(Standard Naming Conventions),null,(Industrial Comments Structure),null,(Cohesion of well defined functionality),null,(Loose Coupling),null,(Simplicity of code),null,(Encapsulation),null,(No Dead or unused Code)],\"occuranceLineNumbers\":[[],[],[],[],[],[],[],[],[],[]],\"Score\":\"integer score = number of true in \\\"checkboxes\\\"\"},\"summary\":{\"strength\":null,// Replace with identified code strengths,give in bullet points\",\"scopeForImprovement\":null // Replace with identified areas for improvement, give in bullet points\"}}";
    private static String PRE_FORMAT_PROMPT = "user code:\n %s \n solution code:\n %s";
    private static String RESPONSE_MIMETYPE = "application/json";
    private static String RESPONSE_SCHEMA_STRING = "{  \"type\": \"object\",  \"properties\": {    \"status\": {      \"type\": \"object\",      \"properties\": {        \"noOfQueries\": {          \"type\": \"integer\"        },        \"inlineCompletions\": {          \"type\": \"integer\"        },        \"overallQuality\": {          \"type\": \"integer\"        }      },      \"required\": [        \"noOfQueries\",        \"inlineCompletions\",        \"overallQuality\"      ]    },    \"inlineSuggestion\": {      \"type\": \"array\",      \"items\": {        \"type\": \"object\",        \"properties\": {          \"lineNumber\": {            \"type\": \"integer\"          },          \"text\": {            \"type\": \"string\"          }        },        \"required\": [          \"lineNumber\",          \"text\"        ]      }    },    \"checkStyle\": {      \"type\": \"object\",      \"properties\": {        \"checkboxes\": {          \"type\": \"array\",          \"items\": {            \"type\": \"boolean\"          }        },        \"occuranceLineNumbers\": {          \"type\": \"array\",          \"items\": {            \"type\": \"array\",            \"items\": {              \"type\": \"integer\"            }          }        },        \"score\": {          \"type\": \"integer\"        }      },      \"required\": [        \"checkboxes\",        \"occuranceLineNumbers\",        \"score\"      ]    },    \"summary\": {      \"type\": \"object\",      \"properties\": {        \"strength\": {          \"type\": \"string\"        },        \"scopeForImprovement\": {          \"type\": \"string\"        }      },      \"required\": [        \"strength\",        \"scopeForImprovement\"      ]    }  },  \"required\": [    \"status\",    \"inlineSuggestion\",    \"checkStyle\",    \"summary\"  ]}";

    private static Double TEMPERATURE = 1.0;
    private static Integer TOP_K = 2;
    private static Integer MAX_OUTPUT_TOKENS = 10;

    private RestTemplate restTemplate = new RestTemplate();
    private String URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";

    private ResponseEntity<String> callGemini(String input, String geminiApiKey) throws JsonProcessingException {

        String url = String.format(URL_TEMPLATE, geminiApiKey);
        String prompt = input;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        GeminiRequestBody requestBody = new GeminiRequestBody();
        requestBody.system_instruction.parts.text = SYSTEM_INSTRUCTION;
        requestBody.contents.get(0).parts.get(0).text = prompt;
        requestBody.safetySettings.get(0).category = "HARM_CATEGORY_DANGEROUS_CONTENT";
        requestBody.safetySettings.get(0).threshold = "BLOCK_ONLY_HIGH";
        requestBody.generationConfig.responseMimeType = RESPONSE_MIMETYPE;

        ObjectMapper om = new ObjectMapper();
        Object response_schema_object = om.readValue(RESPONSE_SCHEMA_STRING, Object.class);
        requestBody.generationConfig.responseSchema = response_schema_object;

        requestBody.generationConfig.temperature = TEMPERATURE;
        requestBody.generationConfig.topK = TOP_K;
        requestBody.generationConfig.maxOutputTokens = MAX_OUTPUT_TOKENS;

        String requestJson = om.writeValueAsString(requestBody);

        System.out.println(requestJson);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response;
    }

    public ResponseEntity<Dashboard> getDashboard(FileFormat fileFormat, String geminiApiKey) throws IOException {
        List<File> matchingFiles = FileSearcher.search(fileFormat.getFileName());
        String SolutionData = "";
        if (matchingFiles.size() > 0) {
            if (matchingFiles.size() == 1) {
                SolutionData = Files.readString(Paths.get(matchingFiles.get(0).getPath()));
            } else {
                for (File file : matchingFiles) {
                    if (file.toString().contains(fileFormat.getProjectName().toString())) {
                        SolutionData = Files.readString(Paths.get(file.getPath()));
                        break;

                    }
                }
            }
        }


        String inputForCall=String.format(PRE_FORMAT_PROMPT,fileFormat.getContent(),SolutionData);
        ResponseEntity<String> response = callGemini(inputForCall, geminiApiKey);
        if(response.getStatusCode().is2xxSuccessful()){
            ObjectMapper om = new ObjectMapper();
            Dashboard dashboard = om.readValue(response.getBody(), Dashboard.class);
            return new ResponseEntity<Dashboard>(dashboard, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * public static void main(String[] args) throws JsonProcessingException {
     * 
     * GeminiService obj = new GeminiService();
     * var response = obj.callGemini("");
     * System.out.println(response);
     * }
     * //
     */
}
