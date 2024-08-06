//Description of the program

/*The C program executes the FIFO (First-in-First-out) and LRU (Least Recently Used) page replacement algorithms using the given page reference sequence and a defined number of frames. It takes two command-line arguments: The file that counts the frame numbers and page references we refer to as a memory file. The fifo method implements the FIFO strategy using a circular queue of page frames whereas the lru method implements the LRU algorithm by observing the timestamps of page references and removing the least recently used page. Following the page reference processing, the program displays the number of page faults and the final memory states for both algorithms. The program offers input validation, error handling, and modular design for better code organization and maintainability.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_FRAMES 10

// Function prototypes
void fifo(int *pages, int n, int frames);
void lru(int *pages, int n, int frames);

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("Usage: %s <page_reference_file> <number_of_frames>\n", argv[0]);
        return 1;
    }

    FILE *file = fopen(argv[1], "r");
    if (file == NULL) {
        printf("Error: Cannot open file %s\n", argv[1]);
        return 1;
    }

    int frames = atoi(argv[2]);
    if (frames < 1 || frames > 10) {
        printf("Error: Number of frames must be between 1 and 10\n");
        fclose(file);
        return 1;
    }

    // Read page references from file
    int pages[1024], n = 0;
    while (fscanf(file, "%d", &pages[n]) == 1) {
        n++;
    }
    fclose(file);

    printf("FIFO: ");
    fifo(pages, n, frames);
    printf("LRU: ");
    lru(pages, n, frames);

    return 0;
}

void fifo(int *pages, int n, int frames) {
    int memory[MAX_FRAMES], faults = 0, front = 0, rear = 0;
    memset(memory, -1, sizeof(memory));

    for (int i = 0; i < n; i++) {
        int page = pages[i];
        int found = 0;

        // Check if page is already in memory
        for (int j = 0; j < frames; j++) {
            if (memory[j] == page) {
                found = 1;
                break;
            }
        }

        // Page fault
        if (!found) {
            faults++;
            memory[rear] = page;
            rear = (rear + 1) % frames;
            if (rear == front) {
                front = (front + 1) % frames;
            }
        }
    }

    printf("%d page faults\n", faults);
    printf("Final state of memory: ");
    for (int i = 0; i < frames; i++) {
        printf("%d ", memory[i]);
    }
    printf("\n");
}

void lru(int *pages, int n, int frames) {
    int memory[MAX_FRAMES], faults = 0, timestamps[1024];
    memset(memory, -1, sizeof(memory));
    memset(timestamps, -1, sizeof(timestamps));

    int time = 0;
    for (int i = 0; i < n; i++) {
        int page = pages[i];
        int found = 0, lru_index = -1;

        // Check if page is already in memory
        for (int j = 0; j < frames; j++) {
            if (memory[j] == page) {
                found = 1;
                timestamps[page] = time;
                break;
            }
        }

        // Page fault
        if (!found) {
            faults++;
            int min_time = time + 1;

            // Find least recently used page
            for (int j = 0; j < frames; j++) {
                if (timestamps[memory[j]] < min_time) {
                    min_time = timestamps[memory[j]];
                    lru_index = j;
                }
            }

            // Replace least recently used page
            memory[lru_index] = page;
            timestamps[page] = time;
        }

        time++;
    }

    printf("%d page faults\n", faults);
    printf("Final state of memory: ");
    for (int i = 0; i < frames; i++) {
        printf("%d ", memory[i]);
    }
    printf("\n");
}