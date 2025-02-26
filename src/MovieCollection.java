import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private ArrayList<String> actors;
    private ArrayList<String> genres;

    private Movie[] top50Rated;
    private Movie[] top50Revenue;
    private int[] revenues;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void getTop50Rated() {
        ArrayList<Movie> sortedMovies = new ArrayList<Movie>();
        Movie currentMovie;
        double current;
        double next;
        for (int x = 0; x < movies.size(); x ++) {
            currentMovie = movies.get(x);
            if (sortedMovies.size() == 0) {
                sortedMovies.add(currentMovie);
            }
            else {
                if (currentMovie.getUserRating() > sortedMovies.get(0).getUserRating()) {
                    sortedMovies.add(0, currentMovie);
                }
                else if (currentMovie.getUserRating() < sortedMovies.get(sortedMovies.size() - 1).getUserRating()) {
                    sortedMovies.add(currentMovie);
                }

                else {
                    for (int y = 0; y < sortedMovies.size() - 1; y ++) {
                        current = sortedMovies.get(y).getUserRating();
                        next = sortedMovies.get(y + 1).getUserRating();
                        if ((currentMovie.getUserRating() < current) && (currentMovie.getUserRating() > next)) {
                            sortedMovies.add(y + 1, currentMovie);
                            break;
                        }
                        else if (current == currentMovie.getUserRating()) {
                            sortedMovies.add(y, currentMovie);
                            break;
                        }
                        else if (next == currentMovie.getUserRating()) {
                            if (!isMoviefound(currentMovie, sortedMovies)) {
                                sortedMovies.add(y + 1, currentMovie);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("sorted size = " + sortedMovies.size());

        for (int x = 0; x < 50; x ++) {
            top50Rated[x] = sortedMovies.get(x);
        }
    }

    public void getTop50Revenue() {

    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        this.sortMovieResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortMovieResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortCastResults(ArrayList<String> listToSort) {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String name = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && name.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, name);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {

        System.out.print("Enter a cast search name: ");
        String castName = scanner.nextLine();
        castName = castName.toLowerCase();
        String tempName;

        ArrayList<String> actorResults = new ArrayList<String>();
        for (String name : actors) {
            tempName = name.toLowerCase();
            if (tempName.contains(castName)) {
                actorResults.add(name);
            }
        }
        sortCastResults(actorResults);

        for (int x = 0; x < actorResults.size(); x ++) {
            System.out.println((x + 1) + ". " + actorResults.get(x));
        }

        System.out.println("Which actor would you like to learn more about? ");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        String targetActor = actorResults.get(num - 1);

        ArrayList<Movie> movieResults = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getCast().contains(targetActor)) {
                movieResults.add(movie);
            }
        }
        this.sortMovieResults(movieResults);

        for (int x = 0; x < movieResults.size(); x ++) {
            System.out.println((x + 1) + ". " + movieResults.get(x).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        num = scanner.nextInt();
        displayMovieInfo(movieResults.get(num - 1));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String userInput = scanner.nextLine();
        userInput = userInput.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getKeywords().contains(userInput)) {
                results.add(movie);
            }
        }
        this.sortMovieResults(results);

        for (int x = 0; x < results.size(); x ++) {
            System.out.println((x + 1) + ". " + results.get(x).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        displayMovieInfo(results.get(num - 1));

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        for (int x = 0; x < genres.size(); x ++) {
            System.out.println((x + 1) + "." + genres.get(x));
        }
        System.out.println("Which genre are you interested in? ");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        String targetGenre = genres.get(num);

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(targetGenre)) {
                results.add(movie);
            }
        }
        this.sortMovieResults(results);

        for (int x = 0; x < results.size(); x ++) {
            System.out.println((x + 1) + ". " + results.get(x).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        num = scanner.nextInt();
        displayMovieInfo(results.get(num - 1));

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        getTop50Rated();

        for (int x = 0; x < 50; x ++) {
            System.out.println((x + 1) + "." + top50Rated[x].getTitle() + " " + top50Rated[x].getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        displayMovieInfo(top50Rated[num - 1]);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue() {
        getTop50Revenue();

        System.out.println();
        System.out.println("length of top50Revenue: " + top50Revenue.length);
        System.out.println();

        for (int x = 0; x < 50; x ++) {
            System.out.println((x + 1) + "." + top50Revenue[x].getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int num = scanner.nextInt();
        displayMovieInfo(top50Revenue[num - 1]);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private boolean isActorFound(String actor) {
        for (int x = 0; x < actors.size(); x ++) {
            if (actors.get(x).equals(actor)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMoviefound(Movie target, ArrayList<Movie> movieArray) {
        String targetTitle = target.getTitle().toLowerCase();
        String title;
        for (int x = 0; x < movieArray.size(); x ++) {
            title = movieArray.get(x).getTitle())
            if (target.getTitle().equals() {
                return true;
            }
        }
        return false;
    }

    private boolean isGenreFound(String genre) {
        for (int x = 0; x < genres.size(); x ++) {
            if (genres.get(x).equals(genre)) {
                return true;
            }
        }
        return false;
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();
            actors = new ArrayList<String>();
            genres = new ArrayList<String>();
            top50Rated = new Movie[50];
            top50Revenue = new Movie[50];

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];

                String[] actorsArray = cast.split("\\|");
                for (String actor : actorsArray) {
                    if (!isActorFound(actor)) {
                        actors.add(actor);
                    }
                }

                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);

                String thisMovieGenre = movieFromCSV[7];
                String[] genreArray = thisMovieGenre.split("\\|");
                for (String genre : genreArray) {
                    if (!isGenreFound(genre)) {
                        genres.add(genre);
                    }
                }

                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, thisMovieGenre, userRating, year, revenue);
                movies.add(nextMovie);
            }
            revenues = new int[movies.size()];
            for (int x = 0; x < movies.size(); x ++ ) {
                revenues[x] = movies.get(x).getRevenue();
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}