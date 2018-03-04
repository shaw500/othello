package helpers;

import othello.ui.Writer;

public class FakeWriter implements Writer {

    StringBuilder builder = new StringBuilder();

    @Override
    public void write(String text) {
        builder.append(text);
    }

    @Override
    public void writeLine(String line) {
        builder.append(line).append("\n");
    }

    public String getOutput() {
        String output = builder.toString();
        clear();
        return output;
    }

    public void clear() {
        builder = new StringBuilder();
    }


}
