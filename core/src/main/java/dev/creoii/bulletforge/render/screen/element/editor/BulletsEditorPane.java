package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.object.definition.BulletDefinition;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.EditorUtils;

public class BulletsEditorPane extends AbstractEditorPane {
    public BulletsEditorPane(EditorScreen parent) {
        super(parent.getMain().getI18n().get("editor.pane.title.bullets"), parent, GlobalAssets.SKIN);
    }

    @Override
    public ClickListener onAddElement() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getEditor().getConfig().bullets().addBullet(BulletDefinition.DEFAULT.copy());
                refresh();
            }
        };
    }

    public Table onRefresh() {
        return EditorUtils.createObjectListEditor(getEditor().getConfig().bullets().values(), getSkin());
    }
}
