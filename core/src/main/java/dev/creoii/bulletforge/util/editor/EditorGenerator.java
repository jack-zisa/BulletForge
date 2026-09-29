package dev.creoii.bulletforge.util.editor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.creoii.bulletforge.object.definition.AttackDefinition;
import dev.creoii.bulletforge.object.definition.BulletDefinition;
import dev.creoii.bulletforge.object.definition.BulletDictionaryDefinition;
import dev.creoii.bulletforge.render.screen.EditorScreen;
import dev.creoii.bulletforge.render.screen.element.editor.ExpandableEditorPane;

import java.util.List;

public final class EditorGenerator {
    public static Table createBulletDictionaryEditor(BulletDictionaryDefinition dictionary, Skin skin) {
        Table root = new Table();
        root.top().left();
        root.defaults().growX().left().pad(2f);

        Label title = new Label("Bullets", skin);
        root.add(title).row();

        Table entries = new Table();
        entries.top().left();
        entries.defaults().growX().left().pad(2f);

        rebuildBulletEntries(entries, dictionary, skin);

        TextButton addButton = new TextButton("Add Bullet", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dictionary.addBullet(BulletDefinition.DEFAULT.copy());
                rebuildBulletEntries(entries, dictionary, skin);
            }
        });

        root.add(entries).growX().top().left().row();
        root.add(addButton).left().row();

        return root;
    }

    private static void rebuildBulletEntries(Table entries, BulletDictionaryDefinition dictionary, Skin skin) {
        entries.clearChildren();

        dictionary.forEach((id, bullet) -> {
            ExpandableEditorPane pane =
                new ExpandableEditorPane(
                    "Bullet " + id,
                    bullet,
                    skin
                );

            TextButton removeButton = new TextButton("X", skin);
            removeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dictionary.removeBullet(id);
                    rebuildBulletEntries(entries, dictionary, skin);
                }
            });

            Table row = new Table();
            row.add(pane).growX().left();
            row.add(removeButton).width(30f);

            entries.add(row).growX().left().row();
        });
    }

    public static Table createAttackListEditor(EditorScreen screen, List<AttackDefinition> attacks, Skin skin) {
        Table root = new Table();
        root.top().left();
        root.defaults().growX().left().pad(2f);

        root.add(new Label("Attacks", skin)).row();

        Table entries = new Table();
        entries.top().left();
        entries.defaults().growX().left().pad(2f);

        rebuildAttackEntries(entries, attacks, skin);

        TextButton addButton = new TextButton("Add Attack", skin);
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AttackDefinition copy = AttackDefinition.DEFAULT.copy();
                attacks.add(copy);
                screen.getAttackManager().addAttack(copy);
                rebuildAttackEntries(entries, attacks, skin);
            }
        });

        root.add(entries).growX().top().left().row();
        root.add(addButton).left().row();

        return root;
    }

    private static void rebuildAttackEntries(Table entries, List<AttackDefinition> attacks, Skin skin) {
        entries.clearChildren();

        for (int i = 0; i < attacks.size(); i++) {
            int index = i;
            AttackDefinition attack = attacks.get(i);

            ExpandableEditorPane pane =
                new ExpandableEditorPane(
                    "Attack " + index,
                    attack,
                    skin
                );

            TextButton removeButton = new TextButton("X", skin);
            removeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    attacks.remove(index);
                    rebuildAttackEntries(entries, attacks, skin);
                }
            });

            Table row = new Table();
            row.add(pane).growX().left();
            row.add(removeButton).width(30f);

            entries.add(row).growX().left().row();
        }
    }

    public static Table createEditorTable(Object target, Skin skin) {
        Table rootTable = new Table();

        rootTable.top().right();
        rootTable.defaults().pad(1f);

        Field[] fields = ClassReflection.getDeclaredFields(target.getClass());

        for (Field field : fields) {
            if (!field.isAnnotationPresent(EditorSerializable.class)) continue;

            field.setAccessible(true);

            String name = field.getName();
            Class<?> type = field.getType();

            if (type.getFields().length > 0 && type != Vector2.class && type != String.class) {
                try {
                    rootTable.row();

                    Object value = field.get(target);
                    if (value != null) {
                        rootTable.add(new ExpandableEditorPane(name, value, skin)).growX().colspan(2).row();
                    } else rootTable.add();
                } catch (ReflectionException e) {
                    throw new RuntimeException("Error reading field: " + name, e);
                }
                continue;
            }

            rootTable.add(new Label(name + ":", skin)).left();

            if (type == int.class || type == float.class || type == double.class) {
                createNumberInput(rootTable, target, field, skin);
            } else if (type == boolean.class) {
                createBooleanInput(rootTable, target, field, skin);
            } else if (type == String.class) {
                createTextInput(rootTable, target, field, skin);
            } else if (type.isEnum()) {
                createEnumInput(rootTable, target, field, skin);
            } else if (type == Vector2.class) {
                createVector2Input(rootTable, target, field, skin);
            } else {
                rootTable.add();
            }

            rootTable.row();
        }
        return rootTable;
    }

    private static void createNumberInput(Table table, Object target, Field field, Skin skin) {
        try {
            String value = String.valueOf(field.get(target));
            TextField textField = new TextField(value, skin);

            textField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            textField.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        String text = textField.getText();
                        if (!text.isEmpty()) {
                            if (field.getType() == int.class) field.set(target, Integer.parseInt(text));
                            if (field.getType() == float.class) field.set(target, Float.parseFloat(text));
                        }
                    } catch (ReflectionException e) { e.printStackTrace(); }
                }
            });
            table.add(textField).width(120);
        } catch (ReflectionException e) {
            throw new RuntimeException("Error creating number input.");
        }
    }

    private static void createBooleanInput(Table table, Object target, Field field, Skin skin) {
        try {
            boolean value = (boolean) field.get(target);
            CheckBox checkBox = new CheckBox("", skin);
            checkBox.setChecked(value);
            checkBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        field.set(target, checkBox.isChecked());
                    } catch (ReflectionException e) { e.printStackTrace(); }
                }
            });
            table.add(checkBox);
        } catch (ReflectionException e) {
            throw new RuntimeException("Error creating boolean input.");
        }
    }

    private static void createTextInput(Table table, Object target, Field field, Skin skin) {
        try {
            String value = (String) field.get(target);
            TextField textField = new TextField(value != null ? value : "", skin);
            textField.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        field.set(target, textField.getText());
                    } catch (ReflectionException e) { e.printStackTrace(); }
                }
            });
            table.add(textField).width(200);
        } catch (ReflectionException e) {
            throw new RuntimeException("Error creating text input.");
        }
    }

    private static void createEnumInput(Table table, Object target, Field field, Skin skin) {
        try {
            Object currentValue = field.get(target);
            Object[] enumConstants = field.getType().getEnumConstants();

            SelectBox<Object> selectBox = new SelectBox<>(skin);
            selectBox.setItems(enumConstants);
            selectBox.setSelected(currentValue);
            selectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        field.set(target, selectBox.getSelected());
                    } catch (ReflectionException e) { e.printStackTrace(); }
                }
            });
            table.add(selectBox).width(150);
        } catch (ReflectionException e) {
            throw new RuntimeException("Error creating enum input.");
        }
    }

    private static void createVector2Input(Table table, Object target, Field field, Skin skin) {
        try {
            Vector2 vector = (Vector2) field.get(target);

            Table vectorTable = new Table();
            vectorTable.defaults().pad(1f);

            TextField xField = new TextField(String.valueOf(vector.x), skin);
            TextField yField = new TextField(String.valueOf(vector.y), skin);

            xField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            yField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());

            xField.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        vector.x = Float.parseFloat(xField.getText());
                    } catch (NumberFormatException ignored) {}
                }
            });

            yField.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        vector.y = Float.parseFloat(yField.getText());
                    } catch (NumberFormatException ignored) {}
                }
            });

            vectorTable.add(new Label("X", skin));
            vectorTable.add(xField).width(80f);
            vectorTable.add(new Label("Y", skin));
            vectorTable.add(yField).width(80f);

            table.add(vectorTable).growX();
        } catch (ReflectionException e) {
            throw new RuntimeException("Error creating Vector2 input.", e);
        }
    }
}
