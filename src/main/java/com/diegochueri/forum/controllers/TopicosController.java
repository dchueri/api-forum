package com.diegochueri.forum.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.diegochueri.forum.controllers.dto.AtualizaTopicoDTO;
import com.diegochueri.forum.controllers.dto.CriaTopicoDTO;
import com.diegochueri.forum.controllers.dto.TopicoDTO;
import com.diegochueri.forum.entities.Topico;
import com.diegochueri.forum.repositories.CursoRepository;
import com.diegochueri.forum.repositories.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public List<TopicoDTO> lista(String nomeDoCurso) {
		if (nomeDoCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converterArrays(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeDoCurso);
			return TopicoDTO.converterArrays(topicos);
		}

	}

	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid CriaTopicoDTO topicoDTO, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoDTO.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Topico> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(topico.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaTopicoDTO topicoAtualizado) {
		Optional<Topico> topicoParaAtualizar = topicoRepository.findById(id);
		if (topicoParaAtualizar.isPresent()) {
			Topico topico = topicoAtualizado.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> topicoParaRemover = topicoRepository.findById(id);
		if (topicoParaRemover.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}
}
