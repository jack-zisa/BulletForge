package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import dev.creoii.bulletforge.render.screen.element.ExpandablePane;
import dev.creoii.bulletforge.util.editor.EditorGenerator;

public class ExpandableEditorPane extends ExpandablePane {
    public ExpandableEditorPane(Object target, Skin skin) {
        super(EditorGenerator.createEditorTable(target, skin));
    }
}
