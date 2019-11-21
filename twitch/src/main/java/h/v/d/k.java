package h.v.d;

import java.io.Serializable;

// Source: Lambda
public abstract class k<R> implements h<R>, Serializable {
    private final int arity;

    public k(int i2) {
        this.arity = i2;
    }

    public int getArity() {
        return this.arity;
    }
}