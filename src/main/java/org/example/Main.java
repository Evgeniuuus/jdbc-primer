package org.example;

import com.google.gson.Gson;
import org.example.model.Book;
import org.example.model.Person;
import org.example.service.BookService;
import org.example.service.MusicService;
import org.example.service.PersonService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        MusicService service;
        PersonService personService;
        BookService bookService;
        try {
            DataSourceManager manager = DataSourceManager.getInstance();
            service = new MusicService(manager.getDataSource());
            personService = new PersonService(manager.getDataSource());
            bookService = new BookService(manager.getDataSource());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------------------1е Задание------------------------------");
        service.findAll().orElse(new ArrayList<>()).forEach(System.out::println);
        System.out.println("------------------------------2е Задание------------------------------");
        service.find_compositions_without_symbols().orElse(new ArrayList<>()).forEach(System.out::println);
        System.out.println("------------------------------3е Задание------------------------------");
        service.addMusic(256, "NewGrounds.com");
        service.findAll().orElse(new ArrayList<>()).forEach(System.out::println);
        BufferedReader buf;
        try {
            buf = new BufferedReader(new FileReader("books.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Дальше делали с Анатолием Чураковым
        System.out.println("------------------------------4е Задание------------------------------");
        System.out.println("Парсим файл...");
        Gson gson = new Gson();
        Person[] visitors = gson.fromJson(buf, Person[].class);
        List<Book> books = Arrays.stream(visitors).flatMap(visitor -> visitor.
                getFavoriteBooks().stream().distinct()).toList();
        personService.createTable();
        personService.insert(List.of(visitors));
        bookService.createTable();
        bookService.insert(books);
        System.out.println("------------------------------5е Задание------------------------------");
        bookService.selectSorted().orElse(new ArrayList<>()).forEach(System.out::println);
        System.out.println("------------------------------6е Задание------------------------------");
        bookService.selectYear(2000).orElse(new ArrayList<>()).forEach(System.out::println);
        System.out.println("------------------------------7е Задание------------------------------");
        bookService.insert(123, "Code Media", "John Marrs", 1966, "", "");
        personService.insert(123, "Eugene", "Zhelinskiy", "987-654-321", false);
        bookService.findById(123).ifPresent(System.out::println);
        personService.findById(123).ifPresent(System.out::println);
        System.out.println("------------------------------8е Задание------------------------------");
        personService.drop();
        bookService.drop();
    }
}