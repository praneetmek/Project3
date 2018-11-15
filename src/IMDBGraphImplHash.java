import java.io.File;
import java.io.IOException;
import java.util.*;

public class IMDBGraphImplHash implements IMDBGraph {
// Data
    private int _start;
    private Scanner _sActors;
    private Scanner _sActresses;
    private ArrayList<ActorNode> _alphabetizedActorList;
    private HashMap<String, ActorNode> _actors;
    private HashMap<String, MovieNode> _movies;

// Constructor

    /**
     * @param actorsFile    the file with the actors' information
     * @param actressesFile the file with the actresses' information
     * @throws IOException
     */
    public IMDBGraphImplHash(String actorsFile, String actressesFile) throws java.io.IOException {
        _start = 0;
        _sActors = new Scanner(new File(actorsFile), "ISO-8859-1");
        _sActresses = new Scanner(new File(actressesFile), "ISO-8859-1");
        _movies = new HashMap<>();
        _actors = new HashMap<>();
        _alphabetizedActorList = new ArrayList<>();
        loadDataForActors();
    }

// Methods

    /**
     * Gets all the actor nodes in the graph.
     * @return  a collection of all the actor and actress nodes in the graph.
     */
    public Collection<? extends Node> getActors() {
        return _alphabetizedActorList;
    }

    /**
     * Gets all the movie nodes in the graph.
     * @return  a collection of all the movie nodes in the graph.
     */
    public Collection<? extends Node> getMovies() {
        return _movies.values();
    }

    /**
     * Returns the movie node having the specified name.
     * @param name  the name of the requested movie
     * @return  the movie node associated with the specified name or null if no such movie exists.
     */
    public Node getMovie(String name) {
        return _movies.get(name);
    }

    /**
     * Returns the actor node having the specified name.
     * @param name  the name of the requested actor
     * @return  the actor node associated with the specified name or null if no such actor exists.
     */
    public Node getActor(String name) {
        return _actors.get(name);
    }

    /**
     *
     */
    private void loadDataForActors() {
        loadData(_sActors);
        loadData(_sActresses);
        _alphabetizedActorList.addAll(_actors.values());
        Collections.sort(_alphabetizedActorList);
    }

    /**
     *
     * @param sc
     */
    private void loadData(Scanner sc) {
        while(sc.hasNextLine()) {
            String line;
            ActorNode actor = new ActorNode("Fake Actor");
            MovieNode movie;

            // Finds the line before the list of actors and their respective movie lists starts.
            if(sc.hasNext("----\t\t\t------")) {
                _start = 1;
            }

            // Starts only after the starting point has been reached.
            if(_start == 1) {
                line = sc.nextLine();
                int tabIdx = line.indexOf("\t");

                // When it's a new actor list, instantiates a new Actor object
                if(tabIdx > 0) {
                    String actorName = line.substring(0, tabIdx);
                    actor = new ActorNode(actorName);
                }

                // When it's definitely a movie, instantiates a new Movie object and adds the actor&movie to the list
                if(isMovie(line)) {
                    String shorterLine = line.substring(tabIdx+1);
                    int secondTabIdx = shorterLine.indexOf("\t");

                    shorterLine = shorterLine.substring(secondTabIdx);
                    int endYear = shorterLine.indexOf(")");

                    String movieName = line.substring(tabIdx+1, endYear+1);
                    if(_movies.containsKey(movieName))
                        movie = _movies.get(movieName);
                    else {
                        movie = new MovieNode(movieName);
                        _movies.put(movieName, movie);
                    }

                    actor.addMovie(movie);
                    movie.addActor(actor);

                    if(!_actors.containsKey(actor.getName()))
                        _actors.put(actor.getName(), actor);
                }
            }
            sc.nextLine();
        }
    }

    /**
     *
     * @param line
     * @return
     */
    private boolean isMovie(String line) {
        boolean movie = true;
        int tabIdx = line.indexOf("\t");
        if(line.indexOf("\"")==tabIdx+1 || line.contains("(TV)"))
            movie = false;
        return movie;
    }
}
