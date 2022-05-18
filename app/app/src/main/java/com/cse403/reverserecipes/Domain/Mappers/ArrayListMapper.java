package com.cse403.reverserecipes.Domain.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListMapper<T, K> implements ListMapper<T, K> {

    private final Mapper<T, K> mInstanceMapper;

    public ArrayListMapper(Mapper<T, K> instanceMapper) {
        mInstanceMapper = instanceMapper;
    }

    @Override
    public List<K> map(List<T> o) {
        List<K> mapped = new ArrayList<>();
        for (T instance : o) {
            mapped.add(mInstanceMapper.map(instance));
        }
        return mapped;
    }
}
