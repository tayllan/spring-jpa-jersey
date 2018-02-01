package br.com.cinq.spring.data.controller.test;

import java.io.IOException;
import java.util.List;

import br.com.cinq.spring.data.sample.controller.CityController;
import br.com.cinq.spring.data.sample.repository.CountryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.cinq.spring.data.sample.application.Application;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("unit")
public class CityControllerTest {

    @Inject
    private CityRepository cityRepository;

    @Inject
    private CountryRepository countryRepository;

    private CityController controller;

    private static final String json = "[\n" +
            "    {\n" +
            "        \"id\": 100,\n" +
            "        \"name\": \"Buenos Aires\",\n" +
            "        \"country\": {\n" +
            "            \"id\": 4,\n" +
            "            \"name\": \"Argentina\"\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 101,\n" +
            "        \"name\": \"Lima\",\n" +
            "        \"country\":{\n" +
            "            \"id\": 5,\n" +
            "            \"name\": \"Peru\"\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 102,\n" +
            "        \"name\": \"Cusco\",\n" +
            "        \"country\":{\n" +
            "            \"id\": 5,\n" +
            "            \"name\": \"Peru\"\n" +
            "        }\n" +
            "    }\n" +
            "]";

    @Before
    public void initializeController() {
        controller = new CityController(cityRepository);
    }

    @Test
    public void testGetCitiesFromInvalidJSON() {
        try {
            City[] notUsedCities = controller.getCitiesFromJSON("");
            Assert.assertTrue(false);
        } catch (Exception ex) {
            // Empty strings are not valid JSON.
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetCitiesFromEmptyJSON() {
        try {
            City[] emptyCities = controller.getCitiesFromJSON("[]");
            Assert.assertEquals(0, emptyCities.length);
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testGetCitiesFromJSON() {
        try {
            City[] cities = controller.getCitiesFromJSON(json);
            Assert.assertEquals(3, cities.length);

            Assert.assertEquals(100, cities[0].getId().intValue());
            Assert.assertEquals(4, cities[0].getCountry().getId().intValue());

            Assert.assertEquals(101, cities[1].getId().intValue());
            Assert.assertEquals(5, cities[1].getCountry().getId().intValue());

            Assert.assertEquals(102, cities[2].getId().intValue());
            Assert.assertEquals(5, cities[2].getCountry().getId().intValue());
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testSaveMultipleCities() throws IOException {
        CityController controller = new CityController(cityRepository);
        City[] cities = controller.getCitiesFromJSON(json);
        controller.saveMultipleCities(cities);

        Assert.assertEquals(
                1,
                countryRepository.findLikeName(cities[0].getCountry().getName()).size()
        );
        Assert.assertEquals(
                1,
                countryRepository.findLikeName(cities[1].getCountry().getName()).size()
        );
        Assert.assertEquals(1, countryRepository.findLikeName("Argentina").size());
        Assert.assertEquals(1, cityRepository.findLikeName("Lima").size());
    }

}

