package asriik.trak2;

/**
 * Singly linked list, meant to be used as a hash table bucket. Insertions go in front.
 * Created by: Antti Riikonen
 * Date and time: 29.3.2013, 16:55
 */
public class BucketList {
    // List head
    private BucketNode head = null;
    // List size
    private int size = 0;

    /**
     * List node helper class.
     */
    private class BucketNode {
        // Node value
        private final int value;
        // Child node pointer
        private BucketNode child = null;

        /**
         * Create a new bucket node with given value and child node.
         * @param value Node value
         * @param child Child node
         */
        public BucketNode(int value, BucketNode child) {
            this.value = value;
            this.child = child;
        }

        public int getValue() {
            return value;
        }

        public BucketNode getChild() {
            return child;
        }

        /**
         * Adds or modifies the child of this list node.
         * @param newChild new child node
         */
        public void setChild(BucketNode newChild) {
            this.child = newChild;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }

    /**
     * Iterates through the list, searching for needle.
     * @param needle value to search for
     * @return True if found, otherwise false
     */
    public boolean contains(int needle) {
        BucketNode nodeToSearch = head;
        while (nodeToSearch != null) {
            if (nodeToSearch.getValue() == needle) {
                return true;
            }
            nodeToSearch = nodeToSearch.getChild();
        }
        return false;
    }

    /**
     * Iterates through the list, searching for and removing the first occurrence of needle.
     * @param needle value to remove from the list
     * @return True if value was found and removed, false if value wasn't found.
     */
    public boolean remove(int needle) {
        BucketNode nodeToRemove = head;
        // Case 1: list head contains the value to remove
        if (head.getValue() == needle) {
            head = nodeToRemove.getChild();
            size--;
            return true;
        } else {
            while (nodeToRemove != null) {
                // Case 2: next node in the lst contains the value to remove
                if (nodeToRemove.getChild().getValue() == needle) {
                    nodeToRemove.setChild(nodeToRemove.getChild().getChild());
                    size--;
                    return true;
                }
                nodeToRemove = nodeToRemove.getChild();
            }
            // Case 3: the list didn't contain the value to remove.
            return false;
        }
    }

    /**
     * Inserts value to the beginning of the list.
     * @param value new value to be inserted.
     */
    public void add(int value) {
        head = new BucketNode(value, head);
        size++;
    }

    /**
     * Returns all values in list as an array.
     * @return array of values in list
     */
    public int[] values() {
        int[] contents = new int[size];
        BucketNode iteration = head;

        for (int i = 0; i < size; i++) {
            contents[i] = iteration.getValue();
            iteration = iteration.getChild();
        }
        return contents;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        BucketNode current = head;
        while (current != null) {
            result.append(current.toString()).append(" → ");
            current = current.getChild();
        }
        return result.toString();
    }
}
