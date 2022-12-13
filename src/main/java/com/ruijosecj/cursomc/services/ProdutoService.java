package com.ruijosecj.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruijosecj.cursomc.domain.Categoria;
import com.ruijosecj.cursomc.domain.Produto;
import com.ruijosecj.cursomc.repositories.CategoriaRepository;
import com.ruijosecj.cursomc.repositories.ProdutoRepository;
import com.ruijosecj.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository podRepository;
	
	@Autowired
	private CategoriaRepository catRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = podRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		} 

	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy );
		List<Categoria> categorias = catRepository.findAllById(ids);
		//return podRepository.search(nome, categorias, pageRequest);
		return podRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}
}
