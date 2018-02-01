package br.com.cinq.spring.data.sample.resource;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import br.com.cinq.spring.data.sample.repository.CountryRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Component
public class CityResource {

    @Inject
    private CityRepository cityRepository;

    @Inject
    private CountryRepository countryRepository;

    @GET
    @Path("/cities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> cities(@DefaultValue("") @QueryParam("country")String countryName) {
        return countryRepository.findLikeName(countryName)
                .stream()
                .map(cityRepository::findByCountry)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
