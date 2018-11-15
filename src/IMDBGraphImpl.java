import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Collections;

/**
 * An implementation of the IMDBGraph interface that parses through the given IMDB actor/actress files to
 * make a graph of actors and movies.
 */
public class IMDBGraphImpl implements IMDBGraph {

// Data
    private ArrayList<ActorNode> _actors;
    private ArrayList<MovieNode> _movies;
    private final Scanner _sActors;
    private final Scanner _sActresses;
    private final int NUM_INTRO_LINES_ACTORS=239;
    private final int NUM_INTRO_LINES_ACTRESSES=241;


// Constructor
    /**
     * @param actorsFile    the file with the actors' information
     * @param actressesFile the file with the actresses' information
     * @throws IOException
     */
    public IMDBGraphImpl(String actorsFile, String actressesFile) throws java.io.IOException {
        _sActors = new Scanner(new File(actorsFile), "ISO-8859-1");
        _sActresses = new Scanner(new File(actressesFile), "ISO-8859-1");
        _movies = new ArrayList<MovieNode>();
        _actors = new ArrayList<ActorNode>();
        loadDataForActors();
        if(actorsFile.equals("") || actressesFile.equals("")) {
            throw new IOException("This isn't working!!!!!");
        }
    }

// Methods

    /**
     * Loads the data from the given actor/actress files.
     */
    public void loadDataForActors(){

        //Actors
        for (int i=0; i<NUM_INTRO_LINES_ACTORS; i++){
           if(_sActors.hasNextLine())
               System.out.println(_sActors.nextLine());
        }
        loadDataFromTop(_sActors);

        //Actresses
        for (int i=0; i<NUM_INTRO_LINES_ACTRESSES; i++){
            if(_sActresses.hasNextLine())
                _sActresses.nextLine();
        }
        loadDataFromTop(_sActresses);

        alphabetizeActorList();
     }

    /**
     *
     */
    private void alphabetizeActorList() {
        Collections.sort(_actors);
     }

    /**
     * IDK HOW TO SAW WHAT THIS DOES
     * @param sc    the scanner that is reading through the file
     */
    private void loadDataFromTop(Scanner sc){
        String newLineForActor;
        String nameOfActor;
        String nameOfMovie;
        MovieNode currentMovie;

        ActorNode currentActor = new ActorNode("Fake Person who doesn't actually exist in the IMDB Database");

        while(sc.hasNextLine()) {
             newLineForActor = sc.nextLine();
             System.out.println(newLineForActor);

             if(newLineForActor.indexOf("\t")!=-1) {                                // if not new line
                 if(newLineForActor.indexOf("\t")>0) {                              // line where an actor introduced
                     nameOfActor = getActorName(newLineForActor);
                     currentActor = new ActorNode(nameOfActor);
                 }

                 if(isMovie(newLineForActor)) {                                   // if that line is a movie
                     nameOfMovie = getMovieName(newLineForActor);
                     currentMovie = movieListHasMovie(nameOfMovie);
                     _movies.add(currentMovie);
                     currentActor.addMovie(currentMovie);
                     currentMovie.addActor(currentActor);
                 }
             }
             else {                                                                 // if new line
                 if (currentActor.getNeighbors() != null)                           // only adds the actors who have acted in movies
                    _actors.add(currentActor);
             }
        }
     }

    /**
     * Gets the name of the movie.
     * @param s the string containing the movie title within it
     * @return  the name of the movie in the format "[title] (year)"
     */
     private String getMovieName(String s) {
         int indexOfFirstTab = s.indexOf("\t");
         int indexOfTabAfterName = s.substring(indexOfFirstTab + 1).indexOf("\t");
         int indexOfYear = s.substring(indexOfTabAfterName).indexOf(")");
         return s.substring(indexOfFirstTab + 1, indexOfYear + 1).replace("\t"," ");
    }

    /**
     * Gets the actor's name.
     * @param s the string with the actor's name in it
     * @return  the name of the actor
     */
     private String getActorName(String s) {
        int indexOfTab = s.indexOf("\t");
        return s.substring(0, indexOfTab);
     }

    /**
     *
     * @param name  the name of the movie
     * @return  the MovieNode with the desired movie or null if it's not in the list of movies yet
     */
     private MovieNode movieListHasMovie(String name) {
            for (MovieNode movie : _movies) {
                if (name.equals(movie.getName()))
                    return movie;
            }
         return new MovieNode(name);
     }

    /**
     * Returns whether the film in question is a movie or not.
     * @param fullLine  the string with all the movie/TV show information in it
     * @return  true if it's a movie, false if it's a TV show
     */
    private boolean isMovie(String fullLine) {
        boolean isMovie = true;
        int indexOfTab = fullLine.indexOf("\t");
        if(fullLine.charAt(indexOfTab+1)=='"' || fullLine.contains("(TV)"))
            isMovie = false;
        return isMovie;
    }


    /**
     * Gets all the actor nodes in the graph.
     * @return a collection of all the actor and actress nodes in the graph.
     */
    public Collection<? extends Node> getActors () {
        return _actors;

    }

    /**
     * Gets all the movie nodes in the graph.
     * @return a collection of all the movie nodes in the graph.
     */
    public Collection<? extends Node> getMovies () {
        return _movies;
    }

    /**
     * Returns the movie node having the specified name.
     * @param name the name of the requested movie
     * @return the movie node associated with the specified name or null
     * if no such movie exists.
     */
    public Node getMovie (String name) {
        for (MovieNode movieNode : _movies) {
            if(movieNode.getName().equals(name))
                return movieNode;
        }
        return null;
    }

    /**
     * Returns the actor node having the specified name.
     * @param name the name of the requested actor
     * @return the actor node associated with the specified name or null
     * if no such actor exists.
     */
    public Node getActor (String name) {
        for(ActorNode actorNode : _actors) {
            if(actorNode.getName().equals(name))
                return actorNode;
        }
        return null;
    }

}