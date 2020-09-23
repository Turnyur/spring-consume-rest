package com.turnyur.springconsumerest.service;

import com.turnyur.springconsumerest.model.CovidCountryTotal;
import com.turnyur.springconsumerest.model.CovidTotal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RestClient {

    @Value("${rapidapi.covid.url}")
    String covidUrl;
    @Value("${rapidapi.covidbycountry.url}")
    String covidUrlByCountry;
    @Value("${rapidapi.key.name}")
    String apiKeyName;
    @Value("${rapidapi.key.value}")
    String apiKeyValue;
    @Value("${rapidapi.host.name}")
    String hostName;
    @Value("${rapidapi.host.covid.value}")
    String hostValue;

    RestTemplate restTemplate;
    public RestClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }
    public List<CovidTotal> getTotals() {
        //CovidTotal total = null;
        List<CovidTotal> total = new ArrayList<CovidTotal>();
        try {
            URI uri;
            uri = new URI(covidUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.set(apiKeyName, apiKeyValue);
            headers.set(hostName, hostValue);
            //String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
            //headers.add("Authorization", authorizationHeader);
           /*
           This section is used in adding body param to POST requests
           User user = new User();
            user.setName("Sample User");
            user.setUsername("user1");
            user.setPassword("pass123");
            //request entity is created with request body and headers
            HttpEntity<User> requestEntity = new HttpEntity<>(user, requestHeaders);*/

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<String> request = new HttpEntity<String>(headers);
            //JSON to POJO mapping in ResponseEntity automatically done by Jackson JSON to POJO library
            ResponseEntity<CovidTotal[]> totalEntity = restTemplate.exchange(uri, HttpMethod.GET, request,
                    CovidTotal[].class);
            //total = totalEntity.getBody()[0];
            if (totalEntity.getStatusCode() == HttpStatus.OK) {
                for (CovidTotal covidTotal:totalEntity.getBody()) {
                    total.add(covidTotal);
                }

            } else {
                total = null;
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return total;
    }

    public CovidCountryTotal getCovidTotalByCountry(String country){
        //make headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set(apiKeyName, apiKeyValue);
        requestHeaders.set(hostName, hostValue);
        //make request body if any
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        //make a http request
        ResponseEntity<CovidCountryTotal[]> responseEntity = restTemplate.exchange(covidUrlByCountry+country,HttpMethod.GET,requestEntity, CovidCountryTotal[].class);

        return responseEntity.getBody()[0];
    }
}
