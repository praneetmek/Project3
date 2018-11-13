import java.util.ArrayList;
import java.util.Collection;

public class MovieNode implements Node {

//Data
    private String _name;
    private ArrayList<ActorNode> _actors;


// Constructor
    public MovieNode(String movieName) {
        _name = movieName;
        _actors = new ArrayList<ActorNode>();
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
        return _actors;
    }
    /**
     *
     * @param actor
     */
    public void addActor(ActorNode actor) {
        _actors.add(actor);
    }
}