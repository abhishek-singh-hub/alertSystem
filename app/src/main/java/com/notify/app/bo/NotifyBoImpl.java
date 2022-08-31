package com.notify.app.bo;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.app.dao.NotifyDao;
import com.notify.app.modal.Developer;
import com.notify.app.modal.Team;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;



@Service
public class NotifyBoImpl implements NotifyBo {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NotifyDao notifyDao;

	@Override
	public void createTeam(Team responseObject) throws Exception {
		logger.info("NotifyBoImpl :: createTeam()");
		
		//ObjectMapper mapper = new ObjectMapper();
		
		//Team team = mapper.readValue(mapper.writeValueAsString(responseObject), Team.class);
		notifyDao.createTeam(responseObject);
	}

	@Override
	public String notifyTeam(Integer teamId) {
		logger.info("NotifyBoImpl :: notifyTeam()");
		Developer developer = notifyDao.getDeveloperFromTeam(teamId);
		return notifyDeveloper(developer.getPhoneNo()+"").toString();
	}
	
	private JSONObject notifyDeveloper(String phoneNumber)  {
		logger.info("NotifyBoImpl :: notifyDeveloper()");
		
		String url="https://run.mocky.io/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f";
		try {
			
			JSONObject requestBody = new JSONObject();
			requestBody.put("phone_number", phoneNumber);
			requestBody.put("message", "To Many 500 internal server error");
			
			HttpEntity<String> httpRequest = new HttpEntity<String>(requestBody.toString());
			
			RestTemplate restTemplate = getRestTemplate();
			ResponseEntity<JSONObject> alertResponse = restTemplate.postForEntity(url, httpRequest, JSONObject.class);
			logger.info("alertResponse.getBody() AKS "+ alertResponse.getBody().toString());
			return alertResponse.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject obj = new JSONObject();
			obj.put("errorMessage", e.getMessage());
			return obj;
		}
	}
	//this code is just to disable PKIX
	//sun.security.validator.ValidatorException: PKIX path building failed:
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	    TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
	}

	@Override
	public List<Developer> getTeamMember(Integer teamId) {
		logger.info("NotifyBoImpl :: getTeamMember()");
		
		return notifyDao.getTeamMember(teamId);
	}
	
	@Override
	public Team getTeam(Integer teamId) {
		logger.info("NotifyBoImpl :: getTeam()");
		
		return notifyDao.getTeam(teamId);
	}

	@Override
	public List<Team> getAllTeam() {
		logger.info("NotifyBoImpl :: getAllTeam()");
		return notifyDao.getAllTeam();
	}
}
