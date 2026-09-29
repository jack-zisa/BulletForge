package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.editor.EditorGenerator;

public class BulletsEditorPane extends Table {
    public BulletsEditorPane(EditorScreen parent) {
        top().left();
        defaults().growX().top();
        add(EditorGenerator.createBulletDictionaryEditor(parent.getConfig().bullets(), GlobalAssets.SKIN)).growX().row();
    }
}
