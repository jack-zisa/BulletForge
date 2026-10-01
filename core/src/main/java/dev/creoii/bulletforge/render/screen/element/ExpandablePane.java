package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ExpandablePane extends Table {
    private final Container<Actor> contentContainer;
    private Actor content;
    private boolean expanded;

    public ExpandablePane(Actor content) {
        this.content = content;

        top().left();
        defaults().growX().left();

        contentContainer = new Container<>(content);
        contentContainer.top().left();

        add(contentContainer).growX().left().row();

        setExpanded(false);
    }

    public void setContent(Actor content) {
        this.content = content;
        setExpanded(expanded);
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        contentContainer.setActor(expanded ? content : null);

        invalidate();
        invalidateHierarchy();
    }

    public boolean isExpanded() {
        return expanded;
    }
}
