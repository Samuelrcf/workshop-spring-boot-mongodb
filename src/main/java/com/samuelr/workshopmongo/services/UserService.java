package com.samuelr.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuelr.workshopmongo.domain.User;
import com.samuelr.workshopmongo.dto.UserDTO;
import com.samuelr.workshopmongo.repositories.UserRepository;
import com.samuelr.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado.");
		}
		return user.get();
	}

	public User insert(User obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User update(User obj) {
		Optional<User> newObj = repo.findById(obj.getId());
		if (newObj.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado.");
		}
		updateData(newObj, obj);
		return repo.save(newObj.get());
	}

	private void updateData(Optional<User> newObj, User obj) {
		if (newObj.isPresent()) {
			User user = newObj.get();
			user.setName(obj.getName());
			user.setEmail(obj.getEmail());
		}
	}

	public User fromDTO(UserDTO dto) {
		return new User(dto.getId(), dto.getName(), dto.getEmail());
	}
}
