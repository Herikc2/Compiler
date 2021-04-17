package br.univali.ttoproject.ide.components.editor;

import br.univali.ttoproject.ide.util.Debug;

import javax.swing.text.*;

public class TabSizeEditorKit extends StyledEditorKit {

    public static float TAB_WIDTH;

    public ViewFactory getViewFactory() {
        return new TTOViewFactory();
    }

    static class TTOViewFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName:
                        return new LabelView(elem);
                    case AbstractDocument.ParagraphElementName:
                        return new TTOParagraphView(elem);
                    case AbstractDocument.SectionElementName:
                        return new BoxView(elem, View.Y_AXIS);
                    case StyleConstants.ComponentElementName:
                        return new ComponentView(elem);
                    case StyleConstants.IconElementName:
                        return new IconView(elem);
                }
            }
            return new LabelView(elem);
        }

    }

    static class TTOParagraphView extends ParagraphView {

        public TTOParagraphView(Element elem) {
            super(elem);
        }

        public float nextTabStop(float x, int tabOffset) {
            TabSet tabs = getTabSet();
            if (tabs == null) {
                return getTabBase() + ((x / TAB_WIDTH + 1) * TAB_WIDTH);
            }
            return super.nextTabStop(x, tabOffset);
        }

    }

}
