package com.example.screenmatch;

import com.example.screenmatch.service.ApiSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApiSerie apiSerie = new ApiSerie();
		String json = apiSerie.returnData("http://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=70066399");
		System.out.println(json);

		json = apiSerie.returnData("");

		System.out.println(json);

	}
}
