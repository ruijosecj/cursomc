package com.ruijosecj.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ruijosecj.cursomc.domain.Categoria;
import com.ruijosecj.cursomc.domain.Cidade;
import com.ruijosecj.cursomc.domain.Cliente;
import com.ruijosecj.cursomc.domain.Endereco;
import com.ruijosecj.cursomc.domain.Estado;
import com.ruijosecj.cursomc.domain.Produto;
import com.ruijosecj.cursomc.domain.enums.TipoCliente;
import com.ruijosecj.cursomc.repositories.CategoriaRepository;
import com.ruijosecj.cursomc.repositories.CidadeRepository;
import com.ruijosecj.cursomc.repositories.ClienteRepository;
import com.ruijosecj.cursomc.repositories.EnderecoRepository;
import com.ruijosecj.cursomc.repositories.EstadoRepository;
import com.ruijosecj.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository catRepository;
	
	@Autowired
	private ProdutoRepository prodRepository;
	
	@Autowired
	private CidadeRepository cidRepository;
	
	@Autowired
	private EstadoRepository estRepository;
	
	@Autowired
	private EnderecoRepository endRepository;
	
	@Autowired
	private ClienteRepository cliRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		estRepository.saveAll(Arrays.asList(est1, est2));
		cidRepository.saveAll(Arrays.asList(cid1, cid2,cid3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua das Flores", "300", "Apto 303", "Jardim", "38220834",cli1,cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "800", "Centro", "38777012",cli1,cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		cliRepository.saveAll(Arrays.asList(cli1));
		endRepository.saveAll(Arrays.asList(e1,e2));
		

	}

}
