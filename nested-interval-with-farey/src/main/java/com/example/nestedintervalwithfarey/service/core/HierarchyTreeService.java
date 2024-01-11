package com.example.nestedintervalwithfarey.service.core;

import java.util.List;

public interface HierarchyTreeService<T, ID> {
    List<T> findDescendants(ID id);
    List<T> findAncestors(ID id);
    T findParent(ID id);
    List<T> findChild(ID id);
    T add(T node) throws Exception;
    void remove(ID id);
    T insert(T node, ID childId);
    T move(ID id, ID parentId);
}
