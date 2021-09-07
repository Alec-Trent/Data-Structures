package cs2321;

import java.util.Iterator;

import net.datastructures.*;

/**
 * A linked binary tree using a binary tree data structure.
 * <p>
 * Course: CS2321 Section R01
 * Assignment: #6
 *
 * @author Alec Trent
 */

@TimeComplexity("O(n^2)")
public class LinkedBinaryTree<E> implements BinaryTree<E> {
    int size = 0;  //the number of entries (mappings)
    Node<E> root = null;

//---------------- nested Node class ----------------

    /**
     * Node of a doubly linked list, which stores a reference to its
     * element and to both the previous and next node in the list.
     */
    private static class Node<E> implements Position<E> {

        /**
         * The element stored at this node
         */
        private E element;               // reference to the element stored at this node

        /**
         * A reference to the left child node in the list
         */
        private Node<E> left;            // reference to the left child node in the list

        /**
         * A reference to the right child node in the list
         */
        private Node<E> right;            // reference to the right child node in the list

        /**
         * A reference to the parent node in the list
         */
        private Node<E> parent;            // reference to the parent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param l reference to a node that should precede the new node
         * @param r reference to a node that should follow the new node
         * @param p reference to a node that is previous
         */
        public Node(E e, Node<E> l, Node<E> r, Node<E> p) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public void setElement(E e) {
            element = e;
        }

        public void setLeft(Node<E> p) {
            left = p;
        }

        public void setRight(Node<E> n) {
            right = n;
        }
    } //----------- end of nested Node class -----------

    //Verifies that a Position belongs to the appropriate class, and is
    // not one that has been previously removed
    @TimeComplexity("O(1)")
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if (node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    //Returns the root Position of the tree (or null if tree is empty).
    @Override
    @TimeComplexity("O(1)")
    public Position<E> root() {
        return this.root;
    }

    public LinkedBinaryTree() {
    }

    //Returns the Position of p's parent (or null if p is root).
    @Override
    @TimeComplexity("O(1)")
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return validate(p).getParent();
    }


    @Override
    @TimeComplexity("O(1)")
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
        /*
        TCJ
        addLast is O(1)
         */
        ArrayList<Position<E>> children = new ArrayList<>();
        if (validate(p).getLeft() != null)
            children.addLast(validate(p).getLeft());
        if (validate(p).getRight() != null)
            children.addLast(validate(p).getRight());
        return children;
    }

    @Override
    @TimeComplexity("O(n)")
    /* count only direct child of the node, not further descendant. */
    public int numChildren(Position<E> p) throws IllegalArgumentException {
        /*
        TCJ
        loops through all nodes looking for children
         */
        int numChildren = 0;
        if (validate(p).getLeft() != null)
            numChildren++;
        if (validate(p).getRight() != null)
            numChildren++;
        return numChildren;
    }

    //Has children
    @Override
    @TimeComplexity("O(n)")
    public boolean isInternal(Position<E> p) throws IllegalArgumentException {
        /*
        TCJ
        call to numChildren
         */
        return numChildren(p) >= 1;
    }

    //Does not have children
    @Override
    @TimeComplexity("O(n)")
    public boolean isExternal(Position<E> p) throws IllegalArgumentException {
        /*
        TCJ
        call to numChildren
         */
        return numChildren(p) == 0;
    }

    @Override
    @TimeComplexity("O(1)")
    public boolean isRoot(Position<E> p) throws IllegalArgumentException {
        if (root() == null)
            return false;
        return root().equals(p);
    }

    @Override
    @TimeComplexity("O(1)")
    public int size() {
        return this.size;
    }

    @Override
    @TimeComplexity("O(1)")
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    @TimeComplexity("O(1)")
    public Iterator<E> iterator() {
        ArrayList<E> list = new ArrayList<>();
        for (Position<E> position :
                positions()) {
            list.addLast(position.getElement());
        }
        return list.iterator();
    }

    //Returns the given positions inOrder
    @TimeComplexity("O(n)")
    public void inOrder(Position<E> p, ArrayList<Position<E>> buffer) {
        /*
        TCJ
        recurses through all data adding to buffer
         */
        if (p == null)
            return;
        if (validate(p).getLeft() != null)
            inOrder(validate(p).getLeft(), buffer);
        buffer.addLast(p);
        if (validate(p).getRight() != null)
            inOrder(validate(p).getRight(), buffer);
    }

    //Returns the given node as a Position, unless it is a sentinel, in which case
    //  null is returned (so as not to expose the sentinels to the user).
    @Override
    @TimeComplexity("O(n)")
    public Iterable<Position<E>> positions() {
        /*
        TCJ
        call to inOrder
         */
        ArrayList<Position<E>> list = new ArrayList<>();
        inOrder(root(), list);
        return list;
    }

    //Returns the Position of p's left child (or null if no child exists).
    @Override
    @TimeComplexity("O(1)")
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return validate(p).getLeft();
    }

    //Returns the Position of p's right child (or null if no child exists).
    @Override
    @TimeComplexity("O(1)")
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return validate(p).getRight();
    }

    @Override
    @TimeComplexity("O(1)")
    public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
        Node<E> parent = validate(p).getParent();
        if (parent.getLeft() == validate(p))
            return parent.getRight();
        else
            return parent.getLeft();
    }

    //Places element e at the root of an empty tree and returns its new Position.
    @TimeComplexity("O(1)")
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = new Node<>(e, null, null, null);
        size++;
        return root;
    }

    //Creates a new left child of Position p storing element e and returns its Position.
    @TimeComplexity("O(1)")
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        if (validate(p).getLeft() != null)
            throw new IllegalArgumentException("P already has left child");
        validate(p).setLeft(new Node<E>(e, null, null, validate(p)));
        size++;
        return validate(p).getLeft();
    }

    //Creates a new right child of Position p storing element e and returns its Position.
    @TimeComplexity("O(1)")
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        if (validate(p).getRight() != null)
            throw new IllegalArgumentException("P already has right child");
        validate(p).setRight(new Node<E>(e, null, null, validate(p)));
        this.size++;
        return validate(p).getRight();
    }

    // Attach trees t1 and t2 as left and right subtrees of external Position.
    //if p is not external, throw IllegalArgumentExeption.
    @TimeComplexity("O(n)")
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
            throws IllegalArgumentException {
        /*
        TCJ
        call to isInternal
         */
        if (isInternal(p))
            throw new IllegalArgumentException("P is internal");
        validate(p).setRight(t2.validate(t2.root));
        validate(p).setLeft(t1.validate(t1.root));
        t2.validate(t2.root).setParent(validate(p));
        t1.validate(t1.root).setParent(validate(p));
        this.size = this.size + t1.size + t2.size;
    }

    //Removes the node at Position p and replaces it with its child, if any.
    @TimeComplexity("O(n^2)")
    public E remove(Position<E> p) {
        /*
        TCJ
        call to numChildren
        loop through data in else statement
         */
        Node<E> node = validate(p);
        if (numChildren(p) == 2)
            throw new IllegalArgumentException("p has two children");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null)
            child.setParent(node.getParent());
        if (node == root)
            root = child;
        else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }

    //Replaces the element at Position p with element e and returns the replaced element.
    @TimeComplexity("O(1)")
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }
}