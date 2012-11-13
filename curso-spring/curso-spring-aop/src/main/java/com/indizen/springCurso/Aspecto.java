package com.indizen.springCurso;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class Aspecto {
	private static final Logger LOGGER = Logger.getLogger(Aspecto.class);
	
	@Before("execution(* com.indizen.springCurso.UserService*.before*(..))")
	public void beforeMethod(JoinPoint joinPoint) {
		log(joinPoint);
	}
	
	@After("execution(* com.indizen.springCurso.UserService*.after*(..))")
	public void afertMethod(JoinPoint joinPoint) {
		log(joinPoint);
	}
	
	@AfterReturning(pointcut="execution(* com.indizen.springCurso.UserService*.afterRunning*(..))",returning= "result")
	public void afertRunningMethod(JoinPoint joinPoint, Object result) {
		log(joinPoint);
		LOGGER.info("\tResultado del metodo: "+result.toString());
	}
	
	@AfterThrowing(pointcut="execution(* com.indizen.springCurso.UserService*.throw*(..)))", throwing = "e")
	public void throwinMethod(JoinPoint joinPoint, Throwable e)
	{
		log(joinPoint);
		LOGGER.error("\tUna excepción ha sido lanzado en el"+ joinPoint.getSignature().getName() + "()");
		LOGGER.error("\tCausa :"+e.getCause());
	}
	
	@Around("execution(* com.indizen.springCurso.UserServiceImpl.around*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		try {
			log(pjp);
			LOGGER.info("\tValor antes: "+pjp.getArgs()[0]);
			return pjp.proceed(new Object[] {"Lo siento pero te cambio el valor"});
		} finally {
			stopWatch.stop();
			StringBuffer trazaSalida = new StringBuffer();
			trazaSalida.append("\t| ");
			trazaSalida.append(pjp.getTarget().getClass().getName());
			trazaSalida.append(".");
			trazaSalida.append(pjp.getSignature().getName());
			trazaSalida.append("  |  ");
			trazaSalida.append(stopWatch.getTotalTimeMillis());
			trazaSalida.append("  | ");
			LOGGER.info(trazaSalida.toString());
		}
	}

	private void log(JoinPoint pjp){
		LOGGER.info("\tTipo de Join point : " + pjp.getKind());
		LOGGER.info("\tArgumentos : " + Arrays.toString(pjp.getArgs()));
		LOGGER.info("\tClase : " + pjp.getTarget().getClass().getName());
	}

}
