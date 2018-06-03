package statistics;

import java.time.Duration;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

	private static final String TEMPLATE = "{ \"sum\": %s, \"avg\": %s, \"max\": %s, \"min\": %s, \"count\": %s } ";

	@RequestMapping("/statistics")
	public String greeting() {

		Instant now = Instant.now();
		long limit = TimeUnit.SECONDS.toSeconds( 60 );
		
		
		DoubleSummaryStatistics stats = Store.transactions.stream()
	    		.filter(t -> Duration.between( t.getTimestamp() , now ).getSeconds() <= limit)
		    	.collect(
		            Collectors.summarizingDouble(Transaction::getAmount));

		return String.format(TEMPLATE, stats.getSum(), stats.getAverage(), stats.getMax(), stats.getMin(),
				stats.getCount());
	}

}
