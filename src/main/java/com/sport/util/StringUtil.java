package com.sport.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;
import org.springframework.stereotype.Service;

import com.sport.payload.request.LoginRequest;

@Service(value = "stringUtil")
public class StringUtil {

	public String generateRandomString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public String generateNowDate() {

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public String convertInterestToArray(String Interest) {

		String[] elements = Interest.split(",");

		List<String> fixedLenghtList = Arrays.asList(elements);

		ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

		return listOfString.toString();
	}

	
	
	
	public boolean isEmpty(Object obj, String str)  {

	    for (Field field : this.getClass().getDeclaredFields()) {
	        try {
	            field.setAccessible(true);
	            if (field.get(this)!=null) {
	                return false;
	            }
	        } catch (Exception e) {
	          System.out.println("Exception occured in processing");
	        }
	    }
	    return true;
	}
	
	
	
public Boolean isEmail(String value) {
	
	   String email = value;
       Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
       Matcher mat = pattern.matcher(email);

       if(mat.matches()){
    	   return true;
    	   
       }else {
    	   return false;
       }
}

	
	
	
	public boolean doesObjectContainField(Object object, String fieldName) {
		return Arrays.stream(object.getClass().getFields()).anyMatch(f -> f.getName().equals(fieldName));
	}
}
