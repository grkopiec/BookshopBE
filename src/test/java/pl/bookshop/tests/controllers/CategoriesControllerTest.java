package pl.bookshop.tests.controllers;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.bookshop.domains.jpa.Category;
import pl.bookshop.mvc.controllers.CategoriesController;
import pl.bookshop.services.CategoriesService;
import pl.bookshop.tests.utils.TestUtils;

public class CategoriesControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private CategoriesService categoriesService;
	
	@InjectMocks
	private CategoriesController categoriesController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(categoriesController)
				.build();
	}
	
	@Test
	public void test_findAll_success() throws Exception {
		List<Category> categories = Arrays.asList(getCategory0(), getCategory1());
		
		Mockito.when(categoriesService.findAll()).thenReturn(categories);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(categories.get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(categories.get(0).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(categories.get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(categories.get(1).getName())));
		
		Mockito.verify(categoriesService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	@Test
	public void test_findOne_success() throws Exception {
		Category category1 = getCategory0();
		
		Mockito.when(categoriesService.findOne(category1.getId())).thenReturn(category1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", category1.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(category1.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(category1.getName())));
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 */
	@Test
	public void test_findOne_fail() throws Exception {
		Category category1 = getCategory0();
		
		Mockito.when(categoriesService.findOne(1L)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", category1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	@Test
	public void test_create_success() throws Exception {
		Category category1 = getCategory0();
		
		Mockito.when(categoriesService.isExist(category1)).thenReturn(false);
		Mockito.doNothing().when(categoriesService).create(category1);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(category1)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		Mockito.verify(categoriesService, Mockito.times(1)).isExist(category1);
		Mockito.verify(categoriesService, Mockito.times(1)).create(category1);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	
	/**
	 * Should occur 409 code error, passed category already exists
	 */
	@Test
	public void test_create_fail() throws Exception {
		Category category1 = getCategory0();
		
		Mockito.when(categoriesService.isExist(category1)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(category1)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(categoriesService, Mockito.times(1)).isExist(category1);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	/**
	 * Scenario for this test is like this, first we are sending to PUT endpoint id exist object in database that will be replaced
	 * and new object, next in service we are finding object in database to replace. Passed object and object which will be replaced
	 * have different names. In object we passed to endpoint we are changing id on this that already contains exists object in
	 * database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_successWithDifferentNames() throws Exception {
		Category category = getCategory0();
		Category updatingCategory = getCategory1();
		Category updatedCategory = getCategory2();

		Mockito.when(categoriesService.findOne(category.getId())).thenReturn(updatingCategory);
		Mockito.when(categoriesService.isExist(category)).thenReturn(false);
		Mockito.when(categoriesService.update(category.getId(), category)).thenReturn(updatedCategory);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/categories/{id}", category.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(category)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(updatedCategory.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(updatedCategory.getName())));
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(category.getId());
		Mockito.verify(categoriesService, Mockito.times(1)).isExist(category);
		Mockito.verify(categoriesService, Mockito.times(1)).update(category.getId(), category);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	/**
	 * Scenario for this test is like this, first we are sending to PUT endpoint id exist object in database that will be replaced
	 * and new object, next in service we are finding object in database to replace. Passed object and object which will be replaced
	 * have the same names. In object we passed to endpoint we are changing id on this that already contains exists object in
	 * database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_successWithSameNames() throws Exception {
		Category category = getCategory0();
		Category updatingCategory = getCategory1();
		updatingCategory.setName(category.getName());
		Category updatedCategory = getCategory2();

		Mockito.when(categoriesService.findOne(category.getId())).thenReturn(updatingCategory);
		Mockito.when(categoriesService.update(category.getId(), category)).thenReturn(updatedCategory);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/categories/{id}", category.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(category)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(updatedCategory.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(updatedCategory.getName())));
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(category.getId());
		Mockito.verify(categoriesService, Mockito.times(1)).update(category.getId(), category);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	/**
	 * Should occur 404 code error, do not found category. In this scenario object will not be change because do not exist requested id
	 */
	@Test
	public void test_update_doNotFindIdfail() throws Exception {
		Category category = getCategory0();

		Mockito.when(categoriesService.findOne(category.getId())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/categories/{id}", category.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(category)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(category.getId());
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	
	/**
	 * Should occur 409 code error, category name already exists. In this scenario object will not be change because category name
	 * already exists
	 */
	@Test
	public void test_update_nameColisionFail() throws Exception {
		Category category = getCategory0();
		Category updatingCategory = getCategory1();

		
		Mockito.when(categoriesService.findOne(category.getId())).thenReturn(updatingCategory);
		Mockito.when(categoriesService.isExist(category)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/categories/{id}", category.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(category)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(category.getId());
		Mockito.verify(categoriesService, Mockito.times(1)).isExist(category);
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	@Test
	public void test_delete_success() throws Exception {
		Category category1 = getCategory0();
		
		Mockito.when(categoriesService.findOne(category1.getId())).thenReturn(category1);
		Mockito.doNothing().when(categoriesService).delete(category1.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/categories/{id}", category1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(category1.getId());
		Mockito.verify(categoriesService, Mockito.times(1)).delete(category1.getId());
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	/**
	 * Should occur 409 code error, category do not exist
	 */
	@Test
	public void test_delete_fail() throws Exception {
		Category category1 = getCategory0();
		
		Mockito.when(categoriesService.findOne(category1.getId())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/categories/{id}", category1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(categoriesService, Mockito.times(1)).findOne(category1.getId());
		Mockito.verifyNoMoreInteractions(categoriesService);
	}
	
	private Category getCategory0() {
		Category category0 = new Category();
		category0.setId(1L);
		category0.setName(RandomStringUtils.randomAlphabetic(10));
		return category0;
	}
	
	private Category getCategory1() {
		Category category1 = new Category();
		category1.setId(2L);
		category1.setName(RandomStringUtils.randomAlphabetic(5));
		return category1;
	}
	
	private Category getCategory2() {
		Category category2 = new Category();
		category2.setId(2L);
		category2.setName(RandomStringUtils.randomAlphabetic(5));
		return category2;
	}
}
