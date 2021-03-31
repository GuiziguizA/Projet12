package sid.org.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class creneauxControllerTest {

	@Autowired
	private MockMvc mockMvc;

	String BASE_URL = "http://localhost:8074/";

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	void getCreneauxUserTest() throws Exception {

		String url = BASE_URL + "/creneaux?idUser=1&page=0&size=2";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}

	@Test
	void getCreneauxUser1Test() throws Exception {

		String url = BASE_URL + "/creneaux1?idUser1=1&page=0&size=2";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}

	@Test
	void getCreneau1Test() throws Exception {

		String url = BASE_URL + "/creneau1?id=1";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}

	@Test
	void getCreneauTest() throws Exception {

		String url = BASE_URL + "/creneau?id=1&idUser=1&idUser1=2";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}
	/*
	 * @Test void getCreneauAvisTest() throws Exception {
	 * 
	 * String url = BASE_URL + "/creneauxAvis?idUser1=1&idUser1=2"; ObjectMapper
	 * mapper = new ObjectMapper();
	 * mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); ObjectWriter
	 * ow = mapper.writer().withDefaultPrettyPrinter(); Competence competence = new
	 * Competence(1L, "changer un pneu", "mecanique",
	 * "j'ai une machine permettant de changer les pneus d'une voiture", user);
	 * 
	 * String requestJson = ow.writeValueAsString(userDto);
	 * 
	 * mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status
	 * ().isOk());
	 * 
	 * }
	 */

}
