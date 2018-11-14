import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GraphSearchEngineImpl implements  GraphSearchEngine {
// Data
    private int _distance;
    private Node _start;
    private Node _end;
    private ArrayList<Node> _BFSList;
    private HashMap<Node, Integer> _BFSMap;
    private ArrayList<Node> _connectionList; //degree of the connection is the length-1


// Constructor
    public GraphSearchEngineImpl() {
        _distance = 0;
        _start = null;
        _end = null;
        _BFSList = new ArrayList<Node>();
        _BFSMap = new HashMap<Node, Integer>();
        _connectionList = new ArrayList<Node>();
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
        _BFSMap.put(_start, _distance);
        _BFSList.add(_start);
        _connectionList.add(_end);
        while(_BFSMap.containsValue(t) != true) {
            _distance++;
            getNextNodeSet();
        }
        backTrack();
        return _connectionList;
    }

    private void getNextNodeSet() {
        for(Node node: _BFSList) {
            _BFSList.addAll(node.getNeighbors());
            for(Node neighbor : node.getNeighbors()) {
                _BFSMap.put(neighbor, _distance);
            }
        }
    }

    private List<Node> backTrack() {
        while(_connectionList.contains(_start) != true) {
            for (Node node : _connectionList) {
                for(Node neighbor : node.getNeighbors()) {
                    if (_BFSMap.containsValue(neighbor) && _BFSMap.get(neighbor) == _BFSMap.get(_end)-1) {   // could also remove from _bfslist bc less space but more time??
                        _connectionList.add(neighbor);
                        break;
                    }
                }
            }
        }
        Collections.reverse(_connectionList);
        return _connectionList;
    }
}
