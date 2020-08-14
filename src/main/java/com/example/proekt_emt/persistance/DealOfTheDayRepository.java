package com.example.proekt_emt.persistance;

import com.example.proekt_emt.model.DealOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealOfTheDayRepository extends JpaRepository<DealOfTheDay, Long> {

    DealOfTheDay findFirstByActive(boolean active);
}
