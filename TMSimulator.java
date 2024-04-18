import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class TMSimulator {
    public static void main(String[] args) {
        String fileName = args[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            int numberOfStates = Integer.parseInt(reader.readLine());
            int numberOfSymbols = Integer.parseInt(reader.readLine());

            TM tm = new TM(numberOfStates);
            for (int state = 0; state < numberOfStates - 1; state++) {
                for (int symbol = 0; symbol <= numberOfSymbols; symbol++) {
                    String line = reader.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");
                    int nextState = Integer.parseInt(tokenizer.nextToken());
                    char writeSymbol = tokenizer.nextToken().charAt(0);
                    char direction = tokenizer.nextToken().charAt(0);

                    tm.addTransition(state, (char) ('0' + symbol), new TM.Transition(nextState, writeSymbol, direction));
                }
            }

            String input = reader.readLine();
            if (input != null && !input.isEmpty()) {
                tm.processInput(input);
            }

            tm.run();
            tm.printTape();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
