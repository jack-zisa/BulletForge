package dev.creoii.bulletforge.render.screen.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import dev.creoii.bulletforge.BulletForge;
import dev.creoii.bulletforge.GlobalAssets;

public class FileButton extends TextButton {
    private final OptionTooltip tooltip;

    public FileButton(BulletForge main) {
        super(main.getI18n().get("window.controlBar.file"), GlobalAssets.SKIN);
