package org.sid.classe;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credentials {

	private String type;
	private String value;
	private boolean temporary;
}
