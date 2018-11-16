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
    private final int NUM_INTRO_LINES_ACTRESSES=241;


// Constructor
    public IMDBGraphImpl(String actorsFile, String actressesFile) throws IOException {
        _sActors = new Scanner(new File(actorsFile),"ISO-8859-1");
        _sActresses = new Scanner(new File(actressesFile),"ISO-8859-1");
        _movies=new ArrayList<MovieNode>();
        _actors=new ArrayList<ActorNode>();
        loadDataForActors();
        java.util.Collections.sort(_actors);
        for (ActorNode actor:_actors) {
            if(actor.getNeighbors()==null){
                _actors.remove(actor);
            }
        }
    }

// Methods

    private void loadDataForActors(){
        //Actors
        for (int i=0;i<NUM_INTRO_LINES_ACTORS;i++){
           if(_sActors.hasNextLine())
              _sActors.nextLine();
        }
        loadDataFromTop(_sActors);
        //Actresses
        for (int i=0;i<NUM_INTRO_LINES_ACTRESSES;i++){
            if(_sActresses.hasNextLine())
                _sActresses.nextLine();
        }
        loadDataFromTop(_sActresses);
     }
     private void loadDataFromTop(Scanner s){
         String newLineForActor;
         ActorNode currentActor=new ActorNode("Fake Person who doesn't actually exist in the IMDB Database");
         String nameOfMovie;
         MovieNode currentMovie;
         String nameOfActor;
         while(s.hasNextLine()){
             newLineForActor=s.nextLine();
             if(newLineForActor.contains("------")){
                 break;
             }
             else {
                 //System.out.println(newLineForActor);
                 if (newLineForActor.indexOf("\t") != -1) {
                     if (newLineForActor.indexOf("\t") > 0) {                              //line where an actor introduced
                         nameOfActor = getActorName(newLineForActor);
                         currentActor = new ActorNode(nameOfActor);
                         _actors.add(currentActor);

                     }
                     if (isMovie(newLineForActor)) {
                         System.out.println(newLineForActor);//if that line is a movie
                         nameOfMovie = getMovieName(newLineForActor);
                         currentMovie = movieListHasMovie(nameOfMovie);
                         if (currentMovie == null) {                                     //if there is no movie with that name yet
                             currentMovie = new MovieNode(nameOfMovie);
                             _movies.add(currentMovie);
                         }
                         currentActor.addMovie(currentMovie);
                         currentMovie.addActor(currentActor);
                     }
                 }
             }
         }
     }

     private String getMovieName(String s) {
         int indexOfTab = s.indexOf("\t");
         int indexOfYear = s.indexOf(")");
         if(indexOfTab<indexOfYear)
            return s.substring(indexOfTab + 1, indexOfYear + 1).replace("\t","");
         else
             return "This is not a real movie. The real movie had a parentheses in its name";
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
        int indexOfTab=fullLine.lastIndexOf("\t");
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