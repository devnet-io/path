# path

Path allows statically typed referces to methods and fields.

Resolve the property path using getters

```java
String path = Path.for(Album.class)
  .$(Album::getArtist)
  .$(Artist::getGenre)
  .$(Genre::getName)
  .resolve();
  
System.out.println(path) // artist.genere.name
```

Shorthand syntax (max depth 5)

```java
String path = Path.for(Album::getArtist, Artist::getGenre, Genre::getName).resolve()
  
System.out.println(path) // artist.genere.name
```
