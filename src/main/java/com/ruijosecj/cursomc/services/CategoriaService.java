package com.ruijosecj.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Categoria;
import com.ruijosecj.cursomc.repositories.CategoriaRepository;
import com.ruijosecj.cursomc.services.exception.DataIntegrityException;
import com.ruijosecj.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepository;

	public Categoria find(Integer id) {
		 Optional<Categoria> obj = catRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	} 

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return catRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return catRepository.save(categoria);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			catRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
	
	public List<Categoria> findAll(){
		return catRepository.findAll();
	}
}
