package org.sid.service;

import org.sid.classe.User;
import org.sid.dto.UserDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;

public interface UserService {

	public User createUser(UserDto userDto) throws ResultNotFoundException, EntityAlreadyExistException;

	public void deleteUser(Long id) throws ResultNotFoundException;

}
