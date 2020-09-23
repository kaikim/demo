package me.jiniworld.demo.controllers.api;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
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
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	@Operation(description = "test123test123test123test1232323gfgfg434343f")
	@PostMapping(value = "", produces = "text/plain")
	public String createToken(@RequestBody Token token) throws InvalidKeyException, UnsupportedEncodingException {
		String accessToken = token.getAccessToken(), secretKey = token.getSecretKey();
//        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
		
//        String jwt = Jwts.builder()
//                .setSubject(String.valueOf(account.getId()))
//                .setAudience(account.getEmail())
//                .setExpiration(new Date())
//                .signWith(key)
//                .compact();
        
        String jwt = Jwts.builder()
                .claim("access_key", accessToken)
                .claim("nonce", new Date().getTime())
                .signWith(key)
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
