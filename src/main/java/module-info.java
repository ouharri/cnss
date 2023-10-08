import com.macnss.Libs.dbutils.ColumnHandler;
import com.macnss.Libs.dbutils.PropertyHandler;

module com.biblio {
    uses ColumnHandler;
    uses PropertyHandler;
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
    requires org.postgresql.jdbc;
    requires java.desktop;
    requires jbcrypt;
    requires cloudinary.http44;
    requires cloudinary.core;
    requires jcalendar;
    requires jakarta.mail;
    requires java.compiler;

    opens com.macnss to javafx.fxml;
    exports com.macnss;
    exports com.macnss.app.Models;
    exports com.macnss.app.Enums;
    exports  com.macnss.app.Models.user;
    exports com.macnss.app.Models.Abstract;
    exports com.macnss.Libs.orm;
}