package com.example.pwr.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pwr.exception.DefaultException;

public class ControllerGeneric<T, ID, R extends JpaRepository<T, ID>> {
	
	protected R repository;
	
	public enum Mode{
		SAVE, 
		UPDATE, 
		LIST, 
		DELETE, 
		LOAD;
	}
	
	protected void validate(T entity, Mode mode) {
		
	}
	
	public T save(T object) {
		validate(object, Mode.SAVE);
		return repository.save(object);
	}
	
	public T update(T object) {
		validate(object, Mode.UPDATE);
		return repository.save(object);
	}
	
	public void delete(T object) {
		validate(object, Mode.DELETE);
		repository.delete(object);
	}
	
	public List<T> list() {
		validate(null, Mode.LIST);
		return repository.findAll();
	}
	
	public T load(ID id) throws DefaultException {
		return repository.findById(id).orElseThrow(() -> new DefaultException("Não existe!"));
	}

}
