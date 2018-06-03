package statistics;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class SummaryStatsTest {
	
	private static final String TEMPLATE = "{ \"sum\": %s, \"avg\": %s, \"max\": %s, \"min\": %s, \"count\": %o } ";

	
	public static void main(String[] args) {
		System.out.println(2323);
	}
	
	List<Transaction> transactions = new ArrayList<>();
	
	private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
	
	@Before
	public void setUp() {
		transactions.add(new Transaction(100.12, Instant.parse("2018-06-01T20:13:31Z")));
		transactions.add(new Transaction(142.65, Instant.parse("2018-06-01T20:13:31Z")));
		transactions.add(new Transaction(12.1, Instant.parse("2018-06-01T20:13:31Z")));
		transactions.add(new Transaction(184.90, Instant.parse("2018-06-01T20:13:32Z")));
		transactions.add(new Transaction(190.90, Instant.parse("2018-06-01T20:12:10Z")));
	}
	
	@Test
	public void double_summary_stats_stream_reduction_target() {

		Instant now = Instant.parse("2018-06-01T20:13:11Z");
		long limit = TimeUnit.SECONDS.toSeconds( 60 );
		
		System.out.println(Duration.between( transactions.get(0).getTimestamp() , now ).getSeconds());
		
	    DoubleSummaryStatistics stats = transactions.stream()
    		.filter(t -> Duration.between( t.getTimestamp() , now ).getSeconds() <= limit)
	    	.collect(
	            Collectors.summarizingDouble(Transaction::getAmount));

	    assertEquals(109.9425, stats.getAverage(), 0);

	    assertEquals(4, stats.getCount(), 0);

	    assertEquals(184.9, stats.getMax(), 0);

	    assertEquals(12.1, stats.getMin(), 0);

	    assertEquals(439.77, stats.getSum(), 0);
	    
	 /*   System.out.println(
	    String.format(template, stats.getSum(), stats.getAverage(), stats.getMax(), stats.getMin(),
				stats.getCount())
	    		);*/
	}

}
