package com.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Agent;


@Repository

public interface AgentRepository  extends WriteableRepository<Agent,UUID>{

	

	Optional<Agent> findByPoultryId(UUID poultryId);

	Optional<Agent> findByPhoneNo(String phoneNo);

	Optional<Agent> findByEmailId(String emailId);

	Optional<Agent> findByAgentName(String agentName);


}
