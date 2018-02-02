package br.com.cinq.spring.data.sample.controller;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Controller
public class UploadController {

    @Inject
    private CityRepository cityRepository;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            String cities = new BufferedReader(new InputStreamReader(
                    file.getInputStream()
            )).lines().collect(Collectors.joining("\n"));
            CityController controller = new CityController(cityRepository);
            City[] citiesJSON = controller.getCitiesFromJSON(cities);
            controller.saveMultipleCities(citiesJSON);

            return "<h2>Cidades salvas com sucesso!</h2>" +
                    "<a href=\"/rest/cities\">Visualizar</a>";
        } catch (IOException ex) {
            return "<h2>Ocorreu algum erro</h2>" +
                    "<p>" + ex.getMessage() + "</p>";
        }
    }

}