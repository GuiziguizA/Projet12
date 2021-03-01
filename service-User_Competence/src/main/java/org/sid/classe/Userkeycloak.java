package org.sid.classe;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Userkeycloak {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String enabled = "true";
	private String username;
	private List<Credentials> credentials;

}
