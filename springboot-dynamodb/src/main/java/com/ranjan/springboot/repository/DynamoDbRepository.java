package com.ranjan.springboot.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.ranjan.springboot.model.Student;

@Repository
public class DynamoDbRepository {

	private static final Logger log = LoggerFactory.getLogger(DynamoDbRepository.class);

	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(Student student) {
		mapper.save(student);
	}

	public Student getStudentDetails(String id, String lastName) {
		return mapper.load(Student.class, id, lastName);
	}

	public void updateStudentDetails(Student student) {
		try {
			mapper.save(student, buildDynamoDbSaveExpression(student));
		} catch (ConditionalCheckFailedException e) {
			log.error("invalid data - " + e.getMessage());
		}
	}

	public void deleteStudent(Student student) {
		mapper.delete(student);
	}

	public DynamoDBSaveExpression buildDynamoDbSaveExpression(Student student) {

		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("id", new ExpectedAttributeValue(new AttributeValue(student.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}

}
