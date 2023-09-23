module com.biblio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires lombok;
    requires java.sql;
//    requires jBCrypt;
    requires org.postgresql.jdbc;
    requires java.desktop;
    requires jakarta.mail;
    requires jbcrypt;

    opens com.macnss;
    exports com.macnss;
}