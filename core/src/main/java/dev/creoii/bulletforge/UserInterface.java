package dev.creoii.bulletforge;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.creoii.bulletforge.render.screen.element.ResetPositionButton;
import dev.creoii.bulletforge.util.manager.TabManager;
import dev.creoii.bulletforge.render.screen.AbstractScreen;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.render.screen.HomeScreen;
import dev.creoii.bulletforge.render.screen.element.AutofireButton;
import dev.creoii.bulletforge.render.screen.element.TargetMouseButton;
import dev.creoii.bulletforge.render.screen.element.tooltip.TooltipProvider;
import dev.creoii.bulletforge.render.screen.element.Tab;
import dev.creoii.bulletforge.render.window.WindowControlBar;

import java.util.ArrayList;
import java.util.List;

public class UserInterface {
    public static final float CONTROL_BAR_HEIGHT = 32f;
    public static final float TAB_BAR_HEIGHT = 40f;
    private final BulletForge main;
    private TabManager tabManager;
    private WindowControlBar windowControlBar;
    private Container<Table> screenContainer;
    private AutofireButton autofireButton;
    private TargetMouseButton targetMouseButton;
    private ResetPositionButton resetPositionButton;
    private AbstractScreen activeScreen;

    public UserInterface(BulletForge main) {
        this.main = main;
    }

    public TabManager getTabManager() {
        return tabManager;
    }

    public WindowControlBar getWindowControlBar() {
        return windowControlBar;
    }

    public Container<Table> getScreenContainer() {
        return screenContainer;
    }

    public void render(float dt) {
        if (activeScreen != null) {
            activeScreen.render(dt);
        }
    }

    public void setActiveScreen(AbstractScreen screen) {
        if (activeScreen != null) {
            activeScreen.hide();
        }

        activeScreen = screen;

        if (activeScreen != null) {
            screenContainer.setActor(activeScreen.getRoot());
            screenContainer.fill();
            activeScreen.show();

            autofireButton.setVisible(activeScreen instanceof EditorScreen);
            targetMouseButton.setVisible(activeScreen instanceof EditorScreen);
            resetPositionButton.setVisible(activeScreen instanceof EditorScreen);
        }
    }

    public AbstractScreen getActiveScreen() {
        return activeScreen;
    }

    protected void createGlobalActors() {
        Table root = new Table();
        root.setFillParent(true);

        root.add(windowControlBar = new WindowControlBar(main)).height(CONTROL_BAR_HEIGHT).growX().top().row();
        root.add(tabManager = new TabManager(main)).height(TAB_BAR_HEIGHT).growX().top().row();

        Table toolsTable = new Table();
        toolsTable.add(autofireButton = new AutofireButton(main)).left();
        toolsTable.add(targetMouseButton = new TargetMouseButton(main)).left();
        toolsTable.add(resetPositionButton = new ResetPositionButton(main)).left();
        root.add(toolsTable).left().row();
        root.add(screenContainer = new Container<>()).grow().fill().row();

        tabManager.addTab(-1, Tab.createHome(main, new HomeScreen(main)));

        main.getGlobalStage().addActor(root);

        collectTooltips(root, new ArrayList<>()).forEach(main.getGlobalStage()::addActor);
    }

    private static List<Actor> collectTooltips(Group parent, List<Actor> list) {
        parent.getChildren().forEach(actor -> {
            if (actor instanceof TooltipProvider tooltipProvider) {
                list.add(tooltipProvider.getTooltip());
            } else if (actor instanceof Group group) collectTooltips(group, list);
        });
        return list;
    }
}
