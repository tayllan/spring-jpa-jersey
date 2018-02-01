package br.com.cinq.spring.data.sample.controller;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CityController {

    private CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City[] getCitiesFromJSON(String json) throws IOException {
        return (new ObjectMapper()).readValue(json, City[].class);
    }

    public void saveMultipleCities(City[] cities) {
        for (City city : cities) {
            this.cityRepository.save(city);
        }
    }

}
