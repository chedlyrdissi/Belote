package adapter;

public interface ItemDeleteHandler<T> {
    void selectToDeleteGame( boolean canDelete );
    void selectGame(T selected);
}
