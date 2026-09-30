package dev.creoii.bulletforge.render.screen.element.editor;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import dev.creoii.bulletforge.util.editor.EditorGenerator;

public class ExpandableEditorPane extends Table {
    private final Container<Table> contentContainer;
    private final Table content;
    private boolean expanded;

    public ExpandableEditorPane(Object target, Skin skin) {
        top().left();
        defaults().growX().left();

        content = EditorGenerator.createEditorTable(target, skin);
        content.top().left();

        contentContainer = new Container<>(content);
        contentContainer.top().left();
        contentContainer.setVisible(true);

        add(contentContainer).growX().left().row();

        setExpanded(false);
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;

        if (expanded) {
            contentContainer.setActor(content);
        } else contentContainer.setActor(null);

        invalidate();
        invalidateHierarchy();
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public boolean hasScrollFocus() {
        return true;
    }
}
