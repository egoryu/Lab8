import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class Table {
    private StringProperty key;
    private IntegerProperty id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private StringProperty name; //Поле не может быть null, Строка не может быть пустой
    private FloatProperty x; //Поле не может быть null
    private IntegerProperty y; //Поле не может быть null
    private ObjectProperty<ZonedDateTime> creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private IntegerProperty minimalPoint; //Поле может быть null, Значение поля должно быть больше 0
    private StringProperty description; //Длина строки не должна быть больше 5207, Поле не может быть null
    private ObjectProperty<Difficulty> difficulty; //Поле не может быть null
    private StringProperty personName; //Поле не может быть null, Строка не может быть пустой
    private ObjectProperty<ZonedDateTime> birthday; //Поле может быть null
    private IntegerProperty height; //Поле не может быть null, Значение поля должно быть больше 0
    private IntegerProperty weight; //Значение поля должно быть больше 0

    public Table(String key, int id, String name, Float x, Integer y, ZonedDateTime creationDate, Integer minimalPoint, String description, Difficulty difficulty, String personName, ZonedDateTime birthday, Integer height, long weight) {
        this.key = new SimpleStringProperty(key);
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.x = new SimpleFloatProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
        this.minimalPoint = new SimpleIntegerProperty(minimalPoint);
        this.description = new SimpleStringProperty(description);
        this.difficulty = new SimpleObjectProperty<>(difficulty);
        this.personName = new SimpleStringProperty(personName);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.height = new SimpleIntegerProperty(height);
        this.weight = new SimpleIntegerProperty((int) weight);
    }

    public String getKey() {
        return key.get();
    }

    public StringProperty keyProperty() {
        return key;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public float getX() {
        return x.get();
    }

    public FloatProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate.get();
    }

    public ObjectProperty<ZonedDateTime> creationDateProperty() {
        return creationDate;
    }

    public int getMinimalPoint() {
        return minimalPoint.get();
    }

    public IntegerProperty minimalPointProperty() {
        return minimalPoint;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Difficulty getDifficulty() {
        return difficulty.get();
    }

    public ObjectProperty<Difficulty> difficultyProperty() {
        return difficulty;
    }

    public String getPersonName() {
        return personName.get();
    }

    public StringProperty personNameProperty() {
        return personName;
    }

    public ZonedDateTime getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<ZonedDateTime> birthdayProperty() {
        return birthday;
    }

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }
}
