
public class Pair <T, K>{
    private T t;
    private K k;

    public Pair(T t, K k) {
        this.t = t;
        this.k = k;
    }

    public T getFirst(){
        return this.t;
    }

    public K getSecond(){
        return this.k;
    }

}
