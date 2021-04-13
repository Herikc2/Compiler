package br.univali.ttoproject.ide.components.editor;

public class State {

    private String t;
    private int p;

    public State(String t, int p) {
        this.t = t;
        this.p = p;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }
}
