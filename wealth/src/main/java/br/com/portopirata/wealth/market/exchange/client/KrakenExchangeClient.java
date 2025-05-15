package br.com.portopirata.wealth.market.exchange.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KrakenExchangeClient implements IExchangeClient 
{
    private final HttpClient httpClient;
	private final ObjectMapper mapper;

	public KrakenExchangeClient( ObjectMapper mapper, HttpClient httpClient ) 
	{
        this.httpClient = httpClient;
		this.mapper = mapper;
	}

	@Override
	public BigDecimal getPrice(String symbol)
	{
		try {
			String url = "https://api.kraken.com/0/public/Ticker?pair=" + symbol;
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(new URI(url))
	                .GET()
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	        JsonNode rootNode = mapper.readTree(response.body());
	        JsonNode resultNode = rootNode.get("result");
	        String resultKey = resultNode.fieldNames().next();
	        return new BigDecimal(resultNode.get(resultKey).get("c").get(0).asText() );
		} catch (IOException | URISyntaxException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
