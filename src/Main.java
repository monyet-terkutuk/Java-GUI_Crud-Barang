import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends Applet implements ActionListener {
    TextField nama, pass;

    public void init() {
        Label namap = new Label("Nama : ", Label.RIGHT);
        Label passp = new Label("Password : ", Label.RIGHT);

        nama = new TextField(12);
        pass = new TextField(8);
        pass.setEchoChar('?');

        add(namap);
        add(nama);
        add(passp);
        add(pass);

        nama.addActionListener(this);
        pass.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        // Logika aksi yang akan dilakukan ketika tombol Enter ditekan
        if (e.getSource() == nama) {
            String inputNama = nama.getText();
            // Lakukan sesuatu dengan inputNama
        } else if (e.getSource() == pass) {
            String inputPass = pass.getText();
            // Lakukan sesuatu dengan inputPass
        }
    }
}
