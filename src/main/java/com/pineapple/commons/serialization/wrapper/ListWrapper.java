package com.pineapple.commons.serialization.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pineapple.commons.serialization.serialize.ListWrapperSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonSerialize(using = ListWrapperSerializer.class)
public class ListWrapper<T> {

    private final List<T> list;

    public ListWrapper(Collection<T> list) {
        this.list = new LinkedList<>(list);
    }

    public ListWrapper() {
        this.list = Collections.emptyList();
    }

    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }

    public <R> ListWrapper(final List<R> value, final Function<? super R, ? extends T> mapper) {
        list = value.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
