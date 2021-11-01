package by.student.javaswreader;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class FileParsing {

    //Коллекция для хранения информации о программах
    private static ArrayList<Program> programs = new ArrayList<>();

    private static int sizesum = 0;
    private static int costsum = 0;

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        //Получаем фабрику, чтобы потом из нее получить билдер
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //Из фабрики получаем билдер, который будет парсить файл и создаст структуру Document
        DocumentBuilder db = dbf.newDocumentBuilder();
        //Парсим XML, создав структуру Document. Теперь есть доступ к элементам файла
        Document doc = db.parse(new File("softwarelist.XML"));

        //Нормализуем структуру XML
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("program");

        //Перебор всех элементов program
        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;

                Node node1 = elem.getElementsByTagName("size").item(0);
                String size = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("cost").item(0);
                String cost = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("name").item(0);
                String name = node3.getTextContent();

                Node node4 = elem.getElementsByTagName("type").item(0);
                String type = node4.getTextContent();

                programs.add(new Program(type, size, cost, name));

                int x = Integer.parseInt(size);
                int y = Integer.parseInt(cost);
                sizesum += x;
                costsum += y;

            }
        }


        Element root = doc.getDocumentElement();
        Element total = doc.createElement("softwareTotal");
        root.appendChild(total);

        Element sizesumm =doc.createElement("sizeTotal");
        Element costsumm =doc.createElement("costTotal");

        sizesumm.appendChild(doc.createTextNode(String.valueOf(sizesum)));
        costsumm.appendChild(doc.createTextNode(String.valueOf(costsum)));

        total.appendChild(sizesumm);
        total.appendChild(costsumm);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;

        try {
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File("softwarelist.xml"));

        try {
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        DOMSource source = new DOMSource(doc);


    }

}
