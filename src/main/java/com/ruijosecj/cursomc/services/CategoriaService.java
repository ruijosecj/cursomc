package com.ruijosecj.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Categoria;
import com.ruijosecj.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = catRepository.findById(id);
		return obj.orElse(null);
	}

}
