package com.example.Junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JunitApplicationTests {
	@Autowired
	TestSomeOneRepository testSomeOneRepository;
	@LocalServerPort
	private int port;
	private static String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;


	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp(){
		baseUrl = baseUrl.concat(":").concat(port+"");
	}

	@Test
	public void add(){
		SomeOne someOne = new SomeOne("Ravi",27,"male");
		SomeOne someOne1 = new SomeOne("Jai",24,"male");

		SomeOne response = restTemplate.postForObject(baseUrl+"/add",someOne, SomeOne.class);
		SomeOne response2 = restTemplate.postForObject(baseUrl+"/add",someOne1, SomeOne.class);
		assertEquals("Ravi",response.getName());
		assertEquals("Jai",response2.getName());
		assertEquals(2,testSomeOneRepository.findAll().size());
	}

	@Test
	@Sql(statements = "insert into some_one(name,age,gender) values ('Chatu',6,'male')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from some_one where name='Chatu'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void fetch(){
		List<SomeOne> someOnes = restTemplate.getForObject(baseUrl+"/fetch", List.class);
		assertEquals(1,testSomeOneRepository.findAll().size());
	}

	@Test
	@Sql(statements = "insert into some_one(name,age,gender) values('Prasanna',25,'male')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from some_one where name='Prasanna'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void fetchById(){
		SomeOne someOne = restTemplate.getForObject(baseUrl+"/fetch/{id}", SomeOne.class,1);
		assertAll(()->assertNotNull(someOne),
				()->assertEquals(1,testSomeOneRepository.findAll().size()),
				()->assertEquals("Prasanna",someOne.getName())
		);
	}

	@Test
	@Sql(statements = "insert into some_one(name,age,gender) values('Hasini',6,'male')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from some_one where name='Hasini'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void update(){
		SomeOne someOne = new SomeOne("Choco",6,"male");
		restTemplate.put(baseUrl+"/update/{id}",someOne,1);
		SomeOne someOne1 = testSomeOneRepository.findById(1).get();
		assertAll(()->assertNotNull(someOne1),
				()->assertEquals(1,testSomeOneRepository.findAll().size()),
				()->assertEquals("Choco",someOne1.getName()));
	}

	@Test
	@Sql(statements = "insert into some_one(name,age,gender) values('Gowrish',28,'male')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void delete(){
		assertEquals(1,testSomeOneRepository.findAll().size());
		restTemplate.delete(baseUrl+"/delete/{id}",1);
		assertEquals(0,testSomeOneRepository.findAll().size());
	}


}
