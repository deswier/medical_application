package com.myapplication.xml;

import com.myapplication.model.FullName;
import com.myapplication.model.Profile;
import com.myapplication.tools.DateParser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class XmlHelper {
    static String fileName = "comProfileCatalog.xml";

    public static void save(Profile profile) {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(fileName);

            // Вызываем метод для добавления новой книги
            addNewProfile(document, profile);
            System.out.println("\n\nsave success\n\n");

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Profile read() {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(fileName);

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            // Просматриваем все подэлементы корневого - т.е. профили
            NodeList profiles = root.getChildNodes();
            for (int i = 0; i < profiles.getLength(); i++) {
                Node profile = profiles.item(i);
                // Если нода не текст, то это книга - заходим внутрь
                if (profile.getNodeType() != Node.TEXT_NODE) {
                    NodeList profileProps = profile.getChildNodes();
                    FullName name = null;
                    LocalDate date = null;
                    char gender = 0;
                    for (int j = 0; j < profileProps.getLength(); j++) {
                        Node profileProp = profileProps.item(j);
                        // Если нода не текст, то это один из параметров профиля - получаем
                        if (profileProp.getNodeType() != Node.TEXT_NODE) {

                            switch (profileProp.getNodeName()) {
                                case "Name":
                                    name = new FullName(profileProp.getChildNodes().item(0).getTextContent());
                                case "DateOfBirth":
                                    date = DateParser.parseStringToDate(profileProp.getChildNodes().item(0).getTextContent());
                                case "Gender":
                                    gender = profileProp.getChildNodes().item(0).getTextContent().toCharArray()[0];
                            }
                        }
                    }
                    if (name != null && date != null && gender != 0) {
                        return new Profile(name, date, gender);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Функция добавления новой книги и записи результата в файл
    private static void addNewProfile(Document document, Profile profile) throws TransformerFactoryConfigurationError, DOMException, InterruptedException {
        // Получаем корневой элемент
        Node root = document.getDocumentElement();

        // Создаем новую книгу по элементам
        // Сама книга <Profile>

        Element profileEl = document.createElement("Profile");
        // <Title>
        Element name = document.createElement("Name");

        Element dateOfBirth = document.createElement("DateOfBirth");
        // Устанавливаем значение текста внутри тега
        Element gender = document.createElement("Gender");

        //   Element image = document.createElement("Image");

        profileEl.setTextContent("Profile");
        name.setTextContent(profile.getName().toString());
        dateOfBirth.setTextContent(profile.getDateOfBirth().toString());
        gender.setTextContent(String.valueOf(profile.getGender()));

        // Добавляем внутренние элементы книги в элемент <Profile>
        profileEl.appendChild(name);
        profileEl.appendChild(dateOfBirth);
        profileEl.appendChild(gender);


        // Добавляем книгу в корневой элемент
        root.appendChild(profileEl);

        // Записываем XML в файл
        writeDocument(document);
    }

    // Функция для сохранения DOM в файл
    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(fileName);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}