import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class IMDBGraphImpl implements IMDBGraph {

// Data
    private ArrayList<ActorNode> _actors;
    private ArrayList<MovieNode> _movies;
    private Scanner _sActors;
    private Scanner _sActresses;
    private final int NUM_INTRO_LINES_ACTORS=239;


// Constructor
    public IMDBGraphImpl(String actorsFile, String actressesFile) throws IOException {
        _sActors = new Scanner(new File(actorsFile),"ISO-8859-1");
        _sActresses = new Scanner(new File(actressesFile),"ISO-8859-1");
    }

// Methods

    public void loadData(){
        //Actors
        for (int i=0;i<NUM_INTRO_LINES_ACTORS;i++){
            _sActors.nextLine();
        }
        String newLineForActor;
        ActorNode currentActor=new ActorNode("Fake Person who doesn't actually exist in the IMDB Database",null);
        String newMediaName;
        MovieNode currentMovie;
        while(_sActors.hasNextLine()){
            newLineForActor=_sActors.nextLine();
            if(!newLineForActor.contains("\t")){
                                                                                //if the next line doesn't contain a tab do nothing
            }
            else if (newLineForActor.indexOf("\t")==0) {                        //if the next line starts with a tab
                newMediaName = getMovieName(newLineForActor);                   //get the name of the media
                currentMovie = movieListHasMovie(newMediaName);                 //sets current movie as a movienode if it exists already or null if it doesnt
                if (currentMovie == null) {
                    currentMovie = new MovieNode(newMediaName, null);   //if movieNode doesnt exist already, create a new movieNode
                    if(isMovie(currentMovie)) {                                 // check if the movieNode is a movie
                        _movies.add(currentMovie);                              // if it is then add that to the movie list
                    }
                }
                currentActor.addMovie(currentMovie);
                currentMovie.addActor(currentActor);
            }
            else{

                ////currentActor=new ActorNode();

            }
        }

     }
     private String getMovieName(String s){
        int indexOfTab=s.indexOf("\t");
        int indexOfYear=s.indexOf(")");
        return s.substring(indexOfTab+1,indexOfYear+1);
     }
     private MovieNode movieListHasMovie(String name){
        for (MovieNode checkMovieNode:_movies) {
             if(name.equals(checkMovieNode.getName())){
                 return checkMovieNode;
             }
         }
         return null;
     }
    private boolean isMovie(MovieNode movieNode){
        boolean isMovie=true;
        if(movieNode.getName().charAt(0)=='"' || movieNode.getName().contains("(TV)")){
            isMovie=false;
        }
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
        for (MovieNode movieNode: _movies) {
            if(movieNode.getName().equals(name)){
                return movieNode;
            }
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
        for(ActorNode actorNode:_actors){
            if (actorNode.getName().equals(name)){
                return actorNode;
            }
        }
        return null;
    }
}