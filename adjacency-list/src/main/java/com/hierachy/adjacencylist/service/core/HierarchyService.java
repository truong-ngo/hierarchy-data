package com.hierachy.adjacencylist.service.core;

import java.util.List;

public interface HierarchyService<T, ID> extends CrudService<T, ID> {
    List<T> findDescendants(ID id);
    List<T> findAncestors(ID id);
    T insertNode(String childIds, T node);
}
