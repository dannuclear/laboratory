package ru.bisoft.laboratory.service.aspect;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import ru.bisoft.laboratory.guide.utils.GuideUtils;

@Aspect
@Component
public class PageableAspect {

	@Around("execution(* ru.bisoft.laboratory.service.*.*(.., org.springframework.data.domain.Pageable, ..))")
	public Object pageableCall(final ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Pageable) {
				args[i] = Optional.ofNullable(args[i]).orElse(GuideUtils.DEFAULT_PAGE);
			}
		}
		return pjp.proceed(args);
	}
}
