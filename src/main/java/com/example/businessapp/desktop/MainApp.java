package com.example.businessapp.desktop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ListView<Item> listView = new ListView<>(items);
        listView.setPrefWidth(220);

        TextField nameField = new TextField();
        TextField descField = new TextField();

        Button addBtn = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        HBox buttons = new HBox(8, addBtn, updateBtn, deleteBtn);

        // Actions
        Runnable doNew = () -> {
            items.clear();
            nameField.clear();
            descField.clear();
        };

        Runnable doAdd = () -> {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            if (name.isEmpty()) { showAlert("Name is required"); return; }
            items.add(new Item(name, desc));
            nameField.clear(); descField.clear();
        };

        Runnable doUpdate = () -> {
            Item sel = listView.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Select an item to update"); return; }
            sel.setName(nameField.getText().trim());
            sel.setDescription(descField.getText().trim());
            listView.refresh();
        };

        Runnable doDelete = () -> {
            Item sel = listView.getSelectionModel().getSelectedItem();
            if (sel != null) items.remove(sel);
        };

        addBtn.setOnAction(e -> doAdd.run());
        updateBtn.setOnAction(e -> doUpdate.run());
        deleteBtn.setOnAction(e -> doDelete.run());

        listView.getSelectionModel().selectedItemProperty().addListener((obs, old, nw) -> {
            if (nw != null) {
                nameField.setText(nw.getName());
                descField.setText(nw.getDescription());
            } else {
                nameField.clear(); descField.clear();
            }
        });

        // Menu bar
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem exitItem = new MenuItem("Exit");
        newItem.setAccelerator(KeyCombination.keyCombination("Shortcut+N"));
        exitItem.setAccelerator(KeyCombination.keyCombination("Shortcut+Q"));
        newItem.setOnAction(e -> doNew.run());
        exitItem.setOnAction(e -> javafx.application.Platform.exit());
        fileMenu.getItems().addAll(newItem, new SeparatorMenuItem(), exitItem);

        Menu editMenu = new Menu("Edit");
        MenuItem addItemMenu = new MenuItem("Add");
        MenuItem updateItemMenu = new MenuItem("Update");
        MenuItem deleteItemMenu = new MenuItem("Delete");
        addItemMenu.setAccelerator(KeyCombination.keyCombination("Shortcut+Shift+N"));
        updateItemMenu.setAccelerator(KeyCombination.keyCombination("Shortcut+U"));
        deleteItemMenu.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        addItemMenu.setOnAction(e -> doAdd.run());
        updateItemMenu.setOnAction(e -> doUpdate.run());
        deleteItemMenu.setOnAction(e -> doDelete.run());
        editMenu.getItems().addAll(addItemMenu, updateItemMenu, deleteItemMenu);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setAccelerator(KeyCombination.keyCombination("F1"));
        aboutItem.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "BusinessApp Desktop\nSimple demo application", ButtonType.OK);
            a.setHeaderText("About");
            a.showAndWait();
        });
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        VBox leftWithMenu = new VBox(menuBar, listView);

        VBox form = new VBox(8,
                new Label("Name"), nameField,
                new Label("Description"), descField,
                buttons
        );
        form.setPadding(new Insets(10));
        form.setPrefWidth(320);

        BorderPane root = new BorderPane();
        root.setLeft(leftWithMenu);
        root.setCenter(form);

        Scene scene = new Scene(root, 640, 360);
        stage.setTitle("BusinessApp Desktop - Simple CRUD");
        stage.setScene(scene);

        // Optional: allow keyboard shortcuts that operate when focus is in list or fields
        scene.getAccelerators().put(KeyCombination.keyCombination("Shortcut+N"), () -> doNew.run());
        scene.getAccelerators().put(KeyCombination.keyCombination("Shortcut+Shift+N"), () -> doAdd.run());
        scene.getAccelerators().put(KeyCombination.keyCombination("Shortcut+U"), () -> doUpdate.run());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DELETE), () -> doDelete.run());
        scene.getAccelerators().put(KeyCombination.keyCombination("F1"), () -> aboutItem.fire());

        stage.show();
    }

    private void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        a.showAndWait();
    }
}
