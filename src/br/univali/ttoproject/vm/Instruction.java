package br.univali.ttoproject.vm;

public class Instruction<L, R> {

    private final L opCode;
    private final R parameter;


    public Instruction(L opCode, R parameter) {
        assert opCode != null;
        assert parameter != null;

        this.opCode = opCode;
        this.parameter = parameter;
    }

    public L getOpCode() {
        return opCode;
    }

    public R getParameter() {
        return parameter;
    }

}
