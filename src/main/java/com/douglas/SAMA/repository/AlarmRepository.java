package com.douglas.SAMA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMA.model.Alarm;
import com.douglas.SAMA.model.Aluno;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

	List<Alarm> findAllByAluno(Aluno aluno);

}
