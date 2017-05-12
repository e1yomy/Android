package com.example.elyo_.sensores;

import android.util.Base64;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Seguridad {

    public static KeyPair generarLlaves(int longitud) throws Exception{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(longitud);
        return keyGen.generateKeyPair();
    }

    public static byte[] testBytes(byte[] bytes)
    {
        for (int i=0; i<bytes.length; i++)
            bytes[i] = (byte)(bytes[i]);
        return bytes;
    }

    public static String publicKeyToXml(PublicKey publicKey) throws Exception
    {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec ks = kf.getKeySpec(publicKey, RSAPublicKeySpec.class);
        BigInteger modulus = ks.getModulus();
        BigInteger exponent = ks.getPublicExponent();
        byte[] modByte = modulus.toByteArray();
        byte[] expByte = exponent.toByteArray();
        modByte = testBytes(modByte);
        expByte = testBytes(expByte);
        String encodedModulus = new String(Base64.encode(modByte, Base64.NO_WRAP), "UTF-8");// Base64.encodeToString(modByte, Base64.NO_WRAP);
        String encodedExponent = Base64.encodeToString(expByte, Base64.NO_WRAP);
        String publicKeyAsXML = "<RSAKeyValue>" +
                "<Modulus>" + encodedModulus + "</Modulus>" +
                "<Exponent>" + encodedExponent + "</Exponent>" +
                "</RSAKeyValue>";
        return publicKeyAsXML;
    }

    public static PublicKey xmlToPublicKey(String xml) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("RSAKeyValue");
        String sModulus = nList.item(0).getNodeValue();
        String sExponent = nList.item(1).getNodeValue();
        BigInteger modulus = new BigInteger(sModulus, 16);
        BigInteger pubExp = new BigInteger(sExponent, 16);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
        return keyFactory.generatePublic(pubKeySpec);
    }

    public static byte[] encriptarRSA(String cadena, PublicKey key) throws Exception{
        byte[] bytes = cadena.getBytes("UTF8");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(bytes);
    }

    public static String desencriptarRSA(byte[] bytes, PrivateKey key) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(bytes),"UTF8");
    }

    public static String md5(String cadena) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("md5");
        digest.update(cadena.getBytes());
        byte[] bytes = digest.digest();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString().toLowerCase();
    }


    public static void main (String[] args) throws Exception {
        String cadena = "Esta es una prueba de un texto encriptado con el algoritmo de RSA" +
                " que utiliza llaves publica y privada";
                //+" pero dependiendo del tamaño de la llave generada es el tamaño maximo del texto que se puede encriptar";
        KeyPair llaves = generarLlaves(1024);
        System.out.println("Cadena a encriptar:   "+cadena);
        String xmlRSALlavePublic = publicKeyToXml(llaves.getPublic());
        PublicKey llavePublica = xmlToPublicKey(xmlRSALlavePublic);
        byte[] bytes = encriptarRSA(cadena, llaves.getPublic());
        System.out.println("Cadena encriptada:    "+bytes);
        bytes = encriptarRSA(cadena, llavePublica);
        System.out.println("Cadena encriptada:    "+bytes);

        cadena = desencriptarRSA(bytes,llaves.getPrivate());
        System.out.println("Cadena desencriptada: "+cadena);
    }


}
