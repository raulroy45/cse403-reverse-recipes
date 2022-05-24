package com.cse403.reverserecipes.Domain.Mappers;

public interface Mapper<T, K> {
    public K map(T o);
}
