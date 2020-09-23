package com.turnyur.springconsumerest;

import com.turnyur.springconsumerest.model.CovidCountryTotal;
import com.turnyur.springconsumerest.model.CovidTotal;
import com.turnyur.springconsumerest.service.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SpringConsumeRestApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(SpringConsumeRestApplication.class);
	@Autowired
	RestClient restClient;
	public static void main(String[] args) {
		SpringApplication.run(SpringConsumeRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*try {
			CovidTotal covidTotal = restClient.getTotals().get(0);
			if(covidTotal==null){
				log.info("Empty Response!");
			}else{
				log.info(covidTotal.toString());
			}
		}catch (Exception e){
			log.info(e.getMessage());
		}
*/

	}

	@Scheduled(cron = "0 * * ? * *") //Every minutes
	//Every 5 minutes (cron="0 */5 * ? * *")
	public void perform() throws Exception
	{
		//covidTotal();
		//covidCountryTotal();
	}

	public void covidTotal(){
		try {
			CovidTotal covidTotal = restClient.getTotals().get(0);
			if(covidTotal==null){
				log.info("Empty Response!");
			}else{
				log.info(covidTotal.toString());
			}
		}catch (Exception e){
			log.info(e.getMessage());
		}
	}

	public void covidCountryTotal(){
		try {
			String country = "Nigeria";
			CovidCountryTotal covidTotalByCountry = restClient.getCovidTotalByCountry(country);
			if(covidTotalByCountry==null){
				log.info("Empty Response!");
			}else{
				log.info(covidTotalByCountry.toString());
			}
		}catch (Exception e){
			log.info(e.getMessage());
		}
	}

}
