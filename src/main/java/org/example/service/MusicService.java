package org.example.service;

import org.example.model.Music;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicService {
    private Connection connection;

    public MusicService(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    public Optional<List<Music>> findAll() {
        List<Music> musicList = new ArrayList<>();
        String sql = "select * from study.music";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                musicList.add(new Music(resultSet.getInt("id"), resultSet.getString("name")));
            }

            return Optional.of(musicList);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
    public Optional<Music> findById(int id) {
        try {
            String sql = "select * from study.music where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Music(resultSet.getInt("id"), resultSet.getString("name")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Optional<Music> findByName(String name) throws SQLException {
        String sql = "select * from study.music where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Music(resultSet.getInt("id"), resultSet.getString("name")));
        }

        return Optional.empty();
    }

    public Optional<List<Music>> find_compositions_without_symbols() {
        List<Music> musicList = new ArrayList<>();
        String sql = "select * from study.music where lower(name) not like '%t%' and lower(name) not like '%m%'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                musicList.add(new Music(resultSet.getInt("id"), resultSet.getString("name")));
            }

            return Optional.of(musicList);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public void addMusic(int id, String name) {
        String sql = "INSERT INTO study.music (id, name) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}