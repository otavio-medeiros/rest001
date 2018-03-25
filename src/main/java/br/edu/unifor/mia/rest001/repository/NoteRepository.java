package br.edu.unifor.mia.rest001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unifor.mia.rest001.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}