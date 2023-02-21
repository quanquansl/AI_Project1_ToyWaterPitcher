package main;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ToyWaterPitcher {
    private List capacity = new ArrayList<Integer>();
    private int maxCapacity = 0;
    private int minCapacity = Integer.MAX_VALUE;
    private int avrCapacity = 0;
    private int midCapacity = 0;
    private int goal;
    private double coefficient = 1; // to adjust F
    private Node start; // Start state
    private List closeList = new ArrayList<Node>();
    private Queue openList = new PriorityQueue<Node>();

    public List getCapacity(){
        return capacity;
    }

    public void addCapacity(int c){
        capacity.add(c);
    }

    public int getGoal(){
        return goal;
    }

    public void setGoal(int g){
        goal = g;
    }

    public void initialize(){
        List l = new ArrayList<Pitcher>();
        // pitchers[0] is the infinite pitcher
        Pitcher p0 = new Pitcher(Integer.MAX_VALUE, 0);
        l.add(0, p0);
        for(int i = 1;i <= capacity.size();i++){
            int c = (Integer)capacity.get(i-1);
            Pitcher p = new Pitcher(c, 0);
            l.add(i, p);
            if(c > maxCapacity){
                maxCapacity = c;
            }
            if(c < minCapacity){
                minCapacity = c;
            }
            avrCapacity += c;
        }
        avrCapacity/=capacity.size();
        midCapacity = (Integer)capacity.get((Integer)((1+capacity.size())/2) - 1);
        Pitchers ps = new Pitchers(l);
        int g = 0;
        double h = heuristic(ps);
        start = new Node(ps, null, g, h, g*coefficient+h);
        openList.add(start);
    }

    public int AStar(){
        List record = new ArrayList<Pitchers>(); // store pitchers that are in the closelist
        while(!openList.isEmpty()){
            Node current = (Node)openList.poll();
            Pitcher infinitePitcher = (Pitcher) current.pitchers.pitchers.get(0);
            closeList.add(current);
            record.add(current.pitchers);
            if(goalTest(current.pitchers)){
                return current.G;
            }else if(infinitePitcher.getCurrent() > goal * 8){
                break;
            }else{// generate new nodes to add into the openlist
                List currentPitchers = current.pitchers.pitchers;
                for(int i = 0;i<currentPitchers.size();i++){
                    Pitcher currentPitcher = (Pitcher) currentPitchers.get(i);
                    if(i != 0 && !currentPitcher.isFull()){ // fill up
                        Pitcher newPitcher = new Pitcher(currentPitcher.getCapacity(), currentPitcher.getCurrent());
                        newPitcher.fillFromSource();
                        Pitchers newPitchers = new Pitchers(currentPitchers);
                        newPitchers.pitchers.set(i, newPitcher);
                        int g = current.G + 1;
                        double h = heuristic(newPitchers);
                        Node newNode = new Node(newPitchers, current, g, h, g*coefficient+h);
                        if(!record.contains(newNode.pitchers)){
                            openList.add(newNode);
                        }
                    }
                    for(int j = 0;j<currentPitchers.size();j++){
                        Pitcher changedPitcher = (Pitcher) currentPitchers.get(j);
                        Pitcher anotherPitcher = new Pitcher(changedPitcher.getCapacity(), changedPitcher.getCurrent());
                        if(j != i && !currentPitcher.isEmpty()){// pour
                            Pitcher newPitcher = new Pitcher(currentPitcher.getCapacity(), currentPitcher.getCurrent());
                            newPitcher.emptyToAnother(anotherPitcher);
                            Pitchers newPitchers = new Pitchers(currentPitchers);
                            newPitchers.pitchers.set(i, newPitcher);
                            newPitchers.pitchers.set(j, anotherPitcher);
                            int g = current.G + 1;
                            double h = heuristic(newPitchers);
                            Node newNode = new Node(newPitchers, current, g, h, g*coefficient+h);
                            if(!record.contains(newNode.pitchers)){
                                openList.add(newNode);
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public double heuristic(Pitchers ps){
        // estimated steps
        Pitcher infinitePitcher = (Pitcher)ps.pitchers.get(0);
        double h1 = Math.floor(Math.abs(goal - infinitePitcher.getCurrent())/maxCapacity);
        // when current of infinite pitcher are the same, compare other pitchers
        int available = Math.abs(goal - infinitePitcher.getCurrent());
        int distance = available;
        for(int i = 1;i<ps.pitchers.size();i++){
            // choose the state with pitcher whose current is the closest with available first
            Pitcher pi = (Pitcher)ps.pitchers.get(i);
            int d = Math.abs(available - pi.getCurrent());
            if(d < distance){
                distance = d;
            }
        }

        double h2 = Math.abs(distance)/(double)avrCapacity;
        return h1+h2;
    }

    public boolean goalTest(Pitchers ps){
        Pitcher infinitePitcher = (Pitcher)ps.pitchers.get(0);
        return infinitePitcher.getCurrent() == goal;
    }
}
