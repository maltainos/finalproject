package mz.gov.misau.sigsmi.ws.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService implements HealthIndicator {
	
	private final String LOGGER_SERVICE = "Logger Service";

	@Override
	public Health health() {
		
		if(isLoggerServiceGood()) {
			return Health.up().withDetail(LOGGER_SERVICE, "Service is running").build();
		}
		return Health.down().withDetail(LOGGER_SERVICE, "Service is not available").build();
	}

	private boolean isLoggerServiceGood() {
		
		
		return true;
	}

}
