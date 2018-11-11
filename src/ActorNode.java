

public class ActorNode implements Node {

// Data
    private String _name;
    private Collection<MovieNode> _movies;

// Constructor
    public ActorNode(String actorName, Collection<MovieNode> movies) {
        _name = actorName;
        _movies = movies;
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
    public Collection<? extends Node> getMovies() {
        return _movies;
    }

    /**
     *
     * @param movie
     */
    public void addMovie(MovieNode movie) {
       Boolean added = addAll(_movies, movie);
    }
}