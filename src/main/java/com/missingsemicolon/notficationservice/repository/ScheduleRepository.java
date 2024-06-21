package com.missingsemicolon.notficationservice.repository;


import com.missingsemicolon.notficationservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule save(Schedule schedule);

    void deleteById(Long Id);

    List<Schedule> findAllByManagerId(Long managerId);

    List<Schedule> findByNextRunBefore(LocalDateTime dateTime);
}
