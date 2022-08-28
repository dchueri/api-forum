package com.diegochueri.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegochueri.forum.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeDoCurso);

}
