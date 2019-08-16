package pl.bookshop.tests.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
	public static String toJson(Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);
	}

	public static Double nextDoubleWithDecimalPlaces(int min, int max, int decimalPlaces) {
		Double generatedDouble = RandomUtils.nextDouble(min, max);

		Double formatedDouble = fillDecimalPlaces(generatedDouble, decimalPlaces);
		return formatedDouble;
	}

	public static Double nextNegativeDoubleWithDecimalPlaces(int min, int max, int decimalPlaces) {
		if (min >= 0 || max >= 0) {
			throw new IllegalArgumentException("min and max must be negative");
		}

		Integer scope = max - min;
		if (scope <= 0) {
			throw new IllegalArgumentException("min must be lower then max");
		}

		Random random = new Random();
		Double generatedDouble = random.nextDouble() * scope - -min;

		Double formatedDouble = fillDecimalPlaces(generatedDouble, decimalPlaces);
		return formatedDouble;
	}

	public static <T> List<T> createListOfObjects(T object, long elements) throws Exception {
		if (elements <= 0) {
			throw new IllegalArgumentException("elements number must be positive");
		}

		List<T> list = new ArrayList<T>();
		for (long i = 0; i < elements; i++) {
			list.add(object);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T clone(T object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return (T) objectMapper.readValue(objectMapper.writeValueAsString(object), object.getClass());
	}

	private static Double fillDecimalPlaces(Double generatedDouble, Integer decimalPlaces) {
		String doubleFormat = "#." + StringUtils.repeat('#', decimalPlaces);
		DecimalFormat decimalFormat = new DecimalFormat(doubleFormat);

		Double formatedDouble = Double.valueOf(decimalFormat.format(generatedDouble));
		String parsedDouble = String.valueOf(formatedDouble);

		Integer dotIndex = parsedDouble.indexOf('.');
		while (parsedDouble.substring(dotIndex + 1).length() != decimalPlaces) {
			parsedDouble = parsedDouble + "1";
		}

		formatedDouble = Double.valueOf(parsedDouble);
		return formatedDouble;
	}
}
