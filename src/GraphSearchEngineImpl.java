import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GraphSearchEngineImpl implements  GraphSearchEngine {
// Data
    private int _distance;
    private Node _start;
    private Node _end;
    private ArrayList<Node> _visitedNodesList;
    private HashMap<Node, Integer> _visitedNodesMap;
    private ArrayList<Node> _connectionList;


// Constructor
    public GraphSearchEngineImpl() {
        _distance = 0;
        _start = null;
        _end = null;
        _visitedNodesList = new ArrayList<>();
        _visitedNodesMap = new HashMap<>();
        _connectionList = new ArrayList<>();
    }

// Methods
    /**
     * Finds a shortest path, if one exists, between nodes s and
     * t. The path will be a list: (s, ..., t). If no
     * path exists, then this method will return null.
     * @param s the start node.
     * @param t the target node.
     * @return a shortest path in the form of a List of Node objects
     * or null if no path exists.
     */
    public List<Node> findShortestPath (Node s, Node t) {
        _start = s;
        _end = t;
        _visitedNodesMap.put(_start, _distance);
        _visitedNodesList.add(_start);
        _connectionList.add(_end);

        while(!_visitedNodesMap.containsKey(t)) {
            _distance++;
            getNextNodeSet();
        }
        backTrack();

        return _connectionList;
    }

    /**
     *
     */
    private void getNextNodeSet() {
        for(Node node: _visitedNodesList) {
            _visitedNodesList.addAll(node.getNeighbors());
            for(Node neighbor : node.getNeighbors()) {
                _visitedNodesMap.put(neighbor, _distance);
            }
        }
    }

    /**
     *
     * @return
     */
    private List<Node> backTrack() {
        for (Node node : _connectionList) {
            for(Node neighbor : node.getNeighbors()) {
                // Checks whether the desired node has already been visited and if it's the shortest path
                if (_visitedNodesMap.containsKey(neighbor) && _visitedNodesMap.get(neighbor) == _visitedNodesMap.get(_end)-1) {
                    _connectionList.add(neighbor);
                    break;
                }
            }
        }

        if(_connectionList.size()==1)
            _connectionList = null;
        else
            Collections.reverse(_connectionList);

        return _connectionList;
    }
}
