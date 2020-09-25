package me.jiniworld.demo.configs;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

@Aspect
@Component
public class AuthAspect {
		
	@Before("execution(public * me.jiniworld.demo.controllers.api.v1..*Controller.*(..)) ")
	public void insertAdminLog(JoinPoint joinPoint) throws WeakKeyException, UnsupportedEncodingException {
		SecretKey key = Keys.hmacShaKeyFor("test123test123test123test1232323gfgfg434343f".getBytes("UTF-8"));
		String target = joinPoint.getSignature().toString();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();		
		
		
		String authorization = request.getHeader("Authorization");
		if(StringUtils.isNotBlank(authorization)){ 
			if(authorization.indexOf("Basic") >= 0) {
				authorization = authorization.replaceAll("^Basic( )*", "");
				System.out.println("Authorization >> " + authorization);
				try {
					System.out.println("Authorization(decode) >> " + new String(Base64.getDecoder().decode(authorization), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else if(authorization.indexOf("Bearer") >= 0) {
				authorization = authorization.replaceAll("^Bearer( )*", "");
				Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key)
                .build()
                .parseClaimsJws(authorization);
				System.out.println("zzzzzzzzz");
//				String header = authorization.indexOf(".")
			}
		}
		System.out.println("target >> " + target);
//		target.substring(target.lastIndexOf(".") + 1).contains("modify")
	}
}
