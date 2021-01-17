package com.arif.wssecurity.server;

import java.util.HashMap;
import java.util.Map;

public class WsSecurityUtil {
    private static final String WSSEXT_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private static final String WSUTIL_NAMESPACE  = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";

    public static Map<String, Object> getOutProperties(){
        Map<String, Object> props = new HashMap<>();

        props.put("signaturePropFile", "props/EncryptionDecryption.properties");
        props.put("signatureKeyIdentifier", "DirectReference");
        props.put("signatureParts", "{Element}{" + WSUTIL_NAMESPACE + "}Timestamp;" + "{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
        props.put("signatureAlgorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha1");

        props.put("encryptionUser", "arifclient");
        props.put("encryptionPropFile", "props/SignatureVerification.properties");
        props.put("encryptionKeyIdentifier", "IssuerSerial");
        props.put("encryptionParts", "{Element}{" + WSSEXT_NAMESPACE + "}UsernameToken;" + "{Content}{http://schemas.xmlsoap.org/soap/envelope/}Body");
        props.put("encryptionKeyTransportAlgorithm", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p");

        props.put("passwordCallbackClass", "com.arif.wssecurity.server.PasswordCallbackHandlerImpl");

        props.put("user", "Arif");
        props.put("signatureUser", "arifserver");
        props.put("action", "UsernameToken Timestamp Signature Encrypt");
        props.put("passwordType", "PasswordText");

        return props;
    }


    public static Map<String, Object> getInProperties(){

        Map<String, Object> props = new HashMap<>();

        props.put("decryptionPropFile", "props/EncryptionDecryption.properties");
        props.put("encryptionKeyIdentifier", "IssuerSerial");
        props.put("encryptionKeyTransportAlgorithm", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p");

        props.put("signaturePropFile", "props/SignatureVerification.properties");
        props.put("signatureKeyIdentifier", "DirectReference");
        props.put("signatureAlgorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha1");

        props.put("action", "UsernameToken Timestamp Signature Encrypt");
        props.put("passwordType", "PasswordDigest");
        props.put("passwordCallbackClass", "com.arif.wssecurity.server.PasswordCallbackHandlerImpl");

        return props;
    }



}
