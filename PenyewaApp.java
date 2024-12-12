package bendi_car;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

public class PenyewaApp extends Application {
    private final ObservableList<Penyewa> penyewaList = FXCollections.observableArrayList();
    private TableView<Penyewa> tableView;
    private VBox welcomePage;
    private Scene welcomeScene;

    @Override
    public void start(Stage primaryStage) {
        // Welcome Page
        welcomePage = new VBox(20);
        welcomePage.setPadding(new Insets(50, 20, 50, 20));
        welcomePage.setStyle("-fx-background-color: #f4f4f4;");
        welcomePage.setAlignment(Pos.CENTER); // Centering content vertically and horizontally

        Label welcomeLabel = new Label("WELCOME TO PT.BENDI CAR");
        welcomeLabel.setFont(new Font("Impact", 56));
        welcomeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Button insertButton = new Button("Insert Penyewa");
        Button updateButton = new Button("Update Penyewa");
        Button deleteButton = new Button("Delete Penyewa");
        Button viewButton = new Button("View Penyewa");

        // Styling buttons
        insertButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10;");
        updateButton.setStyle("-fx-background-color: #d69511; -fx-text-fill: white; -fx-padding: 10;");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10;");
        viewButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10;");

        // Adding actions to buttons
        insertButton.setOnAction(e -> showInsertPage(primaryStage));
        updateButton.setOnAction(e -> showUpdatePage(primaryStage));
        deleteButton.setOnAction(e -> showDeletePage(primaryStage));
        viewButton.setOnAction(e -> showViewPage(primaryStage));

        // Creating a horizontal HBox for the buttons
        HBox buttonBox = new HBox(10); // Horizontal spacing of 10
        buttonBox.setAlignment(Pos.CENTER); // Center buttons horizontally
        buttonBox.getChildren().addAll(insertButton, updateButton, deleteButton, viewButton);

        // Add label and button box to the VBox
        welcomePage.getChildren().addAll(welcomeLabel, buttonBox);

        // Scene and Stage
        welcomeScene = new Scene(welcomePage, 800, 600);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("PT Bendi Car_Gheytsha");
        primaryStage.show();
    }

    private void showInsertPage(Stage primaryStage) {
        VBox insertPage = new VBox(20);
        insertPage.setPadding(new Insets(20));
        insertPage.setStyle("-fx-background-color: #f4f4f4;");
        insertPage.setAlignment(Pos.CENTER);

        TextField noKtpField = new TextField();
        noKtpField.setPromptText("No KTP");
        TextField alamatField = new TextField();
        alamatField.setPromptText("Alamat");
        TextField namaField = new TextField();
        namaField.setPromptText("Nama");
        TextField noTelpField = new TextField();
        noTelpField.setPromptText("No Telepon");

        Button addButton = new Button("Tambah Penyewa");
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10;");
        addButton.setOnAction(e -> addPenyewa(noKtpField, alamatField, namaField, noTelpField));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10;");
        backButton.setOnAction(e -> primaryStage.setScene(welcomeScene));

        insertPage.getChildren().addAll(noKtpField, alamatField, namaField, noTelpField, addButton, backButton);

        primaryStage.setScene(new Scene(insertPage, 800, 600));
    }

    private void showUpdatePage(Stage primaryStage) {
        VBox updatePage = new VBox(20);
        updatePage.setPadding(new Insets(20));
        updatePage.setStyle("-fx-background-color: #f4f4f4;");
        updatePage.setAlignment(Pos.CENTER);

        tableView = new TableView<>();
        tableView.setItems(penyewaList);
        tableView.getColumns().addAll(createColumns());

        TextField noKtpField = new TextField();
        noKtpField.setPromptText("No KTP");
        TextField alamatField = new TextField();
        alamatField.setPromptText("Alamat");
        TextField namaField = new TextField();
        namaField.setPromptText("Nama");
        TextField noTelpField = new TextField();
        noTelpField.setPromptText("No Telepon");

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                noKtpField.setText(newSelection.getNoKtp());
                alamatField.setText(newSelection.getAlamat());
                namaField.setText(newSelection.getNama());
                noTelpField.setText(newSelection.getNoTelp());
            }
        });

        Button updateButton = new Button("Update Penyewa");
        updateButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10;");
        updateButton.setOnAction(e -> updatePenyewa(noKtpField, alamatField, namaField, noTelpField));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10;");
        backButton.setOnAction(e -> primaryStage.setScene(welcomeScene));

        updatePage.getChildren().addAll(tableView, noKtpField, alamatField, namaField, noTelpField, updateButton, backButton);

        primaryStage.setScene(new Scene(updatePage, 800, 600));
        loadPenyewaData();
    }

    private void showDeletePage(Stage primaryStage) {
        VBox deletePage = new VBox(20);
        deletePage.setPadding(new Insets(20));
        deletePage.setStyle("-fx-background-color: #f4f4f4;");
        deletePage.setAlignment(Pos.CENTER);

        TextField noKtpField = new TextField();
        noKtpField.setPromptText("No KTP");

        Button deleteButton = new Button("Delete Penyewa");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10;");
        deleteButton.setOnAction(e -> deletePenyewa(noKtpField));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10;");
        backButton.setOnAction(e -> primaryStage.setScene(welcomeScene));

        deletePage.getChildren().addAll(noKtpField, deleteButton, backButton);

        primaryStage.setScene(new Scene(deletePage, 800, 600));
    }

    private void showViewPage(Stage primaryStage) {
        VBox viewPage = new VBox(20);
        viewPage.setPadding(new Insets(20));
        viewPage.setStyle("-fx-background-color: #f4f4f4;");
        viewPage.setAlignment(Pos.CENTER);

        tableView = new TableView<>();
        tableView.setItems(penyewaList);
        tableView.getColumns().addAll(createColumns());

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10;");
        backButton.setOnAction(e -> primaryStage.setScene(welcomeScene));

        viewPage.getChildren().addAll(tableView, backButton);

        primaryStage.setScene(new Scene(viewPage, 800, 600));
        loadPenyewaData();
    }

    private TableColumn<Penyewa, ?>[] createColumns() {
        TableColumn<Penyewa, String> noKtpColumn = new TableColumn<>("No KTP");
        noKtpColumn.setCellValueFactory(cell -> cell.getValue().noKtpProperty());

        TableColumn<Penyewa, String> alamatColumn = new TableColumn<>("Alamat");
        alamatColumn.setCellValueFactory(cell -> cell.getValue().alamatProperty());

        TableColumn<Penyewa, String> namaColumn = new TableColumn<>("Nama");
        namaColumn.setCellValueFactory(cell -> cell.getValue().namaProperty());

        TableColumn<Penyewa, String> noTelpColumn = new TableColumn<>("No Telepon");
        noTelpColumn.setCellValueFactory(cell -> cell.getValue().noTelpProperty());

        return new TableColumn[]{noKtpColumn, alamatColumn, namaColumn, noTelpColumn};
    }

    private void loadPenyewaData() {
        penyewaList.clear();
        try (Connection conn = connectToDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM penyewa")) {
            while (rs.next()) {
                Penyewa penyewa = new Penyewa(
                        rs.getString("no_ktp"),
                        rs.getString("alamat"),
                        rs.getString("nama"),
                        rs.getString("no_telp")
                );
                penyewaList.add(penyewa);
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to load data: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addPenyewa(TextField noKtpField, TextField alamatField, TextField namaField, TextField noTelpField) {
        String noKtp = noKtpField.getText().trim();
        String alamat = alamatField.getText().trim();
        String nama = namaField.getText().trim();
        String noTelp = noTelpField.getText().trim();

        if (noKtp.isEmpty() || alamat.isEmpty() || nama.isEmpty() || noTelp.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO penyewa (no_ktp, alamat, nama, no_telp) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, noKtp);
            pstmt.setString(2, alamat);
            pstmt.setString(3, nama);
            pstmt.setString(4, noTelp);
            pstmt.executeUpdate();
            showAlert("Success", "Penyewa added successfully.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error", "Failed to add Penyewa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void deletePenyewa(TextField noKtpField) {
        String noKtp = noKtpField.getText().trim();

        if (noKtp.isEmpty()) {
            showAlert("Error", "No KTP must be provided.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM penyewa WHERE no_ktp = ?")) {
            pstmt.setString(1, noKtp);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Penyewa deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Info", "No Penyewa found with the given No KTP.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete Penyewa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updatePenyewa(TextField noKtpField, TextField alamatField, TextField namaField, TextField noTelpField) {
        String noKtp = noKtpField.getText().trim();
        String alamat = alamatField.getText().trim();
        String nama = namaField.getText().trim();
        String noTelp = noTelpField.getText().trim();

        if (noKtp.isEmpty() || alamat.isEmpty() || nama.isEmpty() || noTelp.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE penyewa SET alamat = ?, nama = ?, no_telp = ? WHERE no_ktp = ?")) {
            pstmt.setString(1, alamat);
            pstmt.setString(2, nama);
            pstmt.setString(3, noTelp);
            pstmt.setString(4, noKtp);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Penyewa updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Info", "No Penyewa found with the given No KTP.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to update Penyewa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/bendi_car";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}