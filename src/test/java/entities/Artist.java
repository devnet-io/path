package entities;

import lombok.Data;

import java.util.List;

@Data
public class Artist {
    private String name;
    private List<Album> albums;
    public Genre genre;

    public Genre getDog(String name) {
        return this.getGenre();
    }
}
