package pl.bookshop.utils;

public class StringUtils {
	public static final String resourceLocation = "file:" + System.getProperty("user.home") +
			System.getProperty("file.separator") + "images" + System.getProperty("file.separator");
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String TOKEN_HEADER_STARTS_WITH = "Bearer ";
}
