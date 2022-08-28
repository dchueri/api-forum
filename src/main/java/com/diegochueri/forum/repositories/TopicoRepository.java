package com.diegochueri.forum.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegochueri.forum.entities.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByCurso_Nome(String nomeDoCurso);

}
