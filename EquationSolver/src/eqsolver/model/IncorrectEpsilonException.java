package eqsolver.model;

public class IncorrectEpsilonException extends Exception {
    @Override
    public String toString() {
        return "Не соблюдено условие ε > 0";
    }
}
