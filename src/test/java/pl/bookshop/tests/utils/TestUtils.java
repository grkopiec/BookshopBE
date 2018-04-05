package pl.bookshop.tests.utils;

import java.text.DecimalFormat;

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
		
		String doubleFormat = "#." + StringUtils.repeat('#', decimalPlaces);
		DecimalFormat decimalFormat = new DecimalFormat(doubleFormat);
		
		Double formatedDouble = Double.valueOf(decimalFormat.format(generatedDouble));
		return formatedDouble;
	}
}
