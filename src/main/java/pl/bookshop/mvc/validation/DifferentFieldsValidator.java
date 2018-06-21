package pl.bookshop.mvc.validation;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

public class DifferentFieldsValidator implements ConstraintValidator<DifferentFields, Object> {
	private String firstField;
	private String secondField;
	
	@Override
	public void initialize(DifferentFields constraintAnnotation) {
		String firstField = constraintAnnotation.firstField();
		String secondField = constraintAnnotation.secondField();
		
		if (StringUtils.isAnyBlank(firstField, secondField)) {
			throw new IllegalArgumentException("Arguments firstField and secondField must be specified");
		}
		
		this.firstField = firstField;
		this.secondField = secondField;
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		if (object == null) {
			return true;
		}
		
		String firstField;
		String secondField;
		try {
			firstField = BeanUtils.getProperty(object, this.firstField);
			secondField = BeanUtils.getProperty(object, this.secondField);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
			throw new RuntimeException(
					"Cannot find property " + this.firstField + " or property " + this.secondField + " in validating object", exception);
		}
		
		if (firstField.equals(secondField)) {
			return false;
		}
		return true;
	}
}
