module com.javabd.aula_conexao {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires com.fasterxml.jackson.databind;

    exports com.javabd.aula_conexao;
    exports com.javabd.aula_conexao.controller;
    exports com.javabd.aula_conexao.model;

    opens com.javabd.aula_conexao to javafx.fxml;
    opens com.javabd.aula_conexao.controller to javafx.fxml;
    opens com.javabd.aula_conexao.model to com.fasterxml.jackson.databind;
}
