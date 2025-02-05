package canuran.common.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import java.util.function.*;

/**
 * 树工具类。
 */
@SuppressWarnings("unchecked")
public class TreeUtils {
    private TreeUtils() {
        throw new IllegalStateException("Can not construct TreeUtils");
    }

    /**
     * 树节点集合转换为树形结构，使用函数式接口操作树节点。
     * 有父节点的挂在父节点下，未找到父结节的置于顶级。
     * <p>
     * 例如把 List<Node> nodes 转成树：
     * TreeUtils.toTree(nodes, ArrayList::new,
     * Node::getNodeKey, Node::getParentKey,
     * Node::getChildren, Node::setChildren);
     */
    public static <E, C extends Collection<E>> C toTree(TreeBuilder treeBuilder) {
        C tree = (C) treeBuilder.build();
        return tree;
    }

    /**
     * 先根遍历原子节点，无递归，支持大树。
     */
    public static <E, C extends Collection<E>> void traverseTree(
            C tree, Function<E, C> childrenGetter, Consumer<E> consumer) {
        if (tree == null || tree.isEmpty()) {
            return;
        }
        // 使用迭代器和栈记录所有遍历状态
        Stack<Iterator<E>> stack = new Stack<>();
        stack.push(tree.iterator());
        while (!stack.isEmpty()) {
            Iterator<E> iterator = stack.pop();
            while (iterator.hasNext()) {
                // 先遍历自己，然后遍历子节点
                E node = iterator.next();
                if (childrenGetter.apply(node) != null) {
                    stack.push(iterator);
                    iterator = childrenGetter.apply(node).iterator();
                }
                consumer.accept(node);
            }
        }
    }

    /**
     * 把树的所有节点展开到集合，无递归，支持大树。
     */
    public static <E, C extends Collection<E>> C flat(
            C tree, Supplier<C> collectionCreator, Function<E, C> childrenGetter) {
        if (tree == null) {
            return null;
        }
        C nodes = collectionCreator.get();
        // 使用迭代器和栈记录所有遍历状态
        Stack<Iterator<E>> stack = new Stack<>();
        stack.push(tree.iterator());
        while (!stack.isEmpty()) {
            Iterator<E> iterator = stack.pop();
            while (iterator.hasNext()) {
                // 先遍历自己，然后遍历子节点
                E node = iterator.next();
                if (childrenGetter.apply(node) != null) {
                    stack.push(iterator);
                    iterator = childrenGetter.apply(node).iterator();
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

    /**
     * 从树的所有节点中查找原子节点，无递归，支持大树。
     */
    public static <E, C extends Collection<E>> C filterTree(
            C tree, Supplier<C> collectionCreator,
            Function<E, C> childrenGetter, Predicate<E> predicate) {
        if (tree == null) {
            return null;
        }
        C nodes = collectionCreator.get();
        // 使用迭代器和栈记录所有遍历状态
        Stack<Iterator<E>> stack = new Stack<>();
        stack.push(tree.iterator());
        while (!stack.isEmpty()) {
            Iterator<E> iterator = stack.pop();
            while (iterator.hasNext()) {
                // 先遍历自己，然后遍历子节点
                E node = iterator.next();
                if (childrenGetter.apply(node) != null) {
                    stack.push(iterator);
                    iterator = childrenGetter.apply(node).iterator();
                }
                if (predicate.test(node)) {
                    nodes.add(node);
                }
            }
        }
        return nodes;
    }

    /**
     * 从树的所有节点中查找第一个原子节点，无递归，支持大树。
     */
    public static <E, C extends Collection<E>> E findFirst(
            C tree, Function<E, C> childrenGetter, Predicate<E> predicate) {
        if (tree == null) {
            return null;
        }
        // 使用迭代器和栈记录所有遍历状态
        Stack<Iterator<E>> stack = new Stack<>();
        stack.push(tree.iterator());
        while (!stack.isEmpty()) {
            Iterator<E> iterator = stack.pop();
            while (iterator.hasNext()) {
                // 先遍历自己，然后遍历子节点
                E node = iterator.next();
                if (predicate.test(node)) {
                    return node;
                }
                if (childrenGetter.apply(node) != null) {
                    stack.push(iterator);
                    iterator = childrenGetter.apply(node).iterator();
                }
            }
        }
        return null;
    }

}
