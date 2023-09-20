package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.AgentDAO;
import com.app.dto.AgentDTO;
import com.app.dto.AgentResponseDTO;
import com.app.entity.Agent;
import com.app.entity.User;
import com.app.repository.AgentRepository;
import com.app.repository.UserRepository;
import com.app.util.PasswordUtil;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class AgentService {
	private @NonNull AgentRepository agentRepository;
	private @NonNull UserRepository userRepository;
	@Autowired
	private @NonNull AgentDAO agentDAO;
	
	
		public void saveAgent(AgentDTO agentDTO) {
			Agent agent=new Agent();
			agent.setAgentName(agentDTO.getAgentName());
			agent.setEmailId(agentDTO.getEmailId());
			agent.setPhoneNo(agentDTO.getPhoneNo());
			agent.setAddress(agentDTO.getAddress());
			agent.setDistrictId(agentDTO.getDistrictId());
			agent.setPoultryId(agentDTO.getPoultryId());
			agent.setStateId(agentDTO.getStateId());
			agent.setCountryId(agentDTO.getCountryId());
			Agent agentsave= agentRepository.save(agent);
		  User user =new User();
		  String ecryptedPassword = PasswordUtil.getEncryptedPassword(agentDTO.getPhoneNo());
		  //user.setAgentId(agentsave.getId());
		  user.setFullName(agentDTO.getAgentName());
		  user.setUserName(agentDTO.getAgentName());
		  user.setPassword(ecryptedPassword);
		  user.setEmail(agentDTO.getEmailId());
		  user.setPhoneNo(agentDTO.getPhoneNo());
		  user.setAddress(agentDTO.getAddress());
		  user.setUserRoleId(agentDTO.getRoleId());
		  user.setChangePassword(false);
		  user.setForcePasswordChange(false);
		  userRepository.save(user);	
		
	}

		public Object getAll(Agent agent) {
			return agentDAO.findAll();
		}

		public void saveOrUpdate(Agent agent) {
			agentRepository.saveAndFlush(agent);
			
		}

		public void deleteById(UUID id) {
			agentRepository.deleteById(id);

		}

		public List<AgentResponseDTO> getByPoultryId(UUID poultryId) {
			return agentDAO.findBypoultryId(poultryId);
		}

		public Object getByDistrictId(UUID districtId) {
			return agentDAO.findByDistrictId(districtId);
		}

		public Object getById(UUID id) {
			return agentDAO.findById(id);
		}

		public Optional<Agent> getid(UUID id) {
			return agentRepository.findById(id);
		}

		public Agent findByNameExcludeId(UUID poultryId, UUID id) {
			return agentDAO.findByNameExcludeId(poultryId,id);
		}

		public Optional<Agent> getByPhoneNo(String phoneNo) {
			return agentRepository.findByPhoneNo(phoneNo);
		}

		public Optional<Agent> findByUserEmail(String emailId) {
			return agentRepository.findByEmailId(emailId);
		}

		public Optional<Agent> findByAgentPoultryId(UUID poultryId) {
			return agentRepository.findByPoultryId(poultryId);
		}

		public Optional<Agent> findById(UUID id) {
			return agentRepository.findById(id);
		}

		public Optional<Agent> getByAgentName(String agentName) {
			return agentRepository.findByAgentName(agentName);
		}

		public Agent findByUserEmailExcludeId(String emailId, UUID id) {
			return agentDAO.findByUserEmailExcludeId(emailId,id);
		}

		public Agent getByPhoneNoExcludeId(String phoneNo, UUID id) {
			return agentDAO.getByPhoneNoExcludeId(phoneNo,id);
		}

		public Agent getByAgentNameExcludeId(String agentName, UUID id) {
			return agentDAO.getByAgentNameExcludeId(agentName,id);
		}



		

		

}
