package edu.miu.carrental;

import com.google.gson.Gson;
import edu.miu.carrental.domain.dto.CarDto;
import edu.miu.carrental.domain.dto.CarsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author bazz
 * Apr 25 2023
 * 12:50
 */
@Service
public class CarFleetClient {
    @Autowired AppConfiguration appConfiguration;
    RestTemplate restTemplate = new RestTemplate();

    String fleetBaseUrl = appConfiguration.getFleetBaseUrl();

    Logger logger = LoggerFactory.getLogger(CarFleetClient.class);

    @Autowired
    Gson gson;


    public CarsDto searchCarFromFleet(String searchBy, String value){
        if(fleetBaseUrl.endsWith("/")){
            fleetBaseUrl = fleetBaseUrl.substring(0,fleetBaseUrl.length()-1);
        }
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(fleetBaseUrl+"/search")
                .queryParam("searchBy", searchBy)
                .queryParam("value", value);
        CarsDto carsDto = restTemplate.getForObject(uriBuilder.toUriString(), CarsDto.class);
        logger.info("Car DTO REST Response searchCarFromFleet()::: {}",gson.toJson(carsDto));
        return carsDto;

    }

    public CarsDto reserveCarInFleet(String licensePlate){
        if(fleetBaseUrl.endsWith("/")){
            fleetBaseUrl = fleetBaseUrl.substring(0,fleetBaseUrl.length()-1);
        }

        CarsDto carsDto = restTemplate.getForObject(fleetBaseUrl+"/{licensePlate}/reserve", CarsDto.class,licensePlate);
        logger.info("Car DTO REST Response reserveCarInFleet()::: {}",gson.toJson(carsDto));
        return carsDto;

    }

}
