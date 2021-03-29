package org.sid.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sid.classe.Competence;
import org.sid.dao.CompetenceRepository;
import org.sid.dto.CompetenceDto;
import org.sid.specification.CompetenceCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CompetenceControllerTest {
	@Autowired
	CompetenceRepository competenceRepository;
	@Autowired
	private MockMvc mockMvc;

	String BASE_URL = "http://localhost:8082/";

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	void createCompetenceControllerTest() throws Exception {

		String url = BASE_URL + "/competence";

		CompetenceDto competenceDto = new CompetenceDto("changer un pneu de vélo", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", "tots");
		// ... more
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(competenceDto);

		mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());

		Optional<Competence> comp = competenceRepository.findById(2L);
		assertThat(comp.get().getNom()).isEqualTo("changer un pneu de vélo");
	}

	@Test
	void getCompetenceTest() throws Exception {

		String url = BASE_URL + "/competence/1";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}

	@Test
	void getCompetencesUserTest() throws Exception {
		String nom = "tots";
		String url = BASE_URL + "/competences?nom=" + nom + "&page=0&size=2";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}

	@Test
	void searchCompetencesTest() throws Exception {

		String url = BASE_URL + "/search?page=0&size=2";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		CompetenceCriteria criteria = new CompetenceCriteria(null, null, "mecanique");
		String requestJson = ow.writeValueAsString(criteria);

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk());

	}

}
