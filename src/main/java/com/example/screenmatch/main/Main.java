package com.example.screenmatch.main;

import com.example.screenmatch.model.EpisodeData;
import com.example.screenmatch.model.SeasonData;
import com.example.screenmatch.model.SerieData;
import com.example.screenmatch.service.ApiSerie;
import com.example.screenmatch.service.DataConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner scan = new Scanner(System.in);
    private final String BASEURL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=70066399";
    private ApiSerie apiSerie = new ApiSerie();
    private DataConvert converter = new DataConvert();

    public void exhibitData() {
        System.out.println("Type the serie's name");
        String serieName = scan.nextLine();
        String address = BASEURL + serieName.replace(" ", "+") + API_KEY;

        String json = apiSerie.returnData(address);
        var data = converter.getData(json, SerieData.class);
        System.out.println(data);

        SeasonData season;
        List<SeasonData> seasons = new ArrayList<>();

        for (int index = 1; index <= data.totalSeasons(); index++) {
            address = BASEURL + serieName.replace(" ", "+") +"&season=" + index + API_KEY;
            json = apiSerie.returnData(address);
            season = converter.getData(json, SeasonData.class);
            seasons.add(season);
        }

//        seasons.forEach(System.out::println); with this command we can reduce code syntax

        seasons.forEach(t -> {
            int sizeList = t.episodeList().size();
            List<EpisodeData> episodeData = t.episodeList();
            for (int index = 0; index < sizeList; index++) {
                String title = episodeData.get(index).title();
                System.out.println(title);
            }
        });
    }
}
