package com.turnyur.springconsumerest;

import com.turnyur.springconsumerest.model.CovidCountryTotal;
import com.turnyur.springconsumerest.service.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringConsumeRestApplicationTests {
	@Autowired
	RestClient restClient;
	@Test
	void contextLoads() {
	}

	@Test
	public void testGetNigeriaTotals() {
		CovidCountryTotal countryTotals = restClient.getCovidTotalByCountry("argentina");
		System.out.println("Country: " + countryTotals.getCountry());
		System.out.println("Confirmed: " + countryTotals.getConfirmed());
		System.out.println("Deaths: " + countryTotals.getDeaths());
		System.out.println("Recovered: " + countryTotals.getRecovered());
		assertTrue(countryTotals.getConfirmed() >= 0);
	}

}
