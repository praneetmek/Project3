
public class IMDBGraphImpl extends IMDBGraph {

// Data
    private Collection<ActorNode> _actors;
    private Collection<MovieNode> _movies;
    private Scanner _sActors;
    private Scanner _sActresses;


// Constructor
    public IMDBGraphImpl(String actorsFile, String actressesFile) throws IOException {
        _sActors = new Scanner(new File(actorsFile));
        _sActresses = new Scanner(new File(actressesFile));
    }

// Methods

    private void loadData()
    {
        // Actors
        if(_sActors.next() = "\n")
        {
            String x = _sActors.nextLine();
            actorNode = (_sActors.next(), null);
            while (!(_sActors.next().equals("\n")))
            {
                MovieNode movieNode;
                movieName = _sActors.next();
                movieName = movieName + _sActors.next();
                if(movieNode.getName().equals(movieName) || movieName.charAt(0) == "\"" || _sActors.next().equals("(TV)")) // if TV
                    x = _sActors.nextLine();
			    else
                { // if normal
                    if(binarySearch(_movies, movieNode.getName()) >= 0)
                    { // if movieNode is already there, add the actor!
                        movieNode = singletonList(_movies, movieName).get(0); /////// HOW am i supposed to retrieve this data WTF
                        movieNode.addActor(actorNode);
                        actorNode.addMovie(movieNode);
                        x = _sActors.nextLine();
                    }
                    else
                    { // movieNode doesn't exist yet, so make one and add it to the movie collection!
                        movieNode = new MovieNode(movieName, actorNode);
                        Boolean added = addAll(_movies, movieNode);
                        actorNode.addMovie(movieNode);
                        x = _sActors.nextLine();
                    }
                }
            }
            if(actorNode.getMovies() != null)
                added = addAll(_actors, actorNode);
        }
        // Actresses
        if(_sActors.next() = "\n")
        {
            String x = nextLine();
            actorNode = (_sActors.next(), null);
            while (!(_sActors.next().equals("\n")))
            {
                MovieNode movieNode;
                movieName = _sActors.next();
                movieName = movieName + _sActors.next();
                if(movieNode.getName().equals(movieName) || movieName.charAt(0) == "\"" || _sActors.next().equals("(TV)")) // if TV
                    x = _sActors.nextLine();
                else
                { // if normal
                    if(binarySearch(_movies, movieNode.getName()) >= 0)
                    { // if movieNode is already there, add the actor!
                        movieNode = singletonList(movieNode).get(0);
                        movieNode.addActor(actorNode);
                        actorNode.addMovie(movieNode);
                        x = _sActors.nextLine();
                    }
                    else
                    { // movieNode doesn't exist yet, so make one and add it to the movie collection!
                        movieNode = new MovieNode(movieName, actorNode);
                        Boolean added = addAll(_movies, movieNode);
                        actorNode.addMovie(movieNode);
                        x = _sActors.nextLine();
                    }
                }
            }
        }
    }

    private void mergeActorLists() {

    }

    /**
     * Gets all the actor nodes in the graph.
     * @return a collection of all the actor and actress nodes in the graph.
     */
    public Collection<? extends Node> getActors () {


    }

    /**
     * Gets all the movie nodes in the graph.
     * @return a collection of all the movie nodes in the graph.
     */
    public Collection<? extends Node> getMovies () {

    }

    /**
     * Returns the movie node having the specified name.
     * @param name the name of the requested movie
     * @return the movie node associated with the specified name or null
     * if no such movie exists.
     */
    public Node getMovie (String name) {

    }

    /**
     * Returns the actor node having the specified name.
     * @param name the name of the requested actor
     * @return the actor node associated with the specified name or null
     * if no such actor exists.
     */
    public Node getActor (String name) {

    }
}