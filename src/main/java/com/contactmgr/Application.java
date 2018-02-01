package com.contactmgr;

import com.contactmgr.model.Contact;
import com.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class Application {
    // Hold a reusable reference to a SessionFactory (since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a StandardServiceRegistry
        final ServiceRegistry serviceRegistry = new
                StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(serviceRegistry).buildMetadata()
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new ContactBuilder("Zheng", "Yuan")
                .withEmail("yuanzhengstl@gmail.com")
                .withPhone(3142851077L)
                .build();

        int id = save(contact);

        // Display a list of contacts before the update
        System.out.printf("%n%nBefore update%n%n");
        fetchAllContacts().stream().forEach(System.out::println);

        // Get the persisted contact
//        Contact c = findContactById(id);

//        // Update the contact
//        c.setFirstName("Qianran");
//        c.setLastName("Yang");

        // Persist the changes
        System.out.printf("%n%nDeleting...%n%n");
//        delete(c);
        deleteAll();
        System.out.printf("%n%nDelete complete!%n%n");

        // Display a list of contacts after the update
        System.out.printf("%n%nAfter delete%n%n");
        fetchAllContacts().stream().forEach(System.out::println);
    }

    private static Contact findContactById(int id) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        // Close the session
        session.close();

        // Return the object
        return contact;
    }

    private static void update(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.update(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    private static void deleteAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Execute deleta all contacts
        String stringQuery = String.format("DELETE FROM %s", Contact.class.getName());
        Query query = session.createQuery(stringQuery);
        query.executeUpdate();

        // Close the session
        session.close();
    }

    private static void delete(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.delete(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    private static int save(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to save the contact
        int id = (int)session.save(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

        return id;
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create Criteria
        Criteria criteria = session.createCriteria(Contact.class);

        // Get a list of Contact objects according to the criteria object
        List<Contact> contacts = criteria.list();

        // Close a session
        session.close();

        return contacts;
    }
}
