package dev.creoii.bulletforge.util.manager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.render.screen.element.Tab;

public class TabManager extends Table {
    private final BulletForge main;
    private int selectedTabIndex;

    public TabManager(BulletForge main) {
        this.main = main;
        left();
    }

    @Override
    public <T extends Actor> Cell<T> add(T actor) {
        if (actor instanceof Tab) {
            return super.add(actor);
        } else throw new IllegalArgumentException("Invalid actor: " + actor.getClass().getSimpleName() + ". Must be a Tab.");
    }

    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    public void removeTab(int i) {
        Tab removed = (Tab) removeActorAt(i, true);
        if (removed != null) {
            updateIndexes();

            if (selectedTabIndex >= getChildren().size) {
                selectedTabIndex = Math.max(0, getChildren().size - 1);
            }

            if (getChildren().size > 0) selectTab(selectedTabIndex);
            else main.getUserInterface().getScreenContainer().clearChildren();
        }
    }

    public void addTab(int i, Tab tab) {
        if (i == -1) {
            add(tab);
            i = getChildren().size - 1;
        } else addActorAt(i, tab);

        tab.setIndex(i);
        if (getChildren().size > 1) updateIndexes();
        selectTab(i);
    }

    // TODO: Implement
    public void duplicateTab(int i) {
        Tab tab = (Tab) getChild(i);
        if (tab != null) {
            int index = i + 1;
            Tab newTab = Tab.createEditor(main, tab.getTitle(), tab.getScreen());
            addTab(index, newTab);
        }
    }

    public void updateIndexes() {
        for (int i = 0; i < getChildren().size; i++) {
            ((Tab) getChild(i)).setIndex(i);
        }
    }

    public void selectTab(int i) {
        if (i < 0 || i >= getChildren().size) {
            main.getUserInterface().getScreenContainer().clearChildren();
            return;
        }

        selectedTabIndex = i;

        for (int j = 0; j < getChildren().size; j++) {
            ((Tab) getChild(j)).setSelected(j == i);
        }

        main.getUserInterface().getScreenContainer().clearChildren();
        main.getUserInterface().setActiveScreen(((Tab) getChild(i)).getScreen());
    }
}
