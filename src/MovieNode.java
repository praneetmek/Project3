
public class MovieNode implements Node {

//Data
    private String _name;
    private Collection<ActorNode> _actors;


// Constructor
    public MovieNode(String movieName, Collection<ActorNode> actors) {
        _name = movieName;
        _actors = actors;
    }


// Methods
    /**
     * Returns the name of the node (e.g., "Judy Garland").
     * @return the name of the Node.
     */
    public String getName () {
        return _name;
    }

    @Override
    /**
     * Returns the Collection of neighbors of the node.
     * @return the Collection of all the neighbors of this Node.
     */
    public Collection<? extends Node> getActors() {
        return _actors;
    }

    /**
     *
     * @param actor
     */
    public void addActor(ActorNode actor) {
        Boolean added = addAll(_actors, actor);
    }
}