package com.javabd.aula_conexao.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javabd.aula_conexao.model.Usuario;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class UsuarioService {
    private static final String BASE_URL = "https://jogo2025.onrender.com/usuarios";
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<Usuario> listarTodos() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (InputStream in = conn.getInputStream()) {
            return Arrays.asList(mapper.readValue(in, Usuario[].class));
        }
    }

    public Usuario cadastrar(Usuario usuario) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            mapper.writeValue(os, usuario);
        }

        try (InputStream in = conn.getInputStream()) {
            return mapper.readValue(in, Usuario.class);
        }
    }

    public Usuario atualizar(Long id, Usuario usuario) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            mapper.writeValue(os, usuario);
        }

        try (InputStream in = conn.getInputStream()) {
            return mapper.readValue(in, Usuario.class);
        }
    }

    public void deletar(Long id) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.getInputStream().close();
    }

    public Usuario login(String email, String senha) throws IOException {
        URL url = new URL(BASE_URL + "/login?email=" + email + "&senha=" + senha);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (InputStream in = conn.getInputStream()) {
            return mapper.readValue(in, Usuario.class);
        }
    }

    public List<Usuario> top10() throws IOException {
        URL url = new URL(BASE_URL + "/ranking");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (InputStream in = conn.getInputStream()) {
            return Arrays.asList(mapper.readValue(in, Usuario[].class));
        }
    }
}
