package statistics;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
public class TransactionsController {

	@RequestMapping(name = "/transactions", method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Transaction input) throws JsonProcessingException {
		
		
	//	log(input);
		
		Instant now = Instant.now();
		Duration d = Duration.between( input.getTimestamp() , now );
		
		long secondsElapsed = d.getSeconds() ;
		
		long limit = TimeUnit.SECONDS.toSeconds( 60 );
		
		//System.out.println(secondsElapsed +" > "+limit);
		
		if( secondsElapsed > limit ) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			Store.transactions.add(input);
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		}
		
	}

	private void log(Transaction input) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println(mapper.writeValueAsString(input));
	}

}
