package br.edu.unifor.mia.rest001.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifor.mia.rest001.exception.ResourceNotFoundException;
import br.edu.unifor.mia.rest001.model.Note;
import br.edu.unifor.mia.rest001.repository.NoteRepository;
import br.edu.unifor.mia.rest001.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteRepository noteRepository;

	@Override
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	@Override
	public Note createNote(Note note) {
		return noteRepository.save(note);
	}

	@Override
	public Note getNoteById(Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}

	@Override
	public Note updateNote(Long noteId, Note noteDetails) {
		Note note = getNoteById(noteId);
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}

	@Override
	public void deleteNote(Long noteId) {
		noteRepository.deleteById(noteId);
	}

}