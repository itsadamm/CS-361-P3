import java.util.HashMap;
import java.util.Map;

public class TM {
    private int currentState;
    private Map<Integer, Map<Character, Transition>> transitions;
    private Map<Integer, Character> tape;
    private int headPosition;

    public TM(int numberOfStates) {
        this.currentState = 0;  // Start state
        this.transitions = new HashMap<>();
        this.tape = new HashMap<>();
        this.headPosition = 0;  // Start at the center of the tape
        tape.put(headPosition, '0');  // Initialize tape with blank symbol
    }

    public void addTransition(int state, char readSymbol, Transition transition) {
        transitions.computeIfAbsent(state, k -> new HashMap<>()).put(readSymbol, transition);
    }

    public void processInput(String input) {
        for (char c : input.toCharArray()) {
            tape.put(headPosition++, c);
        }
        headPosition = 0;  // Reset to start position after input is set
    }

    public void run() {
        while (true) {
            char readSymbol = tape.getOrDefault(headPosition, '0');
            Map<Character, Transition> stateTransitions = transitions.get(currentState);
            if (stateTransitions == null || !stateTransitions.containsKey(readSymbol)) {
                break;  // Halt if no transitions available for this state and symbol
            }
            Transition t = stateTransitions.get(readSymbol);
            tape.put(headPosition, t.writeSymbol);
            currentState = t.nextState;
            headPosition += (t.direction == 'R') ? 1 : -1;
        }
    }

    public void printTape() {
        tape.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> System.out.print(entry.getValue()));
        System.out.println();
    }

    public static class Transition {
        int nextState;
        char writeSymbol;
        char direction;

        public Transition(int nextState, char writeSymbol, char direction) {
            this.nextState = nextState;
            this.writeSymbol = writeSymbol;
            this.direction = direction;
        }
    }
}
