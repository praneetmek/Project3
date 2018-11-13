import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActorNode implements Node {

// Data
    private String _name;
    private ArrayList<MovieNode> _movies;

// Constructor
    public ActorNode(String actorName) {
        _name = actorName;
        _movies = new ArrayList<MovieNode>();
    }

// Methods
    /**
     * Returns the name of the node (e.g., "Judy Garland").
     * @return the name of the Node.
     */
    public String getName () {
        return _name;
    }


    /**
     * Returns the Collection of neighbors of the node.
     * @return the Collection of all the neighbors of this Node.
     */
    @Override
    public Collection<? extends Node> getNeighbors() {
        return _movies;
    }
//    public ArrayList<ActorNode> getActorNeighbors(){
//        for (MovieNode movieNode: _movies) {
//
//        }
//    }

    /**
     *
     * @param movie
     */
    public void addMovie(MovieNode movie) {
       _movies.add(movie);
    }
}