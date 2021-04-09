package br.univali.ttoproject.ide.components;

public enum MenuOptions {
    NEW(0),
    OPEN(1),
    SAVE(2),
    SAVE_AS(3),
    SETTINGS(4),
    EXIT(5),
    UNDO(6),
    REDO(7),
    CUT(8),
    COPY(9),
    PASTE(10),
    TOOL_BAR(11),
    STATUS_BAR(12),
    COMPILE(13),
    RUN(14),
    HELP(15),
    ABOUT(16);

    private int id;

    MenuOptions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
