package helpers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import othello.core.Position;
import othello.core.exceptions.InvalidPositionException;

public class InvalidPositionExceptionMatcher extends TypeSafeMatcher<InvalidPositionException> {

    private InvalidPositionException expected;

    public static InvalidPositionExceptionMatcher failsWithInvalidPosition(Position position) {
        return new InvalidPositionExceptionMatcher(new InvalidPositionException(position, null));
    }

    public InvalidPositionExceptionMatcher andMessage(String expectedMessage) {
        return new InvalidPositionExceptionMatcher(new InvalidPositionException(expected.invalidPosition, expectedMessage));
    }

    private InvalidPositionExceptionMatcher(InvalidPositionException expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(InvalidPositionException actual) {
        boolean messageMatches = expected.getMessage().equals(actual.getMessage());
        boolean positionMatches = expected.invalidPosition.equals(actual.invalidPosition);
        return messageMatches && positionMatches;
    }

    @Override
    public void describeTo(Description description) {
        renderExpectations(description, expected);
    }

    @Override
    protected void describeMismatchSafely(InvalidPositionException actual, Description description) {
        renderExpectations(description, actual);
    }

    private void renderExpectations(Description description, InvalidPositionException exception) {
        description.appendText("exception with invalid position (");
        description.appendValue(exception.invalidPosition.x);
        description.appendText(", ");
        description.appendValue(exception.invalidPosition.y);
        description.appendText("), and message ");
        description.appendValue(exception.getMessage());
    }
}
