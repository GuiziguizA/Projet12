package sid.org.controller;

import static org.junit.jupiter.api.Assertions.fail;
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
class creneauxController {

	@Autowired
	private MockMvc mockMvc;

	String BASE_URL = "http://localhost:8082/";

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	void getCreneauxUserTest() throws Exception {

		String url = BASE_URL + "/creneaux?1&page=0&size=2";

		mockMvc.perform(get(url).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}

}
