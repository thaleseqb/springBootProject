package com.example.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double votes;
    private LocalDate launchingDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.number();

        try {
            this.votes = Double.valueOf(episodeData.votes());
        } catch (NumberFormatException exception) {
            this.votes = 0.0;
        }

        try {
            this.launchingDate = LocalDate.parse(episodeData.launchingDate());
        } catch (DateTimeParseException exception) {
            this.launchingDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return episodeNumber;
    }

    public void setNumber(Integer number) {
        this.episodeNumber = number;
    }

    public Double getVotes() {
        return votes;
    }

    public void setVotes(Double votes) {
        this.votes = votes;
    }

    public LocalDate getLaunchingDate() {
        return launchingDate;
    }

    public void setLaunchingDate(LocalDate launchingDate) {
        this.launchingDate = launchingDate;
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", votes=" + votes +
                ", launchingDate=" + launchingDate;
    }
}
