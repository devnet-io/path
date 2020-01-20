import entities.Album;
import entities.Artist;
import entities.Genre;
import io.devnet.util.path.Path;
import jodd.pathref.Pathref;

import java.util.Objects;

public class Test {

    public static void main(String[] args) {
        /*for (int i = 0; i < 100; i++) {
            run();
        }*/
        Album a = new Album();
        Artist ar = new Artist();

        Genre g = new Genre();
        g.setName(null);

        ar.setGenre(g);
        a.setArtist(ar);

        Path<Album, String> s = Path.of(Album.class)
                .$(Album::getArtist)
                .$(Artist::getGenre)
                .$(Genre::getName);

        System.err.println(s.resolve());
        System.err.println(s.evaluateField(a));

        /*String s = Path.of(Album.class)
                .$(Album::getArtist)
                .$(Artist::getGenre)
                .$(Genre::getName)
                .branch(Objects::isNull, (a) -> "hi", a -> a)
                .evaluate(a)


                .$(String::equals, "dog")
                .evaluate();





                //.pushUnsafe("getDog", ratArg)


        // useful for validation!!!



        // later that eiving (uhhhhh 4:24)
        // this is actually a revolutionary way of doing reflection?
        // can use it to get annotations

        // pretty much anything

        // strongly typed reflection for java!

        Album a = new Album();
        Artist ar = new Artist();

        Genre g = new Genre();
        g.setName("hi");

        ar.setGenre(g);
        a.setArtist(ar);

        Path<Album, Integer> path2 = Path.of(Album.class)
                .$(Album::getArtist)
                .$(Artist::getDog, "sasquatch!a") // .$((artist, arg1) -> artist.getDog(arg1), "Hi!")
                .$(String::length);

        System.err.println(path.resolve());



        System.err.println(path2.evaluate(a));

        /*new core.tests.performance.PerformanceTest() {
            @Override
            protected void runTest() {
                Pathref<Album> ref = Pathref.on(Album.class);



                //System.err.println(ref.path());

                String path = ref.path(ref.to().getArtist().getGenre().getName());
            }

            @Override
            protected String getName() {
                return "proxetta";
            }
        }.run();

        new core.tests.performance.PerformanceTest() {
            @Override
            protected void runTest() {
                Path path = Path.of(Album.class)
                        .$(Album::getArtist)
                        .$(Artist::getGenre)
                        .$(Genre::getName);

                //System.err.println(path.resolve());
            }

            @Override
            protected String getName() {
                return "path";
            }
        }.run();*/
    }

    public static void run() {
        doInTimer(() -> {

            Album a = new Album();
            a.setName("hi!");
            System.err.println(a.getName());
        }, "print");

        doInTimer(() -> {
            Pathref<Album> ref = Pathref.on(Album.class);

            ref.path(ref.to().getArtist().getGenre().getName());

            System.err.println(ref.path());
        }, "proxetta");

        doInTimer(() -> {
            Path path = Path.of(Album.class)
                    .$(Album::getArtist)
                    .$(Artist::getGenre)
                    .$(Genre::getName);

            System.err.println(path.resolve());
        }, "path");
    }

    private static void doInTimer(Runnable task, String name) {
        long startTime = System.currentTimeMillis();
        task.run();
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println();
        System.out.println(name + " - " + totalTime + "ms");
        System.out.println();
    }
}
