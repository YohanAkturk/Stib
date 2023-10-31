package g56514.stibride.gestionDB.dto;

import java.util.Objects;

/**
 * This class allows to used a pair which contains a first and a second data.
 * 
 * @author yohan
 * @param <F> the first data.
 * @param <S> the second data.
 */
public class Pair<F, S> {

    private F first;
    private S second;

    /**
     * Creates a new instance of <code>Pair</code>.
     *
     * @param first the first data.
     * @param second the second data.
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
    
    /**
     * Getter of the first data.
     * 
     * @return the first data.
     */
    public F getFirst() {
        return first;
    }

    /**
     * Setter of the first data.
     * 
     * @param first the first data.
     */
    public void setFirst(F first) {
        this.first = first;
    }

    /**
     * Getter of second data.
     * 
     * @return the second data.
     */
    public S getSecond() {
        return second;
    }
    
    /**
     * Setter of the second data.
     * 
     * @param second the second data.
     */
    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.first);
        hash = 79 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }
}
