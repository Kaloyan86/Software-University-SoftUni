package core.io;

import core.io.interfacese.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements Reader {

    private BufferedReader reader;

    public ConsoleReader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine() {
        String result = null;

        try {
            result = this.reader.readLine();
        } catch (IOException ignored) {
            ;
        }

        return result;
    }
}
