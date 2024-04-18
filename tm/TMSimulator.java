import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class TMSimulator {
    
    /** 
     * // Main method that takes command line arguments
     * @param args
     */
    public static void main(String[] args) {
        String fileName = args[0];  // Get file name from command line argument
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));  // Open the file for reading
            int numberOfStates = Integer.parseInt(reader.readLine());  // Read the number of states from the first line
            int numberOfSymbols = Integer.parseInt(reader.readLine());  // Read the number of symbols from the second line

            TM tm = new TM(numberOfStates);  // Create a new Turing Machine instance with the specified number of states
            // Read each transition from the file and add to the Turing Machine
            for (int state = 0; state < numberOfStates - 1; state++) {  // Iterate over states (excluding the halting state)
                for (int symbol = 0; symbol <= numberOfSymbols; symbol++) {  // Iterate over symbols (including the blank symbol)
                    String line = reader.readLine();  // Read the transition line
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");  // Tokenize the line by comma
                    int nextState = Integer.parseInt(tokenizer.nextToken());  // Parse the next state
                    char writeSymbol = tokenizer.nextToken().charAt(0);  // Parse the write symbol
                    char direction = tokenizer.nextToken().charAt(0);  // Parse the direction to move the head

                    tm.addTransition(state, (char) ('0' + symbol), new TM.Transition(nextState, writeSymbol, direction));
                }
            }

            String input = reader.readLine();  // Read the input string for the Turing Machine
            if (input != null && !input.isEmpty()) {
                tm.processInput(input);  // Process the input string if it is not empty
            }

            tm.run();  // Run the Turing Machine simulation
            tm.printTape();  // Print the tape contents after the simulation

        } catch (IOException e) {
            e.printStackTrace();  // Print the stack trace if an IOException occurs
        }
    }
}
