package entities;

import lombok.Data;

import java.util.Date;

@Data
public class Album {
    private String name;
    private Date releaseDate;
    private Genre genre;
    private Artist artist;

    public Genre getGenereByThreeInputParams(String one, String two, String three) {
        return null;
    }
}
