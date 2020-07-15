package ru.bisoft.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ru.bisoft.validator.INNValidator;

@Documented
@Constraint(validatedBy = INNValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface INNValid {

	String message() default "ИНН не верен";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
