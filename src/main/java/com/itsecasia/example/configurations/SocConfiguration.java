package com.itsecasia.example.configurations;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itsecasia.example.configurations.collections.UserCollections;

import io.fusionauth.jwks.domain.JSONWebKey;
import io.fusionauth.jwt.json.Mapper;
import kong.unirest.core.Unirest;

@Configuration
public class SocConfiguration {

	@Value("${soc.keycloak.certsUrl}")
	private String certsUrl;

	@Value("${soc.keycloak.cert.alg}")
	private String certAlgo;
	
	@Value("${use.dummy.pubkey}")
	private String dummyPubKey;

	@Bean
	public UserCollections users() {
		return new UserCollections();
	}

	@Bean
	public PublicKey keyCloackPubKey() throws InvalidKeyException, NoSuchAlgorithmException {

		if(Boolean.parseBoolean(dummyPubKey))	{
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			return keyPairGenerator.generateKeyPair().getPublic();
		}
		else	{
			String result = Unirest.get(certsUrl).asString().getBody();
			JsonObject json = JsonParser.parseString(result).getAsJsonObject();
			if (json.has("keys")) {
				for (JsonElement key : json.get("keys").getAsJsonArray()) {
					JsonObject jsonObject = key.getAsJsonObject();
					if (jsonObject.has("alg") && jsonObject.get("alg").getAsString().equalsIgnoreCase(certAlgo)) {
						return JSONWebKey
								.parse(Mapper.deserialize(jsonObject.toString().getBytes(UTF_8), JSONWebKey.class));
					}
				}
				throw new InvalidKeyException("Cannot find suitable Public Key");
			} else {
				throw new InvalidKeyException("Cannot get valid JWKS");
			}
		}
	}
}
