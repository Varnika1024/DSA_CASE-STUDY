import java.util.*;

class BlockStream {
    private long[] data;
    private int cursor = 0;

    BlockStream(long[] items) {
        this.data = items;
    }

    boolean hasNext() {
        return cursor < data.length;
    }

    long peek() {
        return data[cursor];
    }

    void moveNext() {
        cursor++;
    }
}

class HeapNode {
    long cost;
    int sourceId;

    HeapNode(long cost, int sourceId) {
        this.cost = cost;
        this.sourceId = sourceId;
    }
}

public class Main {
    public static void processHeapSort(List<BlockStream> streams) {
        PriorityQueue<HeapNode> heap = new PriorityQueue<>((a, b) -> Long.compare(a.cost, b.cost));

        for (int i = 0; i < streams.size(); i++) {
            if (streams.get(i).hasNext()) {
                heap.add(new HeapNode(streams.get(i).peek(), i));
            }
        }

        while (!heap.isEmpty()) {
            HeapNode top = heap.poll();
            System.out.print(top.cost + " ");

            int id = top.sourceId;
            streams.get(id).moveNext();

            if (streams.get(id).hasNext()) {
                heap.add(new HeapNode(streams.get(id).peek(), id));
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        long[] b1 = {10, 25, 47, 89};
        long[] b2 = {12, 30, 51, 75};
        long[] b3 = {8, 19, 41, 92};
        long[] b4 = {15, 33, 60, 80};
        long[] b5 = {22, 38, 55, 88};
        long[] b6 = {11, 28, 49, 85};

        List<BlockStream> activeStreams = new ArrayList<>();
        activeStreams.add(new BlockStream(b1));
        activeStreams.add(new BlockStream(b2));
        activeStreams.add(new BlockStream(b3));
        activeStreams.add(new BlockStream(b4));
        activeStreams.add(new BlockStream(b5));
        activeStreams.add(new BlockStream(b6));

        processHeapSort(activeStreams);
    }
}
