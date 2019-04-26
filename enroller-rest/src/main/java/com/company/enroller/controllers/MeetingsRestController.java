package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

import javafx.print.Collation;

@RestController
@RequestMapping("/meetings")
public class MeetingsRestController {
	
	@Autowired
	MeetingService meetingService;
	
	@Autowired
	ParticipantService participantService;
	
	//Pobieranie listy wszystkich spotkań
	@RequestMapping(value="", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(){
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	//Pobieranie listy pojedyncznego spotkania
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOneMeeting(@PathVariable("id") long id){
		Meeting meeting = meetingService.findMeeteingById(id);
		if (meeting == null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}	

	//Dodawanie spotkań
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting){
		
		// Sprawdzam czy istnieje spotkanie
		Meeting findMeeting = meetingService.findMeeteingById(meeting.getId());
		if (findMeeting != null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
	}

	// Usuwanie spotkan
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id){
		Meeting meeting = meetingService.findMeeteingById(id);
		if(meeting == null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.delete(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}	



}
