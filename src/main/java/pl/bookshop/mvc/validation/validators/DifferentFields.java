package pl.bookshop.mvc.validation.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation for validating two properties of entity. Validation pass if two fields of class are different, in other case validation fail.
 * For correct working of this annotation arguments {@code firstField} and {@code secondField} should be specified. Type of these arguments
 * should be {@code String}.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentFieldsValidator.class)
@Documented
public @interface DifferentFields {
	public String message() default "{pl.bookshop.mvc.validation.DifferentPassword.message}";
	public Class<?>[] groups() default{};
	public abstract Class<? extends Payload>[] payload() default{};
	
	String firstField();
	String secondField();
}