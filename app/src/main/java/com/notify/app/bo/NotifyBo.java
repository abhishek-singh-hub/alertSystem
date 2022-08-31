package com.notify.app.bo;

import java.util.List;

import com.notify.app.modal.Developer;
import com.notify.app.modal.Team;

public interface NotifyBo {

	public void createTeam(Team responseObject) throws Exception;

	public String notifyTeam(Integer teamId);

	public List<Developer> getTeamMember(Integer teamId);

	public Team getTeam(Integer teamId);

	public List<Team> getAllTeam();

}
