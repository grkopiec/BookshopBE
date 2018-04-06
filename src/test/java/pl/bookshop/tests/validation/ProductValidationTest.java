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
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.tests.utils.TestUtils;

public class ProductValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void test_allFields_success() {
		Product product = getValidProduct();
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_productName_whenIsNull() {
		Product product = getValidProduct();
		product.setName(null);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product name cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_productName_whenIsTooShort() {
		String name = RandomStringUtils.randomAlphabetic(0);
		Product product = getValidProduct();
		product.setName(name);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed product name: " + name + " size must be between 1 and 100", iterator.next().getMessage());
	}
	
	@Test
	public void test_productName_whenIsTooLong() {
		String name = RandomStringUtils.randomAlphabetic(101);
		Product product = getValidProduct();
		product.setName(name);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed product name: " + name + " size must be between 1 and 100", iterator.next().getMessage());
	}
	
	@Test
	public void test_productProducer_whenIsNull() {
		Product product = getValidProduct();
		product.setProducer(null);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product producer cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_productProducer_whenIsTooShort() {
		String producer = RandomStringUtils.randomAlphabetic(0);
		Product product = getValidProduct();
		product.setProducer(producer);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed product producer: " + producer + " size must be between 1 and 100", iterator.next().getMessage());
	}
	
	@Test
	public void test_productProducer_whenIsTooLong() {
		String producer = RandomStringUtils.randomAlphabetic(101);
		Product product = getValidProduct();
		product.setProducer(producer);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed product producer: " + producer + " size must be between 1 and 100", iterator.next().getMessage());
	}
	
	@Test
	public void test_productDescription_successWhenIsNull() {
		Product product = getValidProduct();
		product.setDescription(null);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_productDescription_whenIsTooShort() {
		String description = RandomStringUtils.randomAlphabetic(0);
		Product product = getValidProduct();
		product.setDescription(description);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed product description: " + description + " size must be between 1 and 1000", iterator.next().getMessage());
	}
	
	@Test
	public void test_productDescription_whenIsTooLong() {
		String description = RandomStringUtils.randomAlphabetic(1001);
		Product product = getValidProduct();
		product.setDescription(description);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed product description: " + description + " size must be between 1 and 1000", iterator.next().getMessage());
	}
	
	@Test
	public void test_productPrice_whenIsNull() {
		Product product = getValidProduct();
		product.setPrice(null);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product price cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_productPrice_whenIsNotPositive() {
		Double price = -1.12;
		Product product = getValidProduct();
		product.setPrice(price);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product price must be zero or greater", iterator.next().getMessage());
	}
	
	@Test
	public void test_productPrice_whenTooMuchNumbersBeforeComma() {
		Double price = 1234567.12;
		Product product = getValidProduct();
		product.setPrice(price);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Product price: " + price + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}
	
	@Test
	public void test_productPrice_whenTooMuchDecimalPlaces() {
		Double price = 12345.123;
		Product product = getValidProduct();
		product.setPrice(price);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Product price: " + price + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}
	
	@Test
	public void test_productDiscount_successWhenIsNull() {
		Product product = getValidProduct();
		product.setDiscount(null);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_productDiscount_whenIsNotPositive() {
		Double discount = -1.12;
		Product product = getValidProduct();
		product.setDiscount(discount);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product discount must be zero or greater", iterator.next().getMessage());
	}
	
	@Test
	public void test_productDiscount_whenTooMuchNumbersBeforeComma() {
		Double discount = 1234567.12;
		Product product = getValidProduct();
		product.setDiscount(discount);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Product discount: " + discount + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}
	
	@Test
	public void test_productDiscount_whenTooMuchDecimalPlaces() {
		Double discount = 12345.123;
		Product product = getValidProduct();
		product.setDiscount(discount);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Product discount: " + discount + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}
	
	@Test
	public void test_productCategory_isNull() {
		Product product = getValidProduct();
		product.setCategory(null);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product category cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_productCategory_hasOneIncorrectField() {
		String categoryName = "a";
		Product product = getValidProduct();
		product.getCategory().setName(categoryName);
		
		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Product>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Passed category name: " + categoryName + " size must be between 2 and 100", iterator.next().getMessage());
	}
	
	private Category getValidCategory() {
		Category category = new Category();
		category.setName(RandomStringUtils.randomAlphabetic(20));
		return category;
	}
	
	private Product getValidProduct() {
		Product product = new Product();
		product.setName(RandomStringUtils.randomAlphabetic(5));
		product.setProducer(RandomStringUtils.randomAlphabetic(10));
		product.setDescription(RandomStringUtils.randomAlphabetic(50));
		product.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product.setCategory(getValidCategory());
		return product;
	}
}
