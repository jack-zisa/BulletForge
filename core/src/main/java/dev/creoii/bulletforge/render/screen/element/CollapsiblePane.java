package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CollapsiblePane extends Table {
    private final Container<Actor> contentContainer;
    private final TextButton headerButton;

    private Actor content;
    private boolean expanded;

    public CollapsiblePane(String title, Actor content, Skin skin) {
        this.content = content;

        top().left();
        defaults().growX().left();

        headerButton = new TextButton(title, skin);
        headerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setExpanded(!expanded);
            }
        });

        add(headerButton).growX().left().row();

        contentContainer = new Container<>();
        contentContainer.fillX().top().left();

        add(contentContainer).growX().left().row();

        setExpanded(false);
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        contentContainer.setActor(expanded ? content : null);
        invalidateHierarchy();
    }
}
