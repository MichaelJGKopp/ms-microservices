package io.michaeljgkopp.github.microservices.currencyexchangeservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    @GetMapping("/sample-api")

    // name="default" returns error only after failing three times else
    // define in application.properties: resilience4j.retry.instances.sample-api.max-attempts=5
    // @Retry(name="sample-api", fallbackMethod = "hardCodedResponse")

    // the circuit breaker trips if failure percentage is higher than threshold
    // it then transitions from closed to open, then it fails fast until a recovery time when it becomes half-open
    // and finally closes again
    // @CircuitBreaker(name="sample-api", fallbackMethod = "hardCodedResponse")

    // not more than 10_000 calls per 10s to the sample-api
    @RateLimiter(name="default")

    public String sampleApi() {

        logger.info("Calling sample api ...");

        // supposed to cause failure
//        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
//                "http://localhost:88/some-dummy-url", String.class);
//
//        return responseEntity.getBody();

        return "sample api";
    }

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);


        public String hardCodedResponse(Exception ex) {
            return "Fallback response";
        }
}
