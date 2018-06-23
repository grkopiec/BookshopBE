package pl.bookshop.mvc.validation.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword {
	public String message() default "{pl.bookshop.mvc.validation.ValidPassword.message}";
	public Class<?>[] groups() default{};
	public abstract Class<? extends Payload>[] payload() default{};
}
