package com.notify.app.dao;

import java.util.List;

import com.notify.app.modal.Developer;
import com.notify.app.modal.Team;

public interface NotifyDao {

	public void createTeam(Team team);

	public Developer getDeveloperFromTeam(Integer teamId);

	public List<Developer> getTeamMember(Integer teamId);

	public Team getTeam(Integer teamId);

	List<Team> getAllTeam();

}
