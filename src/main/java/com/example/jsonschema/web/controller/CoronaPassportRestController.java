package com.example.jsonschema.web.controller;

import com.example.jsonschema.web.model.CoronaPassportRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Balaji Varadharajan
 * Date: 9/3/2021
 * Time: 3:06 PM
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@RequestMapping("/api/corona")
@Slf4j
public class CoronaPassportRestController {

    @PostMapping("/novalidation")
    public CoronaPassportRequest createPassport(@RequestBody CoronaPassportRequest coronaPassportRequest){
        log.info("Request:{}", coronaPassportRequest);
        return coronaPassportRequest;
    }

    @PostMapping("/withvalidation")
    public CoronaPassportRequest createPassportValidation(@RequestBody String requestStr) throws JsonProcessingException {
        log.info("Request JSON String: {}", requestStr);
        InputStream schemaAsStream = CoronaPassportRestController.class.getClassLoader().getResourceAsStream("model/coronapassport.schema.json");
        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);
        ObjectMapper om = new ObjectMapper();
        //om.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        JsonNode jsonNode = om.readTree(requestStr);
        Set<ValidationMessage> errors = schema.validate(jsonNode);
        String errorsCombined = "";
        for(ValidationMessage validationMessage: errors){
            log.error("Validation Error:{}", validationMessage);
            errorsCombined += validationMessage.toString() + "\n";
        }

        if(errors.size() > 0){
            throw new RuntimeException("Please fix your Json: "+ errorsCombined);
        }
        CoronaPassportRequest coronaPassportRequest = om.readValue(requestStr, CoronaPassportRequest.class);
        log.info("Return this request:{}", coronaPassportRequest);
        return coronaPassportRequest;
    }
}
