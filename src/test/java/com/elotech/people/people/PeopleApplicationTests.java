package com.elotech.people.people;

import com.elotech.people.people.builder.EntityBuilder;
import com.elotech.people.people.entity.Person;
import com.elotech.people.people.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private PersonRepository personRepository;

	private final String URL = "/v1/people/";

	@Test
	public void contextLoads() {
	}

	@Test
	public void getPersonByIdShouldReturnStatus200() {
		Person person = new EntityBuilder().createPerson();
		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		ResponseEntity<String> response = restTemplate.getForEntity(URL + "1", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void getPersonByNameShouldReturnStatus200() {
		Person person = new EntityBuilder().createPerson();
		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		ResponseEntity<String> response = restTemplate.getForEntity(URL + "findByName/" + person.getName(), String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void getAllPersonShouldReturnStatus200() {
		Person person = new EntityBuilder().createPerson();
		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void savePersonShouldPersistAndReturnStatus201() {
		Person person = new EntityBuilder().createPerson();

		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		BDDMockito.when(personRepository.save(BDDMockito.any())).thenReturn(person);

		ResponseEntity<String> response = restTemplate.postForEntity(URL, person, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(CREATED.value());
	}

	@Test
	public void deletePersonShouldPersistAndReturnStatus200() {
		Person person = new EntityBuilder().createPerson();

		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		BDDMockito.doNothing().when(personRepository).delete(BDDMockito.any());
		restTemplate.delete(URL + "1");
		ResponseEntity<String> response = restTemplate.exchange(URL + "1", HttpMethod.DELETE, null, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void createPersonShouldPersistAndReturnStatus201() {
		Person person = new EntityBuilder().createPerson();

		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		BDDMockito.when(personRepository.save(person)).thenReturn(person);

		HttpEntity<?> httpEntity = new HttpEntity<Object>(person, null);

		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(CREATED.value());
	}

	@Test
	public void updatePersonShouldPersistAndReturnStatus200() {
		Person person = new EntityBuilder().createPerson();

		BDDMockito.when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
		BDDMockito.when(personRepository.save(person)).thenReturn(person);

		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(person, null), String.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	public void resourceNotFoundShouldThrownException() {
		ResponseEntity<String> response = restTemplate.exchange(URL + "-1", HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
