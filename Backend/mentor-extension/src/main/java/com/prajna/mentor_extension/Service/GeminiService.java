package com.prajna.mentor_extension.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prajna.mentor_extension.Exchanges.GeminiRequestBody;

@Service
public class GeminiService {

    private static String SYSTEM_INSTRUCTION = "You are Neko the cat respond like one";
    private static String PRE_FORMAT_PROMPT = "Write a story about a magic backpack.";
    private static String RESPONSE_MIMETYPE = "application/json";
    private static String RESPONSE_SCHEMA_STRING = "{  \"type\": \"object\",  \"properties\": {    \"status\": {      \"type\": \"object\",      \"properties\": {        \"noOfQueries\": {          \"type\": \"integer\"        },        \"inlineCompletions\": {          \"type\": \"integer\"        },        \"overallQuality\": {          \"type\": \"integer\"        }      },      \"required\": [        \"noOfQueries\",        \"inlineCompletions\",        \"overallQuality\"      ]    },    \"inlineSuggestion\": {      \"type\": \"array\",      \"items\": {        \"type\": \"object\",        \"properties\": {          \"lineNumber\": {            \"type\": \"integer\"          },          \"text\": {            \"type\": \"string\"          }        },        \"required\": [          \"lineNumber\",          \"text\"        ]      }    },    \"checkStyle\": {      \"type\": \"object\",      \"properties\": {        \"checkboxes\": {          \"type\": \"array\",          \"items\": {            \"type\": \"boolean\"          }        },        \"occuranceLineNumbers\": {          \"type\": \"array\",          \"items\": {            \"type\": \"array\",            \"items\": {              \"type\": \"integer\"            }          }        },        \"score\": {          \"type\": \"integer\"        }      },      \"required\": [        \"checkboxes\",        \"occuranceLineNumbers\",        \"score\"      ]    },    \"summary\": {      \"type\": \"object\",      \"properties\": {        \"strength\": {          \"type\": \"string\"        },        \"scopeForImprovement\": {          \"type\": \"string\"        }      },      \"required\": [        \"strength\",        \"scopeForImprovement\"      ]    }  },  \"required\": [    \"status\",    \"inlineSuggestion\",    \"checkStyle\",    \"summary\"  ]}";

    private static Double TEMPERATURE = 1.0;
    private static Integer TOP_K = 2;
    private static Integer MAX_OUTPUT_TOKENS = 10;

    // @Value("${geminiapikey}")
    private String geminiKey="APIKEY";
    private RestTemplate restTemplate = new RestTemplate();
    private String URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";

    public ResponseEntity<String> callGemini(String input) throws JsonProcessingException {

        String url = String.format(URL_TEMPLATE, geminiKey);
        String prompt = PRE_FORMAT_PROMPT + "\n\n" + input;

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

    // /*
    public static void main(String[] args) throws JsonProcessingException {

        GeminiService obj = new GeminiService();
        var response = obj.callGemini("");
        System.out.println(response);
    }
    // */
}
