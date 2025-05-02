package com.projetospring.course.services;

import com.projetospring.course.entities.User;
import com.projetospring.course.repositories.UserRepository;
import com.projetospring.course.services.exceptions.DataBaseException;
import com.projetospring.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {       // Essa exeção RuntimeException vai retornar a msg de erro 204 do
            throw new ResourceNotFoundException(id);       // postman para terminal da IDE. Já o printStackTrace() vai mostrar
        } catch (DataIntegrityViolationException e) {      // o erro de exeção
            throw new DataBaseException(e.getMessage());
        }
    }

    public User update(Long id, User obj) {
        User entity = repository.getReferenceById(id);
        updateDate(entity, obj);
        return repository.save(entity);
    }

    public void updateDate(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
    }
}
