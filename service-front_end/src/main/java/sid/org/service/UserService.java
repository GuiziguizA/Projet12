package sid.org.service;

import java.util.List;

import sid.org.classe.Users;
import sid.org.dto.UserDto;
import sid.org.exception.APiUSerAndCompetenceException;

public interface UserService {

	public void createUser(UserDto userDto) throws APiUSerAndCompetenceException;

	public List<Users> getUsers() throws APiUSerAndCompetenceException;

}
