package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.object.definition.AttackDefinition;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.util.EditorUtils;

public class AttacksEditorPane extends AbstractEditorPane {
    public AttacksEditorPane(EditorScreen parent) {
        super(parent.getMain().getI18n().get("editor.pane.title.attacks"), parent, GlobalAssets.SKIN);
    }

    @Override
    public ClickListener onAddElement() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AttackDefinition copy = AttackDefinition.DEFAULT.copy();
                getEditor().getConfig().attacks().add(copy);
                getEditor().getAttackManager().addAttack(copy);
                refresh();
            }
        };
    }

    public Table onRefresh() {
        return EditorUtils.createObjectListEditor(getEditor().getConfig().attacks(), getSkin());
    }
}
