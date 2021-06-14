package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.vm.Instruction;
import br.univali.ttoproject.vm.VMConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AsmTablePane extends JTable {

    public AsmTablePane() {
        super(new DefaultTableModel(new String[][]{}, new String[]{"ID", "INSTRUCTION", "PARAMETER"}));
    }

    public void setProgram(ArrayList<Instruction<Integer, Object>> program) {
        var tableModel = (DefaultTableModel) getModel();
        tableModel = new DefaultTableModel(new String[][]{}, new String[]{"ID", "INSTRUCTION", "PARAMETER"});
        for (var instr : program) {
            tableModel.addRow(new String[]{
                    instr.getOpCode().toString(),
                    VMConstants.instrNames[instr.getOpCode()],
                    instr.getParameter().toString()
            });
        }
        setModel(tableModel);
    }

}
