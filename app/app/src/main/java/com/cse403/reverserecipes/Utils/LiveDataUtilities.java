package com.cse403.reverserecipes.Utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class LiveDataUtilities {
    public static <T, K, R> LiveData<R> combine(LiveData<T> first, LiveData<K> second, Combiner<T, K, R> combiner) {
        MediatorLiveData<R> combined = new MediatorLiveData<>();
        combined.addSource(first,
                newFirst -> combined.setValue(combiner.combine(newFirst, second.getValue()))
        );
        combined.addSource(second,
                newSecond -> combined.setValue(combiner.combine(first.getValue(), newSecond))
        );

        return combined;
    }
}
