package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.editor.EditorGenerator;

public class AttacksEditorPane extends Table {
    private final EditorScreen parent;

    public AttacksEditorPane(EditorScreen parent) {
        this.parent = parent;
        top().left();
        defaults().growX().top();
        add(EditorGenerator.createAttackListEditor(parent, parent.getConfig().attacks(), GlobalAssets.SKIN)).growX().row();
    }

    public void refresh() {
        clearChildren();

        add(EditorGenerator.createAttackListEditor(parent, parent.getConfig().attacks(), GlobalAssets.SKIN)).growX().row();

        invalidateHierarchy();
    }
}
