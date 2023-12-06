package br.com.rti.routes.configs;

import java.util.HashSet;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class HashSetAggregationStrategy implements AggregationStrategy {

	@SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Object newBody = newExchange.getIn().getBody();
		HashSet<Object> list = null;
		if (oldExchange == null) {
			list = new HashSet<Object>();
			list.add(newBody);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			list = oldExchange.getIn().getBody(HashSet.class);
			list.add(newBody);
			oldExchange.getIn().setBody(list);
			return oldExchange;
		}
	}
}