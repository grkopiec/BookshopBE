package pl.bookshop.tests.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.bookshop.domains.jpa.Category;
import pl.bookshop.repositories.jpa.CategoriesRepository;
import pl.bookshop.services.CategoriesServiceImpl;

public class CategoriesServiceTest {
	@Mock
	private CategoriesRepository categoriesRepository;

	@InjectMocks
	private CategoriesServiceImpl categoriesService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_findAll_success() {
		List<Category> categories = Arrays.asList(getCategory0(), getCategory1());

		Mockito.when(categoriesRepository.findAll()).thenReturn(categories);

		List<Category> answer = categoriesService.findAll();

		Assert.assertEquals(categories, answer);

		Mockito.verify(categoriesRepository, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_findOne_success() {
		Category category = getCategory0();
		Optional<Category> categoryOptional = Optional.of(category);

		Mockito.when(categoriesRepository.findById(category.getId())).thenReturn(categoryOptional);

		Category answer = categoriesService.findOne(category.getId());

		Assert.assertEquals(category, answer);

		Mockito.verify(categoriesRepository, Mockito.times(1)).findById(category.getId());
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_findOne_failWhenNotExists() {
		Long id = getCategory0().getId();

		Mockito.when(categoriesRepository.findById(id)).thenReturn(Optional.empty());

		Category answer = categoriesService.findOne(id);

		Assert.assertNull(answer);

		Mockito.verify(categoriesRepository, Mockito.times(1)).findById(id);
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_findByName_success() {
		Category category = getCategory0();

		Mockito.when(categoriesRepository.findByName(category.getName())).thenReturn(category);

		Category answer = categoriesService.findByName(category.getName());

		Assert.assertEquals(category, answer);

		Mockito.verify(categoriesRepository, Mockito.times(1)).findByName(category.getName());
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_isExist_success() {
		Category category = getCategory0();

		Mockito.when(categoriesRepository.findByName(category.getName())).thenReturn(category);

		Boolean answer = categoriesService.isExist(category);

		Assert.assertTrue(answer);

		Mockito.verify(categoriesRepository, Mockito.times(1)).findByName(category.getName());
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_isExist_failWhenNotExists() {
		Category category = getCategory0();

		Mockito.when(categoriesRepository.findByName(category.getName())).thenReturn(null);

		Boolean answer = categoriesService.isExist(category);

		Assert.assertFalse(answer);

		Mockito.verify(categoriesRepository, Mockito.times(1)).findByName(category.getName());
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_save_success() {
		Category category = getCategory0();

		categoriesService.create(category);

		Mockito.verify(categoriesRepository, Mockito.times(1)).save(category);
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_update_success() {
		Long id = RandomUtils.nextLong(0, 100);
		Category category = getCategory0();
		Category updatedCategory = getCategory(id, category.getName());

		categoriesService.update(id, category);

		Mockito.verify(categoriesRepository, Mockito.times(1)).save(updatedCategory);
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	@Test
	public void test_delete_success() {
		Long id = RandomUtils.nextLong(0, 100);

		categoriesService.delete(id);

		Mockito.verify(categoriesRepository, Mockito.times(1)).deleteById(id);
		Mockito.verifyNoMoreInteractions(categoriesRepository);
	}

	private Category getCategory0() {
		Category category0 = new Category();
		category0.setId(RandomUtils.nextLong(0, 100));
		category0.setName(RandomStringUtils.randomAlphabetic(20));
		return category0;
	}

	private Category getCategory1() {
		Category category1 = new Category();
		category1.setId(RandomUtils.nextLong(0, 100));
		category1.setName(RandomStringUtils.randomAlphabetic(20));
		return category1;
	}
	
	private Category getCategory(Long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		return category;
	}
}
