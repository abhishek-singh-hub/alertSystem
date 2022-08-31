package com.notify.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.notify.app.modal.Developer;
import com.notify.app.modal.DeveloperRepository;
import com.notify.app.modal.Team;
import com.notify.app.modal.TeamRepository;

@Repository
public class NotifyDaoImpl implements NotifyDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private DeveloperRepository developerRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void createTeam(Team team) {
		logger.info("NotifyDaoImpl :: createTeam()");
		Team resTeam = teamRepository.save(team);
		
	}

	@Override
	public Developer getDeveloperFromTeam(Integer teamId) {
		logger.info("NotifyDaoImpl :: getDeveloperFromTeam()");
		
		Query query = entityManager.createQuery("from Developer where teamId =:teamId");
		query.setParameter("teamId", teamId);
		
		List<Developer> developers = query.getResultList();

		return developers.get(0);;
	}

	@Override
	public List<Developer> getTeamMember(Integer teamId) {
		logger.info("NotifyDaoImpl :: getTeamMember()");
		
		Query query = entityManager.createQuery("from Developer where teamId =:teamId");
		query.setParameter("teamId", teamId);

		return query.getResultList();
	}
	
	@Override
	public Team getTeam(Integer teamId) {
		logger.info("NotifyDaoImpl :: getTeamMember()");
		
		Query query = entityManager.createQuery("from Team where id =:teamId");
		query.setParameter("teamId", teamId);
		
		return (Team) query.getSingleResult();
	}
	
	@Override
	public List<Team> getAllTeam() {
		logger.info("NotifyDaoImpl :: getAllTeam()");
		
		Query query = entityManager.createQuery("from Team");
		
		return query.getResultList();
	}
	
}
