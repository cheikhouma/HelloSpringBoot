package sn.edu.ept.git.dic2.HelloSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.edu.ept.git.dic2.HelloSpringBoot.entities.LoggingRequest;

@Repository
public interface LoggingRequestRepository extends JpaRepository<LoggingRequest, Long> {
}
