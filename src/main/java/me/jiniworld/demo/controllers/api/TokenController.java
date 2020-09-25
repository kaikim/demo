package me.jiniworld.demo.controllers.api;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Tag(name = "token", description = "Bearer Token API")
@RequestMapping(value = "${demo.api}/token")
@RequiredArgsConstructor
@RestController
public class TokenController {
	
	@Operation(description = "test123test123test123test1232323gfgfg434343f")
	@PostMapping(value = "", produces = "text/plain")
	public String createToken(@RequestBody Token token) throws InvalidKeyException, UnsupportedEncodingException {
		String accessToken = token.getAccessToken(), secretKey = token.getSecretKey();
        SecretKey key = Keys.hmacShaKeyFor("test123test123test123test1232323gfgfg434343f".getBytes("UTF-8"));
        
		Map<String, Object> header = new HashMap<>(), payloads = new HashMap<>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		
		payloads.put("access_key", accessToken);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 30);
		payloads.put("exp", cal.getTime());
		
        String jwt = Jwts.builder()
        		.setHeader(header)
        		.setClaims(payloads)
//        		.setSubject(sub)
//                .claim("access_key", accessToken)
//                .claim("nonce", new Date().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
//                .signWith(key)
//                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))             
                .compact();
        return jwt;
    }
	
	@Getter @Setter
	public static class Token {
		private String accessToken;
		private String secretKey;
		
	}
}
