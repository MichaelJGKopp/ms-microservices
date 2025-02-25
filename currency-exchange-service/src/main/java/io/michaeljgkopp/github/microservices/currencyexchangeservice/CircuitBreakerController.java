package io.michaeljgkopp.github.microservices.currencyexchangeservice;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);


        @GetMapping("/sample-api")
        @Retry(name="sample-api")  // name="default" returns error only after failing three times
        // define in application.properties: resilience4j.retry.instances.sample-api.max-attempts=5
        public String sampleApi() {

            logger.info("Calling sample api ...");

            // supposed to cause failure
            ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
                    "http://localhost:88/some-dummy-url", String.class);

            return responseEntity.getBody();
        }
}
