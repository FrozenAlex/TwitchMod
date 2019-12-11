package j.w.d;

import java.io.Serializable;

// Source: Lambda
public abstract class l<R> implements h<R>, Serializable {
    private final int arity;

    public l(int i2) {
        this.arity = i2;
    }

    public int getArity() {
        return this.arity;
    }
}