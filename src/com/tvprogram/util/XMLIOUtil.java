package com.tvprogram.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tvprogram.model.Chanel;
import com.tvprogram.model.Program;



public class XMLIOUtil {

    public static void writeChanelToXml(String fileName, Chanel chanel)
            throws TransformerException, ParserConfigurationException {

        if (chanel.getProgram().size() == 0)
            return;
        // обьявление переменных для XML парсера
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        // создание корневого элемента
        Element rootElement = doc.createElement("Chanel");
        doc.appendChild(rootElement);

        Element titleChanelElem = doc.createElement("Title");
        titleChanelElem.appendChild(doc.createTextNode(chanel.getTitle()));
        rootElement.appendChild(titleChanelElem);

        Element programsElem = doc.createElement("Programs");
        rootElement.appendChild(programsElem);

        for (int i = 0; i != chanel.getProgram().size(); i++) {
            Element program = doc.createElement("Program");
            programsElem.appendChild(program);

            Element programTitleElem = doc.createElement("Title");
            Element programStartTimeElem = doc.createElement("Start");
            Element programEndTimeElem = doc.createElement("End");
            programTitleElem.appendChild(doc.createTextNode(chanel.getProgram().get(i).getTitle()));
            programStartTimeElem.appendChild(
                    doc.createTextNode(DateTimeUtils.timeToStr(chanel.getProgram().get(i).getStartTime())));
            programEndTimeElem
                    .appendChild(doc.createTextNode(DateTimeUtils.timeToStr(chanel.getProgram().get(i).getEndTime())));

            program.appendChild(programTitleElem);
            program.appendChild(programStartTimeElem);
            program.appendChild(programEndTimeElem);
        }

        // запись полученной xml структуры в файл
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        // установка параметров кодировки и переносов строк
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(new File(fileName));
        // запись файла
        transformer.transform(source, result);
    }

    public static void readChanelFromXml(String fileName, Chanel chanel) throws SAXException, IOException, ParserConfigurationException {
        // обьявление файла
        File fXmlFile = new File(fileName);
        // инициализация парсера xml
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        // чтение списка элементов из парсера по названию

        // Название канала
        Element elem = (Element) doc.getElementsByTagName("Chanel").item(0);
        chanel.setTitle(elem.getElementsByTagName("Title").item(0).getTextContent());

        // Программы
        NodeList programsList = doc.getElementsByTagName("Program");
        if (programsList.getLength() == 0)
            return;

        for (int temp = 0; temp < programsList.getLength(); temp++) {
            // последовательный перебор элементов и добавление их в массив
            Program tmpProgram = new Program();
            org.w3c.dom.Node nNode = programsList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                tmpProgram.setTitle(eElement.getElementsByTagName("Title").item(0).getTextContent());
                tmpProgram.setStartTime(DateTimeUtils.strToTime(eElement.getElementsByTagName("Start").item(0).getTextContent()));
                tmpProgram.setEndTime(DateTimeUtils.strToTime(eElement.getElementsByTagName("End").item(0).getTextContent()));
                // вызов метода добавления новой записи
                chanel.getProgram().add(tmpProgram);
            }
        }
    }
}

