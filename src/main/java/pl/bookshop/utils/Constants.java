package pl.bookshop.utils;

public class Constants {
	public static final String resourceLocation = "file:" + System.getProperty("user.home") +
			System.getProperty("file.separator") + "images" + System.getProperty("file.separator");
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String TOKEN_HEADER_STARTS_WITH = "Bearer ";
	
	public static final String CATEGORIES_SEQUENCE_GENERATOR = "categoryIdGenerator";
	public static final String CATEGORIES_SEQUENCE = "CATEGORIES_SEQUENCE";
	public static final String PRODUCTS_SEQUENCE_GENERATOR = "productIdGenerator";
	public static final String PRODUCTS_SEQUENCE = "PRODUCTS_SEQUENCE";
	public static final String USERS_SEQUENCE_GENERATOR = "userIdGenerator";
	public static final String USERS_SEQUENCE = "USERS_SEQUENCE";
	public static final String ORDERS_SEQUENCE_GENERATOR = "orderIdGenerator";
	public static final String ORDERS_SEQUENCE = "ORDERS_SEQUENCE";
}
