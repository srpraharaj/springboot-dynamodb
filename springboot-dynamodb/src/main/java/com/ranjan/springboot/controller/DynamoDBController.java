package com.ranjan.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ranjan.springboot.model.Student;
import com.ranjan.springboot.repository.DynamoDbRepository;

@RestController
@RequestMapping("/dynamodb")
public class DynamoDBController {

	@Autowired
	private DynamoDbRepository dynamoDbRepo;

	@PostMapping
	public String insertIntoDynamoDB(@RequestBody Student student) {
		dynamoDbRepo.insertIntoDynamoDB(student);
		return "Succcessfully inseted to DynamoDB.";
	}

	@GetMapping
	public ResponseEntity<Student> getStudentDetails(@RequestParam String id, @RequestParam String lastName) {
		Student student = dynamoDbRepo.getStudentDetails(id, lastName);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	@PutMapping
	public void updateStudent(@RequestBody Student student) {
		dynamoDbRepo.updateStudentDetails(student);
	}

	@DeleteMapping(value = "{id}/{lastName}")
	public void deleteStudent(@PathVariable("id") String id, @PathVariable("lastName") String lastName) {

		Student student = new Student();
		student.setId(id);
		student.setLastName(lastName);
		dynamoDbRepo.deleteStudent(student);
	}

}
