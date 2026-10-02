package dev.creoii.bulletforge.render.screen.element.editor.config;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.reflect.Field;
import dev.creoii.bulletforge.GlobalAssets;
import dev.creoii.bulletforge.render.screen.element.editor.Vector2Field;
import dev.creoii.bulletforge.util.provider.BulletForgeDataTypes;
import dev.creoii.providerlib.api.value.datatype.DataType;

public record Vector2FieldDynamicFieldConfig(Object owner, Field field) implements DynamicFieldConfig<Vector2> {
    @Override
    public DataType<Vector2> dataType() {
        return BulletForgeDataTypes.VEC2;
    }

    @Override
    public Actor create(Vector2 vector2) {
        Vector2Field vector2Field = new Vector2Field(GlobalAssets.SKIN);
        vector2Field.addXFieldListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    String text = vector2Field.getXField().getText();
                    if (text.isBlank()) text = "0";
                    if (text.endsWith(".")) text = text.substring(0, text.length() - 1);
                    if (text.equals("-")) text = "0";

                    vector2.x = Float.parseFloat(text);
                } catch (NumberFormatException ignored) {}
            }
        });
        vector2Field.addYFieldListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    String text = vector2Field.getYField().getText();
                    if (text.isBlank()) text = "0";
                    if (text.endsWith(".")) text = text.substring(0, text.length() - 1);
                    if (text.equals("-")) text = "0";

                    vector2.y = Float.parseFloat(text);
                } catch (NumberFormatException ignored) {}
            }
        });
        return vector2Field;
    }
}
