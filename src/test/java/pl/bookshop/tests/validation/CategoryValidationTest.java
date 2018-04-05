package pl.bookshop.tests.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.bookshop.domains.jpa.Category;

public class CategoryValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void test_categoryName_success() {
		Category category = getValidCategory();
		
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_categoryName_whenIsNull() {
		Category category = getValidCategory();
		category.setName(null);
		
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Category>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Category name cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_categoryName_whenIsTooShort() {
		String name = RandomStringUtils.randomAlphabetic(1);
		
		Category category = getValidCategory();
		category.setName(name);
		
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Category>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed category name: " + name + " size must be between 2 and 100", iterator.next().getMessage());
	}
	
	@Test
	public void test_categoryName_whenIsTooLong() {
		String name = RandomStringUtils.randomAlphabetic(101);
		
		Category category = getValidCategory();
		category.setName(name);
		
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Category>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed category name: " + name + " size must be between 2 and 100", iterator.next().getMessage());
	}
	
	public Category getValidCategory() {
		Category category = new Category();
		category.setName(RandomStringUtils.randomAlphabetic(20));
		return category;
	}
}
