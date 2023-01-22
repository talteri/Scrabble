package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * The BookScrabbleHandler class handles client connections and processes requests
 * by using the DictionaryManager to look up words in books. It implements the
 * ClientHandler interface.
 */
public class BookScrabbleHandler implements ClientHandler {
    private final DictionaryManager dictionaryManager; // Handles the dictionary operations

    /**
     * Initializes the dictionary manager.
     */
    public BookScrabbleHandler() {
        this.dictionaryManager = new DictionaryManager();
    }

    /**
     * Handles client connections by reading input from the client, parsing it,
     * and passing it to the dictionary manager for processing. The response is
     * then written back to the client.
     *
     * @param inFromClient Input stream to read data from the client.
     * @param outToClient Output stream to write data to the client.
     */
    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
                PrintWriter out = new PrintWriter(outToClient)
        ) {
            // Read client message and split it into parts
            String clientMessage = in.readLine();
            String[] parts = clientMessage.split(",");
            String type = parts[0];
            String[] books = Arrays.copyOfRange(parts, 1, parts.length - 1);
            String query = parts[parts.length - 1];
            boolean response = false;
            // Check the request type and call the appropriate method on the dictionary manager
            if (type.equals("Q")) {
                // looping through all books
                for(String book : books){
                    response = dictionaryManager.query(book, query);
                    if(response)
                        break;
                }

            } else if (type.equals("C")) {
                // looping through all books
                for(String book : books){
                    response = dictionaryManager.challenge(book, query);
                    if(response)
                        break;
                }
            }
            // Write response to client
            if(response)
                out.println("true"+"\n");
            else
                out.println("false"+"\n");
            out.flush();
        } catch (IOException ignored) {}
    }

    /**
     * Closes the resources used by the handler.
     * This method does not do anything in the current implementation.
     */
    @Override
    public void close() {
    }
}
