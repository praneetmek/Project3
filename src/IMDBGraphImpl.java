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
        _movies=new ArrayList<MovieNode>();
        _actors=new ArrayList<ActorNode>();
    }

// Methods

    public void loadDataForActors(){
        //Actors
        for (int i=0;i<NUM_INTRO_LINES_ACTORS;i++){
           // System.out.println(_sActors.nextLine());
        }
        String newLineForActor;
        ActorNode currentActor=new ActorNode("Fake Person who doesn't actually exist in the IMDB Database");
        String nameOfMovie;
        MovieNode currentMovie;
        String nameOfActor;
        while(_sActors.hasNextLine()){
            newLineForActor=_sActors.nextLine();
            if(newLineForActor.indexOf("\t")==0){                              //line where an actor introduced
               if(isMovie(newLineForActor)){                                   //if that line is a movie
                   nameOfMovie=getMovieName(newLineForActor);
                   currentMovie=movieListHasMovie(nameOfMovie);
                   if(currentMovie==null){                                     //if there is no movie with that name yet
                       currentMovie=new MovieNode(nameOfMovie);
                       _movies.add(currentMovie);
                   }
                   currentActor.addMovie(currentMovie);
                   currentMovie.addActor(currentActor);
               }
            }
            else if(newLineForActor.indexOf("\t")>0){
                nameOfActor=getActorName(newLineForActor);
                currentActor=new ActorNode(nameOfActor);
                nameOfMovie
            }

        }

     }

     private String getMovieName(String s){
        int indexOfTab=s.indexOf("\t");
        int indexOfYear=s.indexOf(")");
        return s.substring(indexOfTab+1,indexOfYear+1);

     }
     private String getActorName(String s){
        int indexOfTab=s.indexOf("\t");
        return s.substring(0,indexOfTab);
     }
     private MovieNode movieListHasMovie(String name){
            for (MovieNode checkMovieNode : _movies) {
                if (name.equals(checkMovieNode.getName())) {
                    return checkMovieNode;
                }
            }
         return null;
     }
    private boolean isMovie(String fullLine){
        boolean isMovie=true;
        int indexOfTab=fullLine.indexOf("\t");
        if(fullLine.charAt(indexOfTab+1)=='"' || fullLine.contains("(TV)")){
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