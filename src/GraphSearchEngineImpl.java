import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GraphSearchEngineImpl implements  GraphSearchEngine {
// Data
    private int _distance;
    private Node _start;
    private Node _end;
    private ArrayList<Node> _BFSQueue;
    private HashMap<Node, Integer> _BFSDistances;
    private ArrayList<Node> _connectionList; //degree of the connection is the length-1
    private boolean _isNull;


// Constructor
    public GraphSearchEngineImpl() {
        _start = null;
        _end = null;
        _isNull=false;
        _BFSQueue = new ArrayList<Node>();
        _BFSDistances = new HashMap<Node, Integer>();
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
        _BFSDistances.put(_start, 0);
        _BFSQueue.add(_start);
        _connectionList.add(_end);
        while(!_BFSDistances.containsKey(t)&&_isNull==false){
            getNextNodeSet();
        }
        if (_isNull){
            return null;
        }
        else{
            backTrack();
            return _connectionList;
        }

    }

    private void getNextNodeSet() {
        if(_BFSQueue.size()==0){
            _isNull=true;
        }
        else{
            Node node=_BFSQueue.get(0);
            int distanceToNew=_BFSDistances.get(node)+1;
            for(Node neighbor : node.getNeighbors()) {
                if(!_BFSDistances.containsKey(neighbor)){
                    _BFSQueue.addAll(node.getNeighbors());
                    _BFSDistances.put(neighbor, distanceToNew);
                }
            }
            _BFSQueue.remove(0);
        }

    }

    private List<Node> backTrack() {
        while(_connectionList.contains(_start) != true) {
            for (Node node : _connectionList) {
                for(Node neighbor : node.getNeighbors()) {
                    if (_BFSDistances.containsKey(neighbor) && _BFSDistances.get(neighbor) == _BFSDistances.get(node)-1) {   // could also remove from _bfslist bc less space but more time??
                        _connectionList.add(neighbor);
                        System.out.println("added "+neighbor.getName());
                    }
                }
            }
        }
        Collections.reverse(_connectionList);
        return _connectionList;
    }
}
