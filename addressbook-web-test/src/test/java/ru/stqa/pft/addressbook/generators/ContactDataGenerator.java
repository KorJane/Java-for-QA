package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.a.kornilov on 3/6/2018.
 */
public class ContactDataGenerator {
    @Parameter (names = "-c", description = "Contact count")
    public int count;

    @Parameter (names = "-f", description = "Target file")
    public String file;

    @Parameter (names = "-d", description = "Data format")
    public String format;


    public static void main (String [] args) throws IOException {

        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander JCommander = new JCommander(generator);
        try{
            JCommander.parse(args);
        } catch (ParameterException ex){
            JCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateGroups(count);
        if(format.equals("csv")){
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")){
            saveAsXml(contacts, new File(file));
        } else{
            System.out.println("Unsupported format" + format);
        }
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
//        xstream.alias("group", ContactData.class);
        String xml = xstream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }


    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts){
            writer.write(String.format("%s; %s; %s; %s; %s; %s\n", contact.getFirstName(), contact.getLastName(), contact.getAddress(), contact.getEmail(), contact.getHomePhone(), contact.getGroup()));
        }
        writer.close();
    }

    private  List<ContactData> generateGroups(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i ++){
            contacts.add(new ContactData().withFirstName(String.format("firstName%s", i)).withLastName(String.format("lastName%s", i)).
            withAddress(String.format("address%s", i)).withEmail(String.format("test@%s", i)).withHomePhone(String .format("111%s", i)). withGroup("test1"));
        }
        return contacts;
    }

}