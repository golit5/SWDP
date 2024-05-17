package lab;
public interface Handler {
    void setNext(Handler handler);
    boolean handle(String string);
}
