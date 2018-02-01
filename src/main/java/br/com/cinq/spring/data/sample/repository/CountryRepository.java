package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    @Override
    Country findOne(Integer id);

    @Override
    List<Country> findAll();

    @Query("SELECT c FROM Country c WHERE c.name LIKE %:name%")
    List<Country> findLikeName(@Param("name") String name);

}