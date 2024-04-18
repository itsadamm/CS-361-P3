import java.util.HashMap;
import java.util.Map;

public class TM {
    private int currentState;  // Current state of the Turing Machine
    private Map<Integer, Map<Character, Transition>> transitions;  // Mapping of states to their transitions
    private Map<Integer, Character> tape;  // Representation of the Turing Machine's tape
    private int headPosition;  // Current position of the tape head

    // Constructor for the Turing Machine
    public TM(int numberOfStates) {
        this.currentState = 0;  // Initialize at start state
        this.transitions = new HashMap<>();  // Initialize transitions map
        this.tape = new HashMap<>();  // Initialize tape
        this.headPosition = 0;  // Start at the center of the tape
        tape.put(headPosition, '0');  // Initialize tape with blank symbol ('0')
    }

    // Method to add a transition to the Turing Machine
    public void addTransition(int state, char readSymbol, Transition transition) {
        // Create a new map for the state if it doesn't already exist and add the transition
        transitions.computeIfAbsent(state, k -> new HashMap<>()).put(readSymbol, transition);
    }

    // Method to process the input string and set up the tape
    public void processInput(String input) {
        for (char c : input.toCharArray()) {
            tape.put(headPosition++, c);  // Place each character of the input on the tape
        }
        headPosition = 0;  // Reset head position to the start after setting up the input
    }

    // Main method to run the Turing Machine simulation
    public void run() {
        while (true) {
            char readSymbol = tape.getOrDefault(headPosition, '0');  // Read the symbol at the current head position
            Map<Character, Transition> stateTransitions = transitions.get(currentState);
            // Check if there are any transitions for the current state and read symbol
            if (stateTransitions == null || !stateTransitions.containsKey(readSymbol)) {
                break;  // Halt if no transitions available (i.e., reaching a halting condition)
            }
            Transition t = stateTransitions.get(readSymbol);
            tape.put(headPosition, t.writeSymbol);  // Write the specified symbol to the tape
            currentState = t.nextState;  // Move to the next state
            headPosition += (t.direction == 'R') ? 1 : -1;  // Move the head left or right as specified
        }
    }

    // Method to print the contents of the tape
    public void printTape() {
        tape.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> System.out.print(entry.getValue()));  // Print each tape cell in order
        System.out.println();
    }

    // Inner class representing a transition in the Turing Machine
    public static class Transition {
        int nextState;  // State to transition to
        char writeSymbol;  // Symbol to write to the tape
        char direction;  // Direction to move the tape head ('L' for left, 'R' for right)

        public Transition(int nextState, char writeSymbol, char direction) {
            this.nextState = nextState;
            this.writeSymbol = writeSymbol;
            this.direction = direction;
        }
    }
}
