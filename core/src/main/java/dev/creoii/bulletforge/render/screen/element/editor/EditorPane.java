package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.editor.EditorGenerator;

public class EditorPane extends Table {
    public EditorPane(EditorScreen parent) {
        top().left();
        defaults().growX().top();

        Table attackTable = EditorGenerator.createEditorTable(parent.getConfig().attack(), GlobalAssets.SKIN);
        Table bulletTable = EditorGenerator.createEditorTable(parent.getConfig().bullet(), GlobalAssets.SKIN);

        add(attackTable).growX().row();
        add(bulletTable).growX().row();
    }
}
