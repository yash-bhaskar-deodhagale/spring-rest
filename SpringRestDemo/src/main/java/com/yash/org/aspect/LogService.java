package com.yash.org.aspect;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogService {

	@Pointcut(value = "execution(* com.yash.org.controller.EmployeeController.*(..))")
	public void controllerMethod() {

	}

	@Pointcut(value = "execution(* com.yash.org.service.EmployeeServiceImpl.*(..))")
	public void serviceMethod() {

	}

	@Pointcut(value = "execution(* com.yash.org.dao.EmployeeDaoImpl.*(..))")
	public void daoMethod() {

	}
	
	@Pointcut(value = "execution(* com.yash.org.exception.EmployeeExceptionHandler.*(..))")
	public void exceptionMethod() {

	}
	

	@Before("controllerMethod() or serviceMethod() or daoMethod()")
	public void logBefore(JoinPoint joinPoint) {
		final Logger LOGGER = Logger.getLogger(joinPoint.getTarget().getClass().getName());
		LOGGER.info("Starting " + joinPoint.getSignature().getName() + " method");
	}

	@After("controllerMethod() or serviceMethod() or daoMethod()")
	public void logAfter(JoinPoint joinPoint) {
		final Logger LOGGER = Logger.getLogger(joinPoint.getTarget().getClass().getName());
		LOGGER.info("Exiting " + joinPoint.getSignature().getName() + " method");
	}

	@Around("controllerMethod()")
	public Object logReturning(ProceedingJoinPoint jp) throws Throwable {
		final Logger LOGGER = Logger.getLogger(jp.getTarget().getClass().getName());
		long start = System.nanoTime();
		Object[] arguments = jp.getArgs();
		
		String arg="";
		if(arguments.length>0){
			arg=Stream.of(arguments).map(String::valueOf) 
                    .collect(Collectors.joining()); 
		}
		LOGGER.info("Parameters" + arg);
		Object retVal="";
		try {
			retVal = jp.proceed();
		}finally {
			System.out.println("Error Occured");	
			long end = System.nanoTime();
			LOGGER.info("Execution Time:" + TimeUnit.NANOSECONDS.toMillis(end - start) + "ms");
		}
		return retVal;

	}
	
	@AfterReturning( value="controllerMethod()",returning="retVal")
	public void logShowResult(JoinPoint jp,Object retVal) throws Throwable{
		final Logger LOGGER = Logger.getLogger(jp.getTarget().getClass().getName());
		LOGGER.info(" Response= " + retVal);
	}
	
	@AfterReturning( value="exceptionMethod()",returning="retVal")
	public void logThrowing(JoinPoint jp,Object retVal) throws Throwable{
		final Logger LOGGER = Logger.getLogger(jp.getTarget().getClass().getName());
		LOGGER.info(" Response= " + retVal);
		
	}
}
