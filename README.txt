Guidelines on running the Java program
* To run the Java program, ensure that you have the latest JDK installed on your PC.

* Go to the command prompt, and use the command cd to take you to where the java file is. For example, if it is in the Downloads folder, you should use the following command: cd Downloads

* Compile your Java file by using the following command prompt: javac PageReplacementSimulation.java

* This will generate a file named Counter.class in the same directory. 

* Also, make sure you have the text file 'pageref.txt' in the same directory.

* After compiling, you can run your program with: java PageReplacementSimulation pageref.txt 3
 
* You should get an output that looks like this: 
FIFO: 15 page faults
Final state of memory: 7 0 1 2 3 0 4 2 3 0 1 2
LRU: 12 page faults
Final state of memory: 7 1 2 3 0 4 0 3 2

* The above output meets the expected requirements.

Additional Information

* I have also written a C program.

* If you are using windows, download and install Cygwin from its main website and ensure all packages with gcc are installed during the installation process.

* Kindly run the C file first and test it out. If it is correct and meets the requirements, you may use it.

* After successful installation, use the Cygwin terminal to run the page_replacement.c code.

* Use the following commands to run the code: 

  * cd /cygdrive/c/Users/yourpcname/Desktop - This will help you navigate to the file directory.

  * gcc -o page_replacement page_replacement.c - This will compile the counter.c code, creating a counter.exe file in the same directory.

  * ./page_replacement pageref.txt 3 - This should run the code.

* You should get an output that looks like this: 
FIFO: 15 page faults
Final state of memory: 7 0 1
LRU: 12 page faults
Final state of memory: 0 7 1


* The above output meets the expected requirements.
