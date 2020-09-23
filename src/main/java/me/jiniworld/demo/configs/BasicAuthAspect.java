package me.jiniworld.demo.configs;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class BasicAuthAspect {
	
	@Before("execution(public * me.jiniworld.demo.controllers.api.v1..*Controller.*(..)) ")
	public void insertAdminLog(JoinPoint joinPoint) {
		String target = joinPoint.getSignature().toString();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();		
		
		
		String authorization = request.getHeader("Authorization");
		if(StringUtils.isNotBlank(authorization))
			authorization = authorization.replaceAll("^Basic( )*", "");
		System.out.println("Authorization >> " + authorization);
		try {
			System.out.println("Authorization(decode) >> " + new String(Base64.getDecoder().decode(authorization), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("target >> " + target);
//		target.substring(target.lastIndexOf(".") + 1).contains("modify")
	}
}
