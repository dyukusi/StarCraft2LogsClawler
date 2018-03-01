package github.dyukusi;

public enum Region {
    US(1),
    EU(2),
    KR(3);

    private int id;

    Region(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
