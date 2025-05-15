package br.com.portopirata.wealth.market.exchange.adapter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SunSwapExchangeAdapter implements IExchangeAdapter 
{
    private final HttpClient httpClient;
	private final ObjectMapper mapper;

	public SunSwapExchangeAdapter( ObjectMapper mapper, HttpClient httpClient ) 
	{
        this.httpClient = httpClient;
		this.mapper = mapper;
	}

	@Override
	public BigDecimal getPrice(String symbol)
	{

        try {
        	// String poolAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"; // hexadecimal TRON address (sem 0x)
        	// String contractAddressBase58 = "TThJt8zaJzJMhCEScH7zWKnp5buVZqys9x"; // Base58 (TRC20 token pool address)
        	String ownerAddressBase58 = "TLVBrrz2bQn7pBivzw5hwaACQdBw9eWwyB"; // sua wallet TRON
            String contractAddressBase58 = "TV7nMn3RPaKvxRBgPhhrfLSjv9F5FjRk8j"; // contrato do pool V3
        	
        	BodyPublisher body = BodyPublishers.ofString( String.format(
                    "{\"owner_address\":\"%s\",\"contract_address\":\"%s\",\"function_selector\":\"slot0()\",\"call_value\":0,\"visible\":true,\"parameter\":\"3fc8cef3\"}",
                    ownerAddressBase58, contractAddressBase58 ) );
        	
        	String url = "https://api.trongrid.io/wallet/triggersmartcontract";

			HttpRequest request = HttpRequest.newBuilder()
	                .uri(new URI(url))
	                .header("Content-Type", "application/json")
	                .header("TRON-PRO-API-KEY", "2cad8602-8b48-4197-bfd7-771866db4ca7")
	                .POST(body)
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode responseNode = mapper.readTree( response.body() );
            String hex = responseNode.get("constant_result").get(0).asText();

            BigInteger sqrtPriceX96 = new BigInteger(hex, 16);
            BigDecimal sqrtPrice = new BigDecimal(sqrtPriceX96);
            BigDecimal divisor = BigDecimal.valueOf(Math.pow(2, 96));
            BigDecimal price = sqrtPrice.pow(2).divide(divisor.pow(2), 18, BigDecimal.ROUND_HALF_UP);

            return new BigDecimal( price.toPlainString() );
        } catch (Exception e) 
        {
        	throw new RuntimeException( e );
        }
	}
}
