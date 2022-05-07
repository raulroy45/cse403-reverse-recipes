package com.cse403.reverserecipes;

import androidx.lifecycle.LiveData;

public interface Combiner<T, K, R> {
    public R combine(T first, K second);
}
