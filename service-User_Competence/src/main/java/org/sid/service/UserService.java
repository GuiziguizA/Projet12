package org.sid.service;

import java.util.List;
import java.util.Optional;

import org.sid.classe.Users;
import org.sid.dto.UserDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;

public interface UserService {

	public Users createUser(UserDto userDto) throws ResultNotFoundException, EntityAlreadyExistException;

	public void deleteUser(Long id) throws ResultNotFoundException;

	public Users getUser(Long id) throws ResultNotFoundException;

	public UserDto getUserDto(Long id) throws ResultNotFoundException;

	public void updateUser(UserDto userDto, Optional<String> role) throws ResultNotFoundException;

	public List<Users> getUsers() throws ResultNotFoundException;

}
