package othello.ui;

public class WriterImpl implements Writer {
    @Override
    public void write(String text) {
        System.out.print(text);
    }

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
