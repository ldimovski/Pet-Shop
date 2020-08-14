package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.DealOfTheDay;
import com.example.proekt_emt.persistance.DealOfTheDayRepository;
import com.example.proekt_emt.service.DealOfTheDayService;
import org.springframework.stereotype.Service;

@Service
public class DealOfTheDayServiceImpl implements DealOfTheDayService {

    private final DealOfTheDayRepository dealOfTheDayRepository;

    public DealOfTheDayServiceImpl(DealOfTheDayRepository dealOfTheDayRepository){
        this.dealOfTheDayRepository = dealOfTheDayRepository;
    }

    @Override
    public DealOfTheDay save(DealOfTheDay d) {
        return this.dealOfTheDayRepository.save(d);
    }

    @Override
    public DealOfTheDay findFirstByActive(boolean active) {
        return this.dealOfTheDayRepository.findFirstByActive(active);
    }

    @Override
    public void deleteById(Long id) {
        this.dealOfTheDayRepository.deleteById(id);
    }
}
