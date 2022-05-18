package com.cse403.reverserecipes.Domain.Mappers;

import java.util.List;

public interface ListMapper<T, K> extends Mapper<List<T>, List<K>> {
    public List<K> map(List<T> o);
}
