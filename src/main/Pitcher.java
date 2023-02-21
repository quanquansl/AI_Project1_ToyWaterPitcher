package main;

public class Pitcher {
    private int capacity;
    private int current;

    public Pitcher(int cap, int cur){
        capacity = cap;
        current = cur;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getCurrent(){
        return current;
    }

    public void setCurrent(int cur){
        current = cur;
    }

    public boolean isEmpty(){
        if(current == 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isFull(){
        if(current == capacity){
            return true;
        }else{
            return false;
        }
    }

    public void fillFromSource(){
        current = capacity;
    }

    public void emptyToSource(){
        current = 0;
    }

    public void emptyToAnother(Pitcher p){
        int available = p.getCapacity() - p.getCurrent();
        if(available >= current){
            // empty this pitcher
            p.setCurrent(p.getCurrent() + current);
            current = 0;
        }else{
            // fill another pitcher
            current -= available;
            p.setCurrent(p.getCapacity());
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof Pitcher)
        {
            Pitcher p = (Pitcher) obj;
            return current == p.getCurrent();
        }
        return false;
    }
}
