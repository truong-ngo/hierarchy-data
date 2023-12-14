package com.example.readwritetable.service.core;

import java.util.List;

public interface HierarchyService<T, ID> {
    List<T> findDescendants(ID id);
    List<T> findAncestors(ID id);
    T insertNode(String childIds, T node);
}
