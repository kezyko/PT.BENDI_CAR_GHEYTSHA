package bendi_car;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Penyewa {
    private final StringProperty noKtp;
    private final StringProperty alamat;
    private final StringProperty nama;
    private final StringProperty noTelp;

    public Penyewa(String noKtp, String alamat, String nama, String noTelp) {
        this.noKtp = new SimpleStringProperty(noKtp);
        this.alamat = new SimpleStringProperty(alamat);
        this.nama = new SimpleStringProperty(nama);
        this.noTelp = new SimpleStringProperty(noTelp);
    }

    public String getNoKtp() {
        return noKtp.get();
    }

    public StringProperty noKtpProperty() {
        return noKtp;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getNama() {
        return nama.get();
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getNoTelp() {
        return noTelp.get();
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }
}