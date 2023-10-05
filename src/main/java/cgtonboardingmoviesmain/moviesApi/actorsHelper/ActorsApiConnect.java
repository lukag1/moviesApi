package cgtonboardingmoviesmain.moviesApi.actorsHelper;

import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ActorsApiConnect {
    @Value("${actorsImage.url}")
    private String actorsImage;

    RestTemplate restTemplate;

    LoggerImpl logger;

    public ActorsApiConnect(RestTemplate restTemplate,LoggerImpl logger) {
        this.restTemplate = restTemplate;
        this.logger = logger;
    }

    public String getActorImage(int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Getting actor image name from actors api started");
        String responseBody = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(actorsImage, String.class, id);
            responseBody = responseEntity.getBody();
            if (responseBody.isBlank()) {
                logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Error to get actors image");
            } else {
                logger.formatLogMessageGen(LogLevel.INFO, lm, "Getting actor image name from actors api is succesfull");
            }
        } catch (RestClientException e) {
            logger.formatLogMessageException(LogLevel.ERROR, lm, "RestClientException", "Error with Actors API");
            throw new RestClientException("Error with actors api");
        }
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Getting actor image name from actors api finished");
        return responseBody;
    }
}
