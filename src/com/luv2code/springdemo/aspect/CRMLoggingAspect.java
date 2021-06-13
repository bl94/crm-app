package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	//setup Logger
	private Logger myLogger=Logger.getLogger(getClass().getName());
	
	//setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	public void forControllerPackage() {}
	
	//do the same service and dao 
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	public void forDaoPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("forDaoPackage() || forServicePackage() || forControllerPackage()")
	private void forAllPackage() {}
	
	//add @Before advice
	@Before("forAllPackage()")
	public void before(JoinPoint theJoinPoint) {
		// display the calling method
		myLogger.info("===>Calling method "+ theJoinPoint.getSignature().toShortString());
		
		// display args to the method
		Object[] args = theJoinPoint.getArgs(); 
		myLogger.info(args);
		 //loop through args
		for (Object object : args) {
			myLogger.info("argument "+ object);
		}
		
	}
	//add @AfterReturning advice
	@AfterReturning(pointcut="forAllPackage()",
					returning="theResult")
	public void afterReturning(JoinPoint theJoinPoint,Object theResult)
	{
		// display the calling method
		myLogger.info("===>Calling @AfterReturning method "+ theJoinPoint.getSignature().toShortString());	
		
		// display args to the method
		myLogger.info("Result "+theResult);
	}
}
