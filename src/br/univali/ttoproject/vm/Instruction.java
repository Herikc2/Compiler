package br.univali.ttoproject.vm;

public class Instruction<L, R> {

    private final L left;
    private final R right;

    public Instruction(L left, R right) {
        assert left != null;
        assert right != null;

        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Instruction)) return false;
        Instruction pairo = (Instruction) o;
        return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
    }

}
