package com.javabd.aula_conexao.controller;

import com.javabd.aula_conexao.model.Usuario;
import com.javabd.aula_conexao.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class ListaUsuariosController {

    @FXML
    private TableView<Usuario> tableUsuarios;

    @FXML
    private TableColumn<Usuario, Long> colId;

    @FXML
    private TableColumn<Usuario, String> colNome;

    @FXML
    private TableColumn<Usuario, Integer> colPontuacao;

    @FXML
    private TableColumn<Usuario, Integer> colInimigos;

    @FXML
    private TextField tfNome;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private TextField tfPontuacao;

    @FXML
    private TextField tfInimigos;

    private final UsuarioService service = new UsuarioService();

    private Usuario usuarioSelecionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getId()).asObject());
        colNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        colPontuacao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPontuacao()).asObject());
        colInimigos.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getInimigosDerrotados()).asObject());

        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) preencherFormulario(newSelection);
        });

        carregarUsuarios();
    }

    private void preencherFormulario(Usuario usuario) {
        usuarioSelecionado = usuario;
        tfNome.setText(usuario.getNome());
        tfSenha.setText(usuario.getSenha());
        tfPontuacao.setText(String.valueOf(usuario.getPontuacao()));
        tfInimigos.setText(String.valueOf(usuario.getInimigosDerrotados()));
    }

    private void limparFormulario() {
        tfNome.clear();
        tfSenha.clear();
        tfPontuacao.clear();
        tfInimigos.clear();
        usuarioSelecionado = null;
    }

    private void carregarUsuarios() {
        try {
            List<Usuario> usuarios = service.listarTodos();
            ObservableList<Usuario> lista = FXCollections.observableArrayList(usuarios);
            tableUsuarios.setItems(lista);
        } catch (IOException e) {
            mostrarAlerta("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    @FXML
    public void onSalvar() {
        String nome = tfNome.getText();
        String senha = tfSenha.getText();
        int pontuacao = Integer.parseInt(tfPontuacao.getText());
        int inimigos = Integer.parseInt(tfInimigos.getText());

        Usuario usuario = new Usuario(null, nome, senha, pontuacao, inimigos);

        try {
            if (usuarioSelecionado == null) {
                service.cadastrar(usuario);
                mostrarAlerta("Usuário cadastrado com sucesso!");
            } else {
                service.atualizar(usuarioSelecionado.getId(), usuario);
                mostrarAlerta("Usuário atualizado com sucesso!");
            }
            carregarUsuarios();
            limparFormulario();
        } catch (IOException e) {
            mostrarAlerta("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    public void onExcluir() {
        if (usuarioSelecionado == null) {
            mostrarAlerta("Selecione um usuário para excluir.");
            return;
        }

        try {
            service.deletar(usuarioSelecionado.getId());
            mostrarAlerta("Usuário excluído com sucesso!");
            carregarUsuarios();
            limparFormulario();
        } catch (IOException e) {
            mostrarAlerta("Erro ao excluir: " + e.getMessage());
        }
    }

    @FXML
    public void onLimpar() {
        limparFormulario();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
