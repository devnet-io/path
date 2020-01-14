# path

Path allows strongly typed referces to methods and fields.

Resolve the property path using getters

```java
String path = Path.for(Album.class)
  .$(Album::getArtist)
  .$(Artist::getGenre)
  .$(Genre::getName)
  .resolve();
  
System.out.println(path); // artist.genere.name
```

Shorthand syntax (max depth 5)

```java
String path = Path.for(Album::getArtist, Artist::getGenre, Genre::getName).resolve();
  
System.out.println(path); // artist.genere.name
```

Resolve property path using fields

```java
String path = Path.for(Album.class)
  .$(album -> album.artist)
  .$(artist -> artist.genre)
  .$(genre -> genre.name)
  .resolve();
  
System.out.println(path); // artist.genere.name
```

Shorthand syntax (max depth 5)

```java
String path = Path.for(album -> album.artist, artist -> artist.genre, genre -> genre.name).resolve();
  
System.out.println(path); // artist.genere.name
```
