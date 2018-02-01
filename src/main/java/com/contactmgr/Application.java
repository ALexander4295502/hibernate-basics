package com.contactmgr;

import com.contactmgr.model.Contact;
import com.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

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

    }
}
