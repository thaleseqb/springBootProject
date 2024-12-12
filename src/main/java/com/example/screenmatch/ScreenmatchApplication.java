package com.example.screenmatch;

import com.example.screenmatch.model.EpisodeData;
import com.example.screenmatch.model.SeasonData;
import com.example.screenmatch.model.SerieData;
import com.example.screenmatch.service.ApiSerie;
import com.example.screenmatch.service.DataConvert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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

		DataConvert converter = new DataConvert();
		SerieData data = converter.getData(json, SerieData.class);

		Integer totalSeasons = data.totalSeasons();

		System.out.println(data);
		json = apiSerie.returnData("http://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=70066399");
		EpisodeData episode = converter.getData(json, EpisodeData.class);
		System.out.println(episode);
		SeasonData season;
		List<SeasonData> seasons = new ArrayList<>();

		for (int index=1; index <= totalSeasons; index++) {
			json = apiSerie.returnData(
					"http://www.omdbapi.com/?t=gilmore+girls&Season="+index+"&apikey=70066399"
			);
			season = converter.getData(json, SeasonData.class);
			seasons.add(season);
		}
		seasons.forEach(System.out::println);
	}
}
