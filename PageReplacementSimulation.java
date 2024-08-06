//Description of the Program

/*This Java program implements FIFO and LRU page replacement strategies with the given page reference sequence. It performs the sequence reading from an input file and verifies command-line arguments for proper execution. The simulation has two versions, the first is implemented by utilizing the FIFO policy for the data structures and the second is done by using the LRU policy for the tracking memory state and page faults. The simulation is then carried out which displays both policies' number of page faults and final memory states.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class PageReplacementSimulation {
    public static void main(String[] args) {
        // Check if the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: java PageReplacementSimulation pagereffile numberofframes");
            return;
        }

        // Extract command-line arguments
        String filename = args[0];
        int numFrames = Integer.parseInt(args[1]);

        // Validate the number of frames
        if (numFrames < 1 || numFrames > 10) {
            System.out.println("Number of frames must be between 1 and 10.");
            return;
        }

        try {
            // Open and read from the input file
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            String[] references = line.split(" ");

            // Simulate FIFO page replacement policy
            SimulationResult fifoResult = simulateFIFO(references, numFrames);

            // Simulate LRU page replacement policy
            SimulationResult lruResult = simulateLRU(references, numFrames);

            // Print results
            System.out.println("FIFO: " + fifoResult.pageFaults + " page faults");
            System.out.println("Final state of memory: " + fifoResult.memoryState);
            System.out.println("LRU: " + lruResult.pageFaults + " page faults");
            System.out.println("Final state of memory: " + lruResult.memoryState);

            reader.close();
        } catch (IOException e) {
            // Handle file I/O exceptions
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Simulate FIFO page replacement policy
    private static SimulationResult simulateFIFO(String[] references, int numFrames) {
        Queue<Integer> queue = new ArrayDeque<>();
        Map<Integer, Boolean> memory = new HashMap<>();
        StringBuilder memoryState = new StringBuilder();
        int pageFaults = 0;

        for (String ref : references) {
            int page = Integer.parseInt(ref);
            if (!memory.containsKey(page)) {
                if (queue.size() == numFrames) {
                    int removedPage = queue.poll();
                    memoryState.append(removedPage).append(" ");
                    memory.remove(removedPage);
                }
                queue.offer(page);
                memory.put(page, true);
                pageFaults++;
            }
        }

        return new SimulationResult(pageFaults, memoryState.toString().trim());
    }

    // Simulate LRU page replacement policy
    private static SimulationResult simulateLRU(String[] references, int numFrames) {
        Map<Integer, Integer> memory = new HashMap<>();
        StringBuilder memoryState = new StringBuilder();
        int pageFaults = 0;
        int time = 0;

        for (String ref : references) {
            int page = Integer.parseInt(ref);
            time++;
            if (!memory.containsKey(page)) {
                if (memory.size() == numFrames) {
                    int minTime = Integer.MAX_VALUE;
                    int minPage = -1;
                    for (Map.Entry<Integer, Integer> entry : memory.entrySet()) {
                        if (entry.getValue() < minTime) {
                            minTime = entry.getValue();
                            minPage = entry.getKey();
                        }
                    }
                    memoryState.append(minPage).append(" ");
                    memory.remove(minPage);
                }
                memory.put(page, time);
                pageFaults++;
            } else {
                memory.put(page, time);
            }
        }

        return new SimulationResult(pageFaults, memoryState.toString().trim());
    }

    // Class to hold simulation results
    private static class SimulationResult {
        int pageFaults;
        String memoryState;

        SimulationResult(int pageFaults, String memoryState) {
            this.pageFaults = pageFaults;
            this.memoryState = memoryState;
        }
    }
}
