import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PriorityQueue<T extends Comparable<T>> {

        class Node {

            T key;
            Node next;

            public Node(T key, Node next) {
                this.key = key;
                this.next = next;
            }
        }

        int size=0;
        Node head=null;

        //get parent index
        private int parent(int child) {
            return (child - 1) / 2;
        }

        //get left child index
        private int leftChild(int parent) {
            return 2 * parent + 1;
        }

        //get right child index
        private int rightChild(int parent) {
            return 2 * (parent + 1);
        }
        //get node by index
        private Node getNode(int index)
        {
            Node node=head;
            for (int i = 0; i <index ; i++) {
                node=node.next;
            }
            return node;
        }

        // swaps any two elements
        private void swap(Node nodeI, Node nodeJ) {
            T value = nodeI.key;
            nodeI.key=nodeJ.key;
            nodeJ.key=value;
        }

        //check heap i node is less than j node
        private boolean isLessThan(Node nodeI, Node nodeJ) {
            return nodeI.key.compareTo(nodeJ.key)<0;
        }

        // starting at the final leaf,this method move the inserted element up
        // the tree until it satisfies the  heap-order,
        private void reHeapUp(int index) {
            if (index == 0) {
                return;
            }
            Node node = getNode(index);
            Node parent = getNode(parent(index));
            if (isLessThan(node, parent)) {
                swap(parent, node);
                reHeapUp(parent(index));
            }
        }

        //insert data
        public void insert(T value) {
            if (size==0)
            {
                head=new Node(value,null);
            }else
            {
                getNode(size-1).next=new Node(value,null);
                reHeapUp(size);
            }
            size++;
        }
        //starting at the root, this method should move the former final leaf element down the tree
        //until it satisfies the  heap-order, swapping places with its most important child until it
        //is less important than its parent and more important than its children.
        private void reHeapDown(int index) {
            //Check index
            if (index >= size || index < 0) {
                return;
            }
            //No more children exist in the current sub-heap.
            if (leftChild(index) >= size && rightChild(index) >= size) {
                return;
            }
            boolean compareLeft, compareRight, compareChildren;
            int swapIndex;
            Node swapNode;
            Node node;
            Node leftChild;
            Node rightChild =null;
            //If a left element does not exist in the heap the right element also not exist so this is the
            //current place
            if (leftChild(index) >= size) {
                return;
            }
            //If a right element does not exist in the heap.
            else if (rightChild(index) >= size) {
                node = getNode(index);
                leftChild=getNode(leftChild(index));
                compareRight = true;
                compareLeft = isLessThan(node,leftChild);
                compareChildren = false;
            }
            //There are two children in the current sub-heap
            else {
                node = getNode(index);
                leftChild=getNode(leftChild(index));
                rightChild = getNode(rightChild(index));
                compareLeft = isLessThan(node, leftChild);
                compareRight = isLessThan(node,rightChild);
                compareChildren = isLessThan(rightChild,leftChild);
            }
            //If the key is at the right position
            if (compareRight && compareLeft) {
                return;
            }
            //Find the greater children in which to pursue.
            else {
                if (compareChildren) {
                    swapIndex=rightChild(index);
                    swapNode = rightChild;
                } else {
                    swapIndex=leftChild(index);
                    swapNode = leftChild;
                }
            }
            swap(node, swapNode);
            reHeapDown(swapIndex);
        }
        //delete the min data and return
        public T delMin() {
            T min = head.key;
            size--;
            if (size > 1) {
                Node last = getNode(size);
                head.key=last.key;
                getNode(size-1).next=null;
                reHeapDown(0);
            }else
            {
                head=head.next;
            }
            return min;
        }
        // use for test
        public boolean isEmpty()
        {
            return size==0;
        }
        // use for test
        @Override
        public String toString() {
            String result = "PriorityQueue{" +
                    "size=" + size +
                    ", data=";
            Node node = head;
            for (int i = 0; i <size ; i++) {
                result+=node.key+" ";
                node=node.next;
            }
            return result+"}";

        }
        public static void main(String[] args) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            Random random = new Random();
            //Part2 test of insert and delMin
            System.out.println("Part2 test of insert and delMin");
            System.out.println("-----------------------------------------------------------");
            for (int i = 0; i < 20; i++) {
                int value = random.nextInt(100);
                priorityQueue.insert(value);
                System.out.println("Insert value: "+value+" "+priorityQueue);
            }
            while (!priorityQueue.isEmpty())
            {
                System.out.println("Delete min: "+priorityQueue.delMin()+" "+priorityQueue);
            }
            //Part4 data use for simple performance benchmark
            System.out.println("\nPart4 data use for  simple performance benchmark");
            System.out.println("-----------------------------------------------------------");
            List<Integer> data = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                data.add(random.nextInt(100000));
            }
            int[] size = new int[]{4000,8000,16000,32000,64000};
            for (int i = 0; i <5 ; i++) {
                long startTime=System.currentTimeMillis();
                for (int j = 0; j < size[i]; j++) {
                    priorityQueue.insert(data.get(j));
                }
                long insertEndTime = System.currentTimeMillis();
                System.out.println("Insert size: "+size[i]+" Time: "+(insertEndTime-startTime));
                while (!priorityQueue.isEmpty())
                {
                    priorityQueue.delMin();
                }
                long delEndTime = System.currentTimeMillis();
                System.out.println("DelMin size: "+size[i]+" Time: "+(delEndTime-insertEndTime));
            }
            //Part5 data use for draw structure
            System.out.println("\nPart5 data use for draw structure");
            System.out.println("-----------------------------------------------------------");
            long seed = 100;
            random = new Random(seed);
            for (int i = 0; i <7 ; i++) {
                priorityQueue.insert(random.nextInt(100));
            }
            System.out.println(priorityQueue);
        }

    }


