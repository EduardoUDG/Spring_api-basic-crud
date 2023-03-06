package com.company.api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.company.api.helpers.ValidationMessages;
import com.company.api.pojo.Response;

@Service
public class ValidationService {

	public ResponseEntity<?> bodyErrorResponse(BindingResult result) {
		List<String> errors = result.getFieldErrors()
				.stream()
				.map( err -> ValidationMessages.ERROR_MESSAGE_PREFIX + " " + err.getObjectName() + " '" + err.getField() +"' " + err.getDefaultMessage() )
				.collect(Collectors.toList());
		return new ResponseEntity<>(new Response(false, "error", errors), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> idNotFoundResponse(Long id) {
		var message = Arrays.asList("El id: "+id+" no existe en la base de datos");
		return new ResponseEntity<>(new Response(false, "error", message),HttpStatus.OK);
	}
	
	public ResponseEntity<?> emptyDataResponse() {
		String message = "La base de datos está vacía";
		List<?> array = new ArrayList<>();
		return new ResponseEntity<>(new Response(true, message, array),HttpStatus.OK);
	}
	
	public ResponseEntity<?> idIsNotEqualBodyResponse() {
		List<String> array = Arrays.asList("El id de la parámetro y del body no son iguales");
		return new ResponseEntity<>(new Response(false, "error", array),HttpStatus.OK);
	}
	
}
