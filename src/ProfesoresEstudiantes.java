
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProfesoresEstudiantes {

    // Listas principales del programa
    ArrayList<String> listaProfesores;
    ArrayList<ArrayList<String>> listaGeneralEstudiantes;

    boolean estudiantesRegistrados;

    // Constructor
    public ProfesoresEstudiantes() {

        listaProfesores = new ArrayList<>();
        listaGeneralEstudiantes = new ArrayList<>();

        estudiantesRegistrados = false;
    }

    // Método principal del sistema
    public void iniciar() {

        int opcion = 0;

        do {

            String menu = "SISTEMA DE PROFESORES Y ESTUDIANTES\n\n";
            menu += "1. Registrar profesores\n";
            menu += "2. Registrar estudiantes\n";
            menu += "3. Consultar lista de profesores y estudiantes\n";
            menu += "4. Consultar profesor y sus estudiantes\n";
            menu += "5. Consultar cantidad de estudiantes por profesor\n";
            menu += "6. Consultar estudiante y su director de grupo\n";
            menu += "7. Salir\n\n";
            menu += "Seleccione una opción:";

            String respuesta = JOptionPane.showInputDialog(menu);

            if (respuesta == null) {
                break;
            }

            try {
                opcion = Integer.parseInt(respuesta);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Ingrese una opción válida.");
                continue;
            }

            switch (opcion) {

                case 1:
                    registrarProfesores();
                    break;

                case 2:
                    registrarEstudiantes();
                    break;

                case 3:
                    consultarListaProfesoresYEstudiantes();
                    break;

                case 4:
                    consultaProfesorPorNombre();
                    break;

                case 5:
                    consultaCantidadEstudiantesPorProfesor();
                    break;

                case 6:
                    consultarEstudiante();
                    break;

                case 7:
                    JOptionPane.showMessageDialog(null,
                            "Gracias por utilizar el sistema.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null,
                            "Opción no válida.");
            }

        } while (opcion != 7);
    }

    // 1. Registrar profesores
    public void registrarProfesores() {

        if (!listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Los profesores ya fueron registrados.");
            return;
        }

        int cantidad = leerCantidad("Ingrese la cantidad de profesores:");

        if (cantidad == -1) {
            return;
        }

        if (cantidad == 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe registrar al menos un profesor.");
            return;
        }

        for (int i = 0; i < cantidad; i++) {

            String nombre = JOptionPane.showInputDialog(
                    "Ingrese el nombre del profesor " + (i + 1) + ":");

            if (nombre == null) {
                return;
            }

            nombre = nombre.trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "El nombre no puede estar vacío.");
                i--;
            } else if (listaProfesores.contains(nombre)) {

                JOptionPane.showMessageDialog(null,
                        "Este profesor ya se encuentra registrado.");
                i--;

            } else {

                listaProfesores.add(nombre);

                JOptionPane.showMessageDialog(null,
                        "Profesor registrado correctamente.");
            }
        }

        JOptionPane.showMessageDialog(null,
                "Registro de profesores finalizado.");
    }

    // 2. Registrar estudiantes
    public void registrarEstudiantes() {

        if (listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los profesores.");
            return;
        }

        if (estudiantesRegistrados) {
            JOptionPane.showMessageDialog(null,
                    "Los estudiantes ya fueron registrados.");
            return;
        }

        for (int i = 0; i < listaProfesores.size(); i++) {

            JOptionPane.showMessageDialog(null,
                    "Ingrese los estudiantes del profesor: "
                            + listaProfesores.get(i));

            // Se crea una lista nueva para cada profesor
            ArrayList<String> listaEstudiantes = new ArrayList<>();

            int cantidad = leerCantidad(
                    "¿Cuántos estudiantes tiene "
                            + listaProfesores.get(i) + "?");

            if (cantidad == -1) {
                return;
            }

            for (int j = 0; j < cantidad; j++) {

                String nombre = JOptionPane.showInputDialog(
                        "Ingrese el nombre del estudiante "
                                + (j + 1) + ":");

                if (nombre == null) {
                    return;
                }

                nombre = nombre.trim();

                if (nombre.isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "El nombre no puede estar vacío.");
                    j--;

                } else if (listaEstudiantes.contains(nombre)
                        || estudianteExiste(nombre)) {

                    JOptionPane.showMessageDialog(null,
                            "Este estudiante ya está registrado.");
                    j--;

                } else {

                    listaEstudiantes.add(nombre);
                }
            }

            // Se relaciona la lista con su profesor
            listaGeneralEstudiantes.add(listaEstudiantes);
        }

        estudiantesRegistrados = true;

        JOptionPane.showMessageDialog(null,
                "Estudiantes registrados correctamente.");
    }

    // 3. Consultar todos los profesores y estudiantes
    public void consultarListaProfesoresYEstudiantes() {

        if (listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los profesores.");
            return;
        }

        if (!estudiantesRegistrados) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los estudiantes.");
            return;
        }

        String resultado = "LISTA DE PROFESORES Y ESTUDIANTES\n\n";

        for (int i = 0; i < listaProfesores.size(); i++) {

            resultado += "Profesor: " + listaProfesores.get(i) + "\n";

            ArrayList<String> listaTemporal =
                    listaGeneralEstudiantes.get(i);

            resultado += "Estudiantes: ";

            if (listaTemporal.isEmpty()) {
                resultado += "No tiene estudiantes registrados.";
            } else {

                for (int j = 0; j < listaTemporal.size(); j++) {

                    resultado += listaTemporal.get(j);

                    if (j < listaTemporal.size() - 1) {
                        resultado += ", ";
                    }
                }
            }

            resultado += "\n\n";
        }

        JOptionPane.showMessageDialog(null, resultado);
    }

    // 4. Consultar un profesor y sus estudiantes
    public void consultaProfesorPorNombre() {

        if (listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los profesores.");
            return;
        }

        if (!estudiantesRegistrados) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los estudiantes.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                "Ingrese el nombre del profesor:");

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (listaProfesores.contains(nombre)) {

            int posicion = listaProfesores.indexOf(nombre);

            ArrayList<String> listaTemporal =
                    listaGeneralEstudiantes.get(posicion);

            String resultado = "Profesor: " + nombre + "\n\n";
            resultado += "Estudiantes:\n";

            if (listaTemporal.isEmpty()) {
                resultado += "No tiene estudiantes registrados.";
            } else {

                for (int i = 0; i < listaTemporal.size(); i++) {
                    resultado += (i + 1) + ". "
                            + listaTemporal.get(i) + "\n";
                }
            }

            JOptionPane.showMessageDialog(null, resultado);

        } else {

            JOptionPane.showMessageDialog(null,
                    "El profesor no se encuentra registrado.");
        }
    }

    // 5. Consultar cantidad de estudiantes por profesor
    public void consultaCantidadEstudiantesPorProfesor() {

        if (listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los profesores.");
            return;
        }

        if (!estudiantesRegistrados) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los estudiantes.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                "Ingrese el nombre del profesor:");

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (listaProfesores.contains(nombre)) {

            int posicion = listaProfesores.indexOf(nombre);

            int cantidad = listaGeneralEstudiantes
                    .get(posicion).size();

            JOptionPane.showMessageDialog(null,
                    "El profesor " + nombre
                            + " tiene " + cantidad + " estudiantes.");

        } else {

            JOptionPane.showMessageDialog(null,
                    "El profesor no se encuentra registrado.");
        }
    }

    // 6. Consultar estudiante y su director de grupo
    public void consultarEstudiante() {

        if (listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los profesores.");
            return;
        }

        if (!estudiantesRegistrados) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe registrar los estudiantes.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                "Ingrese el nombre del estudiante:");

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        boolean encontrado = false;
        String resultado = "";

        for (int i = 0; i < listaGeneralEstudiantes.size(); i++) {

            ArrayList<String> listaTemporal =
                    listaGeneralEstudiantes.get(i);

            if (listaTemporal.contains(nombre)) {

                resultado += "Estudiante: " + nombre + "\n";
                resultado += "Director de grupo: "
                        + listaProfesores.get(i) + "\n\n";

                encontrado = true;
            }
        }

        if (encontrado) {

            JOptionPane.showMessageDialog(null, resultado);

        } else {

            JOptionPane.showMessageDialog(null,
                    "El estudiante no se encuentra registrado.");
        }
    }

    // Verificar si un estudiante ya existe
    public boolean estudianteExiste(String nombre) {

        for (int i = 0; i < listaGeneralEstudiantes.size(); i++) {

            ArrayList<String> listaTemporal =
                    listaGeneralEstudiantes.get(i);

            if (listaTemporal.contains(nombre)) {
                return true;
            }
        }

        return false;
    }

    // Leer cantidades y validar que sean números positivos
    public int leerCantidad(String mensaje) {

        while (true) {

            String respuesta = JOptionPane.showInputDialog(mensaje);

            if (respuesta == null) {
                return -1;
            }

            try {

                int cantidad = Integer.parseInt(respuesta);

                if (cantidad >= 0) {
                    return cantidad;
                }

                JOptionPane.showMessageDialog(null,
                        "Ingrese un número igual o mayor que cero.");

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(null,
                        "Debe ingresar un número válido.");
            }
        }
    }
}



