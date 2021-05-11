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
    SELECT_ALL(11),
    SHOW_TOOL_BAR(12),
    SHOW_STATUS_BAR(13),
    SHOW_CONSOLE(14),
    COMPILE_RUN(15),
    COMPILE(16),
    RUN(17),
    STOP(18),
    HELP(19),
    ABOUT(20);

    private final int ID;

    MenuOptions(int id) {
        this.ID = id;
    }

    public final int getID() {
        return ID;
    }
}
