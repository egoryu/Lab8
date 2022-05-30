import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AppController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button outButton;

    @FXML
    private TableView<Table> mainTable;

    @FXML
    private TableColumn<Table, String > Key;

    @FXML
    private TableColumn<Table, Integer> id;

    @FXML
    private TableColumn<Table, String> Name;

    @FXML
    private TableColumn<Table, Float> X;

    @FXML
    private TableColumn<Table, Integer> Y;

    @FXML
    private TableColumn<Table, ZonedDateTime> creationDate;

    @FXML
    private TableColumn<Table, Integer> minimalPoint;

    @FXML
    private TableColumn<Table, String> description;

    @FXML
    private TableColumn<Table, Difficulty> difficulty;

    @FXML
    private TableColumn<Table, String> author_name;

    @FXML
    private TableColumn<Table, ZonedDateTime> birthday;

    @FXML
    private TableColumn<Table, Integer> height;

    @FXML
    private TableColumn<Table, Integer> weight;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem ChangeMode;

    @FXML
    private MenuItem insetItem;

    @FXML
    private TextField keyFilter;

    @FXML
    private TextField idFilter;

    @FXML
    private TextField nameFilter;

    @FXML
    private TextField personNameFilter;

    @FXML
    private TextField heightFilter;

    @FXML
    private TextField xFilter;

    @FXML
    private TextField yFilter;

    @FXML
    private TextField minimalPointFilter;

    @FXML
    private TextField descriptionFilter;

    @FXML
    private TextField weightFilter;

    @FXML
    private ChoiceBox<String> difficultyFilter;

    @FXML
    private ChoiceBox<String> creationDateFilter;

    @FXML
    private ChoiceBox<String> birthdayFilter;

    @FXML
    private Button filterButton;

    private ObservableList<Table> masterData = FXCollections.observableArrayList();
    private ObservableList<Table> filteredData = FXCollections.observableArrayList();
    private ObservableList<String> difficultyData = FXCollections.observableArrayList();
    private ObservableList<String> creationDateData = FXCollections.observableArrayList();
    private ObservableList<String> birthdayData = FXCollections.observableArrayList();

    public AppController() throws IOException, ClassNotFoundException {
        // Add some sample data to the master data
        Request request = Transfer.getTransfer().Start("get", null);
        if (request.getCollection() != null) {
            request.getCollection().forEach((key, value) -> masterData.add(new Table(key, value.getId(),
                    value.getName(), value.getCoordinates().getX(), value.getCoordinates().getY(),
                    value.getCreationDate(), value.getMinimalPoint(), value.getDescription(),
                    value.getDifficulty(), value.getAuthor().getName(), value.getAuthor().getBirthday(),
                    value.getAuthor().getHeight(), value.getAuthor().getWeight())));
        }

        // Initially add all data to filtered data
        filteredData.addAll(masterData);

        Arrays.stream(Difficulty.values()).forEach(e -> difficultyData.add(e.toString()));
        difficultyData.add("DIFFICULTY");

        // Listen for changes in master data.
        // Whenever the master data changes we must also update the filtered data.
        masterData.addListener(new ListChangeListener<Table>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Table> change) {
                updateFilteredData();
            }
        });
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        outButton.setOnAction(event -> {
            CommonAnimation.changeScene(new File("Auth.fxml"), true);
        });

        ChangeMode.setOnAction(event -> {
            CommonAnimation.changeScene(new File("Anim.fxml"), false);
        });

        insetItem.setOnAction(event -> {
            CommonAnimation.changeSubScene(new File("Insert.fxml"), true);
        });

        filterButton.setOnAction(event -> {
            updateFilteredData();
        });

        mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2) {
                        CommonAnimation.element = mainTable.getSelectionModel().getSelectedItem();
                        CommonAnimation.changeSubScene(new File("Update.fxml"), false);
                    }
                }
            }
        });
        /*
        TableView.TableViewSelectionModel<Table> selectionModel = mainTable.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Table>() {
            @Override
            public void changed(ObservableValue<? extends Table> observable, Table oldValue, Table newValue) {
            }
        });*/


        Key.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        Name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        X.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        Y.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());
        creationDate.setCellValueFactory(cellData -> cellData.getValue().creationDateProperty());
        minimalPoint.setCellValueFactory(cellData -> cellData.getValue().minimalPointProperty().asObject());
        description.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        difficulty.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
        author_name.setCellValueFactory(cellData -> cellData.getValue().personNameProperty());
        birthday.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
        height.setCellValueFactory(cellData -> cellData.getValue().heightProperty().asObject());
        weight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());

        mainTable.setItems(filteredData);

        difficultyFilter.setItems(difficultyData);
        difficultyFilter.setValue("DIFFICULTY");

        creationDateFilter.setItems(creationDateData);

        birthdayFilter.setItems(birthdayData);

        updateTimeFilter();

        setFloatFilter(xFilter);
        setIntegerFilter(idFilter);
        setIntegerFilter(weightFilter);
        setIntegerFilter(heightFilter);
        setIntegerFilter(yFilter);
        setIntegerFilter(minimalPointFilter);
    }

    public static void setFloatFilter(TextField filter) {
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && (!Useful.isFloat(newValue) || Float.parseFloat(newValue) < 0 ||
                    (newValue.length() > 1 && newValue.indexOf('.') != 1 && newValue.indexOf("00") == 0) ||
                    newValue.contains("d") || newValue.contains("f") || newValue.contains(" "))) {
                if (oldValue.isEmpty())
                    filter.clear();
                else
                    filter.setText(oldValue);
            }
        });
    }

    public static void setIntegerFilter(TextField filter) {
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && (!Useful.isInteger(newValue) || Integer.parseInt(newValue) < 0 ||
                    newValue.indexOf("00") == 0) ||
                    newValue.contains("d") || newValue.contains("f") || newValue.contains(" ")) {
                if (oldValue.isEmpty())
                    filter.clear();
                else
                    filter.setText(oldValue);
            }
        });
    }

    public static void setDateFilter(TextField filter) {
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && (newValue.length() >= 10 && !isDate(newValue))) {
                if (oldValue.isEmpty())
                    filter.clear();
                else
                    filter.setText(oldValue);
            }
        });
    }

    public static boolean isDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void updateFilteredData() {
        filteredData.clear();

        masterData.stream().filter(e -> keyFilter.getText().isEmpty() || e.getKey().contains(keyFilter.getText())).
                filter(e -> idFilter.getText().isEmpty() || Integer.toString(e.getId()).contains(idFilter.getText())).
                filter(e -> nameFilter.getText().isEmpty() || e.getName().contains(nameFilter.getText())).
                filter(e -> personNameFilter.getText().isEmpty() || e.getPersonName().contains(personNameFilter.getText())).
                filter(e -> heightFilter.getText().isEmpty() || Integer.toString(e.getHeight()).contains(heightFilter.getText())).
                filter(e -> xFilter.getText().isEmpty() || Float.toString(e.getX()).contains(xFilter.getText())).
                filter(e -> yFilter.getText().isEmpty() || Integer.toString(e.getY()).contains(yFilter.getText())).
                filter(e -> minimalPointFilter.getText().isEmpty() || Integer.toString(e.getMinimalPoint()).contains(minimalPointFilter.getText())).
                filter(e -> descriptionFilter.getText().isEmpty() || e.getDescription().contains(descriptionFilter.getText())).
                filter(e -> weightFilter.getText().isEmpty() || Integer.toString(e.getWeight()).contains(weightFilter.getText())).
                filter(e -> difficultyFilter.getValue().equals("DIFFICULTY") || e.getDifficulty().toString().equals(difficultyFilter.getValue())).
                filter(e -> creationDateFilter.getValue().equals("CreationTime") || e.getCreationDate().toLocalDate().toString().equals(creationDateFilter.getValue())).
                filter(e -> birthdayFilter.getValue().equals("BirthDay") || (e.getBirthday() != null ? e.getBirthday().toLocalDate().toString().equals(birthdayFilter.getValue()) : birthdayFilter.getValue().equals(""))).
                forEach(e -> filteredData.add(e));

        // Must re-sort table after items changed
        reapplyTableSortOrder();
        updateTimeFilter();
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Table, ?>> sortOrder = new ArrayList(mainTable.getSortOrder());
        mainTable.getSortOrder().clear();
        mainTable.getSortOrder().addAll(sortOrder);
    }

    private void updateTimeFilter() {
        creationDateData.clear();
        List<String> allDate = new ArrayList<>();
        for (Table e : filteredData) {
            allDate.add(e.getCreationDate().toLocalDate().toString());
        }
        allDate = allDate.stream().distinct().collect(Collectors.toList());
        creationDateData.addAll(allDate);
        creationDateData.add("CreationTime");
        creationDateFilter.setValue("CreationTime");

        birthdayData.clear();
        List<String> allDate1 = new ArrayList<>();
        for (Table e : filteredData) {
            allDate1.add(e.getBirthday() != null ? e.getBirthday().toLocalDate().toString() : "");
        }
        allDate1 = allDate1.stream().distinct().collect(Collectors.toList());
        birthdayData.addAll(allDate1);
        birthdayData.add("BirthDay");
        birthdayFilter.setValue("BirthDay");
    }
}