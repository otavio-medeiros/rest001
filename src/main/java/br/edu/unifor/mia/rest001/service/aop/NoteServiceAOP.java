package br.edu.unifor.mia.rest001.service.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.edu.unifor.mia.rest001.model.Note;
import br.edu.unifor.mia.rest001.utils.Utils;

@Component
@Aspect
@PropertySource("classpath:static/aop-switch.properties")
public class NoteServiceAOP {

	private static final String FIND_ALL = "br.edu.unifor.mia.rest001.service.NoteService.getAllNotes";
	private static final String CREATE = "br.edu.unifor.mia.rest001.service.NoteService.createNote";
	private static final String FIND_ONE = "br.edu.unifor.mia.rest001.service.NoteService.getNoteById";
	private static final String SAVE = "br.edu.unifor.mia.rest001.service.NoteService.updateNote";
	private static final String DELETE = "br.edu.unifor.mia.rest001.service.NoteService.deleteNote";

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${url.base}")
	private String URL_BASE;

	@Value("${service.name.note}")
	private String SERVICE_NAME;

	@SuppressWarnings("unchecked")
	@Around("execution(* " + FIND_ALL + "(..))")
	public List<Note> findAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		List<Note> lstNote;

		if (Boolean.parseBoolean(Utils.getProperty(FIND_ALL))) {

			Note[] arrNote = restTemplate.getForObject(URL_BASE + SERVICE_NAME + "/notes", Note[].class);

			lstNote = Arrays.asList(arrNote);

		} else {

			lstNote = (List<Note>) proceedingJoinPoint.proceed();

		}

		return lstNote;

	}

	@Around("execution(* " + CREATE + "(..))")
	public Note create(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Note note;

		note = null;

		if (Boolean.parseBoolean(Utils.getProperty(CREATE))) {

			note = (Note) restTemplate.postForObject(URL_BASE + SERVICE_NAME + "/notes", (Note) proceedingJoinPoint.getArgs()[0], Note.class);

		} else {

			note = (Note) proceedingJoinPoint.proceed();

		}

		return note;

	}

	@Around("execution(* " + FIND_ONE + "(..))")
	public Note findOne(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Note note;

		if (Boolean.parseBoolean(Utils.getProperty(FIND_ONE))) {

			Map<String, Long> mapParams = new HashMap<String, Long>();

			Long id = (Long) proceedingJoinPoint.getArgs()[0];

			mapParams.put("id", id);

			note = restTemplate.getForObject(URL_BASE + SERVICE_NAME + "/notes/{id}", Note.class, mapParams);

		} else {

			note = (Note) proceedingJoinPoint.proceed();

		}

		return note;

	}

	@Around("execution(* " + SAVE + "(..))")
	public Note save(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		if (Boolean.parseBoolean(Utils.getProperty(SAVE))) {			

			Map<String, Long> mapParams = new HashMap<String, Long>();

			Long id = (Long) proceedingJoinPoint.getArgs()[0];

			mapParams.put("id", id);

			restTemplate.put(URL_BASE + SERVICE_NAME + "/notes/{id}", (Note) proceedingJoinPoint.getArgs()[1], mapParams);
			
			return restTemplate.getForObject(URL_BASE + SERVICE_NAME + "/notes/{id}", Note.class, mapParams);

		} else {

			return (Note) proceedingJoinPoint.proceed();

		}

	}

	@Around("execution(* " + DELETE + "(..))")
	public void delete(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		if (Boolean.parseBoolean(Utils.getProperty(DELETE))) {

			Map<String, Long> mapParams = new HashMap<String, Long>();

			Long id = (Long) proceedingJoinPoint.getArgs()[0];

			mapParams.put("id", id);

			restTemplate.delete(URL_BASE + SERVICE_NAME + "/notes/{id}", mapParams);

		} else {

			proceedingJoinPoint.proceed();

		}

	}

}