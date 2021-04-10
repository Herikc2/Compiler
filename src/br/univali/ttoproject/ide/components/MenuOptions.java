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
    SHOW_TOOL_BAR(11),
    SHOW_STATUS_BAR(12),
    SHOW_CONSOLE(13),
    COMPILE_RUN(14),
    COMPILE(15),
    RUN(16),
    STOP(17),
    HELP(18),
    ABOUT(19);

    private int id;

    MenuOptions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
