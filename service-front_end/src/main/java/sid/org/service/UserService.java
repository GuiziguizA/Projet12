package sid.org.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Users;
import sid.org.dto.UserDto;

public interface UserService {

	public void createUser(UserDto userDto, HttpServletRequest request) throws HttpStatusCodeException;

	public List<Users> getUsers(HttpServletRequest request) throws HttpStatusCodeException;

	public Users getUser(HttpServletRequest request) throws HttpStatusCodeException;

}
