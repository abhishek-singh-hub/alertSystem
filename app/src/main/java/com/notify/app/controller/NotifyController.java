package com.notify.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notify.app.bo.NotifyBo;
import com.notify.app.modal.Developer;
import com.notify.app.modal.Team;


@RestController
@RequestMapping("/v1/api/notify/team")
public class NotifyController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private NotifyBo notifyBO;

	@GetMapping("/isserviceup")
	public String isServiceUp() {
		logger.info("NotifyController :: isServiceUp()");
		return "Service is up";
	}

	@PostMapping("/create")
	public ResponseEntity<String> createTeam(@RequestBody Team responseObject) {
		logger.info("NotifyController :: createTeam()");
		String responseMsg = "";
		try {
			notifyBO.createTeam(responseObject);
			responseMsg = "Team created";
		} catch (Exception e) {
			responseMsg = e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity<String>(responseMsg, HttpStatus.OK);
	}

	@GetMapping("/alert")
	public ResponseEntity<String> notifyTeam(@RequestParam Integer teamId){
		logger.info("NotifyController :: notifyTeam()");
		
		String responseMsg = "";
		try {
			responseMsg = notifyBO.notifyTeam(teamId);
		} catch (Exception e) {
			responseMsg = e.getMessage();
			e.printStackTrace();
		}
		return new ResponseEntity<String>(responseMsg, HttpStatus.OK);
	}
	
	@GetMapping("/members")
	public ResponseEntity<List> getTeamMember(@RequestParam Integer teamId){
		logger.info("NotifyController :: notifyTeam()");
		List<Developer> list = null;
		try {
			list = notifyBO.getTeamMember(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{teamId}")
	public ResponseEntity<Team> getTeam(@PathVariable Integer teamId){
		logger.info("NotifyController :: notifyTeam()");
		Team team = null;
		try {
			team = notifyBO.getTeam(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Team>(team, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Team>> getAllTeam(){
		logger.info("NotifyController :: getAllTeam()");
		List<Team> teams = null;
		try {
			teams = notifyBO.getAllTeam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
	}

}
