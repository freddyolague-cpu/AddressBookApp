import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    // HashMap: número telefónico = llave, nombre = valor
    private HashMap<String, String> contacts;
    private final String fileName = "contacts.txt";
    private Scanner scanner;

    public AddressBook() {
        contacts = new HashMap<>();
        scanner = new Scanner(System.in);
        load();
    }

    // Carga los contactos almacenados en el archivo
    public void load() {
        contacts.clear();

        File file = new File(fileName);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", 2);

                if (data.length == 2) {
                    contacts.put(data[0].trim(), data[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Guarda los contactos en formato CSV
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (Map.Entry<String, String> contact : contacts.entrySet()) {
                writer.write(contact.getKey() + "," + contact.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Muestra todos los contactos
    public void list() {
        System.out.println("\nContactos:");

        if (contacts.isEmpty()) {
            System.out.println("No hay contactos guardados.");
            return;
        }

        for (Map.Entry<String, String> contact : contacts.entrySet()) {
            System.out.println(contact.getKey() + " : " + contact.getValue());
        }
    }

    // Crea un nuevo contacto
    public void create() {
        System.out.print("Ingrese el número telefónico: ");
        String number = scanner.nextLine();

        if (contacts.containsKey(number)) {
            System.out.println("El número ya existe.");
            return;
        }

        System.out.print("Ingrese el nombre del contacto: ");
        String name = scanner.nextLine();

        contacts.put(number, name);
        save();

        System.out.println("Contacto guardado correctamente.");
    }

    // Elimina un contacto
    public void delete() {
        System.out.print("Ingrese el número telefónico a eliminar: ");
        String number = scanner.nextLine();

        if (contacts.containsKey(number)) {
            contacts.remove(number);
            save();
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    // Menú interactivo
    public void menu() {
        int option;

        do {
            System.out.println("\n1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        list();
                        break;
                    case 2:
                        create();
                        break;
                    case 3:
                        delete();
                        break;
                    case 4:
                        save();
                        System.out.println("Programa finalizado.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                option = 0;
                System.out.println("Ingrese una opción válida.");
            }

        } while (option != 4);
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.menu();
    }
}
