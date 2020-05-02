package com.arif;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;

/**
 *
 * @author arif
 */
public class AwsCognitoOAuthHelper {
    
    private static String client_id = AppContext.getInstance().getCLIENT_ID();
    private static String client_secret = AppContext.getInstance().getCLIENT_SECRET();
    private static String jwks_url = AppContext.getInstance().getJWKS_URL();
    
    public static JwtClaims getJWTClaims(String jwt) throws Exception{
        HttpsJwks httpsJkws = new HttpsJwks(jwks_url);
        HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(httpsJkws);
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setVerificationKeyResolver(httpsJwksKeyResolver)
                .setRequireExpirationTime()
                .setMaxFutureValidityInMinutes(300)
                .setRequireSubject()
                .setExpectedAudience(client_id)
                .build(); 

        JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
        System.out.println("JWT Valid. " + jwtClaims);
        return jwtClaims;
    }
    
    public static Map<String, String> getCognitoTokens(String code) throws Exception{
        HttpPost method = new HttpPost(new URI(AppContext.getInstance().getCOGNITO_TRIPBACK_URL()));

        String encoding = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes("utf-8"));
        method.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("client_id", client_id));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("redirect_uri", AppContext.getInstance().getCALLBACK_URL()));
        method.setEntity(new UrlEncodedFormEntity(params));            

        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse tripBackResponse = httpClient.execute(method);
        
        HttpEntity entity = tripBackResponse.getEntity();
        String strHttpEntity = EntityUtils.toString(entity);
        
        return parseResponseWithTokens(strHttpEntity);
    }
    
    public static Map<String, String> parseResponseWithTokens(String strCognito){
        strCognito = strCognito.replace("{", "").replace("}", "");
        
        Map<String, String> map = new HashMap<String, String>();
        String[] test1 = strCognito.split(",");

        for (String s : test1) {
            String[] t = s.split(":");
            map.put(t[0].replace("\"", ""), t[1].replace("\"", ""));
        }

        return map;
    }
    
    
}
