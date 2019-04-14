package com.elotech.people.people;

import com.elotech.people.people.builder.EntityBuilder;
import com.elotech.people.people.entity.Contato;
import com.elotech.people.people.entity.Person;
import com.elotech.people.people.entity.PersonContato;
import com.elotech.people.people.repository.ContatoRepository;
import com.elotech.people.people.repository.PersonRepository;

import org.junit.Before;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ContatoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private ContatoRepository contatoRepository;
	@MockBean
	private PersonRepository personRepository;

	private final String URL = "/v1/contatos/";

	@Before
	public void setUp() {
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void getContatoByIdShouldReturnStatus200() {
		Contato contato = new EntityBuilder().createContato();
		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		ResponseEntity<String> response = restTemplate.getForEntity(URL + "1", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void getContatoByNameShouldReturnStatus200() {
		Contato contato = new EntityBuilder().createContato();
		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		ResponseEntity<String> response = restTemplate.getForEntity(URL + "findByName/" + contato.getName(), String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void getAllContatoShouldReturnStatus200() {
		Contato contato = new EntityBuilder().createContato();
		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void saveContatoShouldPersistAndReturnStatus201() {
		Contato contato = new EntityBuilder().createContato();

		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		BDDMockito.when(contatoRepository.save(BDDMockito.any())).thenReturn(contato);

		ResponseEntity<String> response = restTemplate.postForEntity(URL, contato, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(CREATED.value());
	}

	@Test
	public void deleteContatoShouldPersistAndReturnStatus200() {
		Contato contato = new EntityBuilder().createContato();

		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		BDDMockito.doNothing().when(contatoRepository).delete(BDDMockito.any());
		restTemplate.delete(URL + "1");
		ResponseEntity<String> response = restTemplate.exchange(URL + "1", HttpMethod.DELETE, null, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(OK.value());
	}

	@Test
	public void createContatoShouldPersistAndReturnStatus201() {
		Contato contato = new EntityBuilder().createContato();

		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		BDDMockito.when(contatoRepository.save(contato)).thenReturn(contato);

		HttpEntity<?> httpEntity = new HttpEntity<Object>(contato, null);

		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(CREATED.value());
	}

	@Test
	public void updateContatoShouldPersistAndReturnStatus200() {
		Contato contato = new EntityBuilder().createContato();

		BDDMockito.when(contatoRepository.findById(1L)).thenReturn(java.util.Optional.of(contato));
		BDDMockito.when(contatoRepository.save(contato)).thenReturn(contato);

		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(contato, null), String.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	public void resourceNotFoundShouldThrownException() {
		ResponseEntity<String> response = restTemplate.exchange(URL + "-1", HttpMethod.GET, new HttpEntity<>(null, null), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void databaseShouldPersistContatosAndPeopleAtSameTimeAndReturn200() {
		PersonContato personContato = new EntityBuilder().createPersonContato();

		BDDMockito.when(personRepository.save(BDDMockito.any())).thenReturn(personContato.getPerson());
		BDDMockito.when(personRepository.findById(0L)).thenReturn(Optional.of(personContato.getPerson()));
		BDDMockito.when(contatoRepository.saveAll(BDDMockito.any())).thenReturn(personContato.getContatos());

		ResponseEntity<String> response = restTemplate.exchange(
				URL + "savePersonAndContatos/", HttpMethod.POST,
				new HttpEntity<>(personContato, null), String.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	public void databaseShouldUpdateContatosAndPeopleAtSameTimeAndReturn200() {
		PersonContato personContato = new EntityBuilder().createPersonContato();

		BDDMockito.when(personRepository.findById(personContato.getPerson().getId())).thenReturn(Optional.of(personContato.getPerson()));
		BDDMockito.when(personRepository.save(personContato.getPerson())).thenReturn((personContato.getPerson()));
		personContato.getContatos().forEach(contato -> {
			BDDMockito.when(contatoRepository.findById(contato.getId())).thenReturn(Optional.of(contato));
			BDDMockito.when(contatoRepository.save(contato)).thenReturn((contato));
		});

		ResponseEntity<String> response = restTemplate.exchange(URL + "updatePersonAndContatos/",
				HttpMethod.PUT, new HttpEntity<>(personContato, null), String.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).contains(personContato.getPerson().getName());
		personContato.getContatos().forEach(contato -> assertThat(response.getBody()).contains(contato.getName()));
	}

	@Test
	public void getContatosByPersonIdShouldReturnContatosFromThePersonId() {
		Person person1 = new EntityBuilder().createPerson();
		person1.setId(1);
		Contato contato1 = new EntityBuilder().createContato();
		contato1.setPerson(person1);
		contato1.setId(2);

		BDDMockito.when(contatoRepository.findByPersonId(person1.getId())).thenReturn(Collections.singletonList(contato1));

		ResponseEntity<String> response = restTemplate.getForEntity(URL + "findByPersonId/"+person1.getId(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}
}
