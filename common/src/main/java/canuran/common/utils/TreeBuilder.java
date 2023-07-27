package canuran.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TreeBuilder<E, C extends List<E>> {

    private List<E> nodes;
    private Supplier<C> treeCreator;
    private Function<E, Serializable> keyGetter;
    private Function<E, Serializable> parentKeyGetter;
    private Function<E, C> childrenGetter;
    private BiConsumer<E, C> childrenSetter;

    public TreeBuilder<E,C> withNodes(C nodes) {
        this.nodes = (List<E>) nodes;
        return this;
    }

    public TreeBuilder<E,C> withTreeCreator(Supplier<C> treeCreator) {
        this.treeCreator = treeCreator;
        return this;
    }

    public TreeBuilder<E,C> withKeyGetter(Function<E, Serializable> keyGetter) {
        this.keyGetter = keyGetter;
        return this;
    }

    public TreeBuilder<E,C> withParentKeyGetter(Function<E, Serializable> parentKeyGetter) {
        this.parentKeyGetter = parentKeyGetter;
        return this;
    }

    public TreeBuilder<E,C>withChildrenGetter(Function<E, C> childrenGetter) {
        this.childrenGetter = childrenGetter;
        return this;
    }

    public TreeBuilder<E,C>withChildrenSetter(BiConsumer<E, C> childrenSetter) {
        this.childrenSetter = childrenSetter;
        return this;
    }

    public C build() {
        if (nodes == null) {
            return null;
        }
        if (keyGetter == null || treeCreator == null || parentKeyGetter == null
                || childrenGetter == null || childrenSetter == null) {
            throw new IllegalArgumentException("Operate methods missing");
        }
        C tree = treeCreator.get();
        boolean single;
        for (E node : nodes) {
            // 没有父节点作为根节点
            if (parentKeyGetter.apply(node) == null) {
                tree.add(node);
            } else {
                single = true;
                for (E parent : nodes) {
                    // 有父节点ID，添加到它的父节点
                    if (parentKeyGetter.apply(node).equals(keyGetter.apply(parent))) {
                        if (childrenGetter.apply(parent) == null) {
                            childrenSetter.accept(parent, treeCreator.get());
                        }
                        childrenGetter.apply(parent).add(node);
                        single = false;
                        break;
                    }
                }
                // 没有找到父节点的也做为根节点
                if (single) {
                    tree.add(node);
                }
            }
        }
        return tree;
    }
}