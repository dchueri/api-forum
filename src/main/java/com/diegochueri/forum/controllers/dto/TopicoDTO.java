package com.diegochueri.forum.controllers.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.diegochueri.forum.entities.Topico;

public class TopicoDTO {
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataDeCriacao;
	
	public TopicoDTO(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataDeCriacao = topico.getDataDeCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}

	public static List<TopicoDTO> converterArrays(List<Topico> topicos) {
		return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
	}
	
	
}
