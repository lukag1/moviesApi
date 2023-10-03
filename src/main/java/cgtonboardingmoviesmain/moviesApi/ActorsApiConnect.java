package cgtonboardingmoviesmain.moviesApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ActorsApiConnect {
    @Value("${actorsImage.url}")
    private String actorsImage;

    RestTemplate restTemplate;

    public ActorsApiConnect(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getActorImage(int id){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(actorsImage, String.class, id);
        String responseBody = responseEntity.getBody();
        return responseBody;
    }
}
