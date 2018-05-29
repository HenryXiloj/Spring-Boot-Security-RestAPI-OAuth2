package com.hxiloj.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hxiloj.model.AuthTokenInfo;
import com.hxiloj.model.User;
 
  
public class RestClient {
  
    public static final String REST_SERVICE_URI = "http://localhost:8080/Spring-Boot-Security-RestAPI-OAuth2";
     
    public static final String AUTH_SERVER_URI = "http://localhost:8080/Spring-Boot-Security-RestAPI-OAuth2/oauth/token";
     
    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=henry&password=abc123";
     
    public static final String QPM_ACCESS_TOKEN = "?access_token=";
 
    /*
     * HTTP Headers.
     */
    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
     
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials(){
        String plainClientCredentials="my-trusted-client:secret"; //clientId + secret
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
         
        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }    
     
    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({ "unchecked"})
    private static AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
         
        if(map!=null){
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String)map.get("access_token"));
            tokenInfo.setToken_type((String)map.get("token_type"));
            tokenInfo.setRefresh_token((String)map.get("refresh_token"));
            tokenInfo.setExpires_in((int)map.get("expires_in"));
            tokenInfo.setScope((String)map.get("scope"));
            System.out.println(tokenInfo);
           
        }else{
            System.out.println("No user exist----------");
             
        }
        return tokenInfo;
    }
     
    /*
     * GET request to get list of all users.
     */
    private static void findAllUsers(AuthTokenInfo tokenInfo){
 
        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        
 
        ResponseEntity<List<User>> response =
               restTemplate.exchange(
                					REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
                					HttpMethod.GET, 
                					request, 
                					new ParameterizedTypeReference<List<User>>() {});
        
        List<User> listUser = response.getBody();
        
        listUser.forEach(item -> {
        	
        	System.out.println("id "+item.getId() + " name  "+ item.getName()+" email "+item.getEmail() + " age "+item.getAge());
        	
        });
    }
      
  
    public static void main(String args[]){
        AuthTokenInfo tokenInfo = sendTokenRequest();
        findAllUsers(tokenInfo);
         
    }
}
