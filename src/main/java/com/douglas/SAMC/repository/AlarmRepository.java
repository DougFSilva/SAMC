package com.douglas.SAMC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.SAMC.model.Alarm;
import com.douglas.SAMC.model.Aluno;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

	List<Alarm> findAllByAluno(Aluno aluno);

}
