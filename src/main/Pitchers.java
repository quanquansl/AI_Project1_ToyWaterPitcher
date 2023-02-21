package main;

import java.util.ArrayList;
import java.util.List;

public class Pitchers {
    public List pitchers = new ArrayList<Pitcher>();

    public Pitchers(List p){
        pitchers = new ArrayList(p);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof Pitchers)
        {
            Pitchers p = (Pitchers) obj;
            return pitchers.equals(p.pitchers);
        }
        return false;
    }
}
