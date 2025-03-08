package com.example.screenmatch.main;

import com.example.screenmatch.model.Episode;
import com.example.screenmatch.model.EpisodeData;
import com.example.screenmatch.model.SeasonData;
import com.example.screenmatch.model.SerieData;
import com.example.screenmatch.service.ApiSerie;
import com.example.screenmatch.service.DataConvert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        List<EpisodeData> episodesData = seasons.stream()
                .flatMap(t -> t.episodeList().stream())
                .collect(Collectors.toList()); // this list is mutable. Using only .toList returns an immutable list

        episodesData.stream()
                .filter(t -> !t.votes().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::votes).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodeList().stream()
                        .map(d -> new Episode(t.number(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("From which date do you want to watch?");
        var year = scan.nextInt();
        scan.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> {
                    return (e.getLaunchingDate() != null)
                            && e.getLaunchingDate().isAfter(searchDate);
                }).forEach(e -> System.out.println(
                        "Season " + e.getSeason() +
                                " Episode " + e.getTitle() +
                                " Launching Date " + e.getLaunchingDate().format(formatter)
                ));
    }
}
