package entities;

import lombok.Data;

import java.util.List;

@Data
public class Artist {
    private String name;
    private List<Album> albums;
    private Genre genre;

    public String getDog(String name) {
        return "doggo: " + name;
    }
}
