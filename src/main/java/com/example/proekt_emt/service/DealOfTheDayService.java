package com.example.proekt_emt.service;

import com.example.proekt_emt.model.DealOfTheDay;

public interface DealOfTheDayService {
    DealOfTheDay save(DealOfTheDay d);
    DealOfTheDay findFirstByActive(boolean active);
    void deleteById(Long id);
}
