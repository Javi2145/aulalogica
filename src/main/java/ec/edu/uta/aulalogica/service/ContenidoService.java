package ec.edu.uta.aulalogica.service;

import ec.edu.uta.aulalogica.model.Credito;
import ec.edu.uta.aulalogica.model.Ejemplo;
import ec.edu.uta.aulalogica.model.Ejercicio;
import ec.edu.uta.aulalogica.model.Evaluacion;
import ec.edu.uta.aulalogica.model.Miembro;
import ec.edu.uta.aulalogica.model.Pregunta;
import ec.edu.uta.aulalogica.model.Tema;
import ec.edu.uta.aulalogica.model.Termino;
import java.util.List;
import org.springframework.stereotype.Service;

/** Contenido didáctico en memoria (temas, evaluaciones, niveles de teclado y prácticas). */
@Service
public class ContenidoService {

    private static final List<Tema> TEMAS = List.of(
        new Tema("1. Datos y operadores", "Qué es un algoritmo · Tipos de datos · Operadores",
            "Un algoritmo es una secuencia ordenada de pasos para resolver un problema. En Java, cada dato tiene un tipo (int, double, String, boolean) y se combina con operadores como +, -, *, / y %.",
            "¿Qué resultado da 10 / 4 con números enteros en Java?", List.of("2.5", "2", "3", "4"), 1),
        new Tema("2. Conectores booleanos", "AND, OR, NOT · Tablas de verdad",
            "Los conectores combinan condiciones: && (AND) exige que ambas sean verdaderas, || (OR) que al menos una lo sea y ! (NOT) invierte el valor.",
            "¿Qué resultado da true || false?", List.of("false", "true", "null", "Error"), 1),
        new Tema("3. Condicionales", "if else · Condicionales en cascada · switch",
            "if / else ejecuta un bloque según una condición. Con muchas opciones fijas se usa switch, que compara un valor contra varios casos.",
            "Para elegir entre muchas opciones fijas conviene usar:", List.of("switch", "while", "void", "Scanner"), 0),
        new Tema("4. Ciclos while y do while", "while · do while · Cuándo usar cada uno",
            "while revisa la condición antes de entrar; do-while ejecuta el bloque primero y revisa después, así que corre al menos una vez.",
            "¿Qué ciclo se ejecuta siempre al menos una vez?", List.of("while", "do-while", "if", "for"), 1),
        new Tema("5. Ciclo for y anidados", "for · Ciclos anidados",
            "for reúne inicio, condición y avance en una línea. Es ideal cuando sabes cuántas veces repetir. Un ciclo dentro de otro (anidado) recorre tablas o patrones.",
            "¿Cuántas vueltas da for(int i=0; i<3; i++)?", List.of("2", "3", "4", "0"), 1),
        new Tema("6. Arreglos y métodos", "Arreglos · Definir y llamar métodos",
            "Un arreglo guarda varios valores del mismo tipo y empieza en el índice 0. Un método agrupa instrucciones con nombre para reutilizarlas: se define una vez y se llama cuando se necesite.",
            "En int[] a = new int[5]; ¿cuál es el último índice?", List.of("5", "4", "3", "6"), 1),
        new Tema("7. Entrada con Scanner", "Scanner · nextInt · nextLine",
            "Scanner lee datos del teclado: new Scanner(System.in). nextInt() lee un entero, nextDouble() un decimal y nextLine() una línea de texto.",
            "¿Qué método lee una línea de texto?", List.of("nextInt()", "nextLine()", "print()", "length()"), 1),
        new Tema("8. Cadenas de texto", "String · length · equals · concatenar",
            "Un String es texto entre comillas dobles. length() da su tamaño, equals() compara su contenido y + une textos. Para comparar textos no se usa ==.",
            "¿Cómo se comparan dos textos en Java?", List.of("a == b", "a.equals(b)", "a = b", "a.length(b)"), 1),
        new Tema("9. Contadores y acumuladores", "Variables que cambian dentro de un ciclo",
            "Un contador suma 1 en cada vuelta (cont++) y un acumulador suma valores distintos (suma += n). Ambos empiezan con un valor inicial, casi siempre 0.",
            "¿Qué hace suma += 5?", List.of("Resta 5", "Suma 5 a suma", "Reinicia suma", "Multiplica por 5"), 1),
        new Tema("10. Clases y objetos", "Atributos · Métodos · new",
            "Una clase es un molde y un objeto es una copia creada con new. La clase define atributos (datos) y métodos (acciones).",
            "¿Con qué palabra se crea un objeto?", List.of("new", "class", "void", "int"), 0),
        new Tema("11. Algoritmos y EPS", "Entradas · Procesos · Salidas · Características",
            "Antes de programar se analiza el problema con EPS: qué datos entran (entradas), qué se hace con ellos (procesos) y qué resultado sale (salidas). Un buen algoritmo es preciso, ordenado y termina en un número finito de pasos.",
            "En el análisis EPS, ¿qué significa la P?", List.of("Pantalla", "Procesos", "Programa", "Puerto"), 1),
        new Tema("12. Pseudocódigo y diagramas", "Pseudocódigo · Diagrama de flujo · Tabla de decisión",
            "El pseudocódigo escribe el algoritmo en lenguaje casi natural, sin las reglas estrictas de Java. El diagrama de flujo lo dibuja con símbolos: óvalo (inicio y fin), paralelogramo (entrada y salida), rectángulo (proceso) y rombo (decisión). La tabla de decisión lista condiciones y acciones.",
            "¿Qué símbolo del diagrama de flujo representa una decisión?", List.of("Rombo", "Óvalo", "Rectángulo", "Paralelogramo"), 0),
        new Tema("13. Java básico: clase y main", "Estructura de una clase · main · variables · Scanner",
            "Todo programa Java vive dentro de una clase. La ejecución comienza en el método main: public static void main(String[] args). Dentro se declaran variables con su tipo, se leen datos con Scanner y se muestran resultados con System.out.println.",
            "¿Con qué método comienza a ejecutarse un programa Java?", List.of("main", "start", "run", "init"), 0),
        new Tema("14. Validación y pruebas", "Casos normales, límites e inválidos · Prueba de escritorio",
            "Validar es comprobar que el dato sea aceptable (no vacío, numérico y dentro del rango) antes de usarlo. Se prueba con tres tipos de casos: normal (dato típico), límite (mínimo y máximo aceptados) e inválido (vacío, texto o fuera de rango). La prueba de escritorio sigue el algoritmo a mano, paso a paso, en una tabla.",
            "Si el rango válido es de 0 a 120, ¿cuál es un caso límite?", List.of("60", "120", "121", "abc"), 1),
        new Tema("15. Programación orientada a objetos", "Clases · Objetos · Constructor · Encapsulamiento · Herencia · Polimorfismo",
            "La programación orientada a objetos (POO) organiza el programa en clases y objetos. La clase es el molde: define atributos (datos) y métodos (acciones); el objeto se crea con new. El constructor da los valores iniciales y this se refiere al objeto actual. Sus pilares son el encapsulamiento (atributos private con getters y setters), la herencia (extends: una clase hija reutiliza a su clase padre) y el polimorfismo (el mismo método responde distinto según el objeto).",
            "¿Qué palabra de Java hace que una clase herede de otra?", List.of("new", "this", "extends", "private"), 2));

    private static final List<Evaluacion> EVALUACIONES = List.of(
        new Evaluacion("Evaluación general", List.of(
            new Pregunta("¿Qué es un algoritmo?", List.of("Secuencia ordenada de pasos para resolver un problema", "Un lenguaje de programación", "Un tipo de dato", "Un error del programa"), 0, "Un algoritmo es una lista ordenada de pasos que resuelve un problema; el lenguaje solo sirve para escribirlo."),
            new Pregunta("¿Qué tipo de dato de Java guarda texto?", List.of("int", "String", "boolean", "double"), 1, "String guarda texto entre comillas dobles. int guarda enteros, double decimales y boolean true o false."),
            new Pregunta("¿Cuánto es 7 % 3?", List.of("2", "1", "0", "3"), 1, "% devuelve el residuo de la división: 7 = 2 · 3 + 1, por eso da 1."),
            new Pregunta("AND (&&) es verdadero cuando:", List.of("Alguna condición es verdadera", "Ambas son verdaderas", "Ninguna lo es", "Siempre"), 1, "&& solo da true cuando las dos condiciones son verdaderas."),
            new Pregunta("¿Qué valor tiene !true?", List.of("true", "null", "false", "0"), 2, "! invierte el valor: !true es false."),
            new Pregunta("¿Qué estructura permite decidir entre dos caminos?", List.of("for", "if / else", "main", "return"), 1, "if / else ejecuta un bloque si la condición se cumple y otro si no."),
            new Pregunta("¿Qué ciclo se ejecuta al menos una vez?", List.of("while", "for", "do-while", "if"), 2, "do-while ejecuta el bloque primero y revisa la condición al final."),
            new Pregunta("¿Qué ciclo conviene si conoces el número de repeticiones?", List.of("for", "if", "switch", "else"), 0, "for reúne inicio, condición y avance, ideal para repeticiones conocidas."),
            new Pregunta("¿Cuál es el primer índice de un arreglo en Java?", List.of("1", "0", "-1", "2"), 1, "Los arreglos empiezan en el índice 0."),
            new Pregunta("¿Qué palabra indica que un método no devuelve valor?", List.of("return", "void", "static", "int"), 1, "void significa que el método solo ejecuta acciones y no devuelve nada."))),
        new Evaluacion("Datos y operadores", List.of(
            new Pregunta("¿Qué tipo usar para guardar 3.14?", List.of("int", "double", "String", "boolean"), 1, "double guarda números con decimales; int solo enteros."),
            new Pregunta("¿Cuánto da 5 + 3 * 2?", List.of("16", "11", "10", "13"), 1, "* tiene prioridad sobre +: primero 3 * 2 = 6 y luego 5 + 6 = 11."),
            new Pregunta("¿Cuánto es 10 % 3?", List.of("3", "1", "0", "3.33"), 1, "El residuo de dividir 10 entre 3 es 1."),
            new Pregunta("¿Cómo se declara una variable entera edad con valor 20?", List.of("int edad = 20;", "edad int = 20;", "String edad = 20;", "integer edad = 20;"), 0, "Primero el tipo y luego el nombre: int edad = 20;"),
            new Pregunta("¿Qué muestra System.out.println(\"Hola \" + 5);?", List.of("Hola 5", "Hola", "Error", "Hola+5"), 0, "Con un String, el + une (concatena) el texto con el número."))),
        new Evaluacion("Condicionales y booleanos", List.of(
            new Pregunta("¿Qué da true && false?", List.of("true", "false", "null", "Error"), 1, "&& necesita que ambas sean verdaderas; con una falsa el resultado es false."),
            new Pregunta("¿Qué operador compara igualdad?", List.of("=", "==", ":=", "=>"), 1, "= asigna un valor y == compara si dos valores son iguales."),
            new Pregunta("Si edad = 18, ¿qué da edad > 18?", List.of("true", "false", "18", "Error"), 1, "18 no es mayor que 18; para incluir el 18 se usa >=."),
            new Pregunta("¿Qué bloque se ejecuta cuando la condición del if es falsa?", List.of("else", "while", "case", "return"), 0, "else se ejecuta cuando la condición del if no se cumple."),
            new Pregunta("En un switch, ¿qué evita que se ejecuten los casos siguientes?", List.of("break", "stop", "exit", "void"), 0, "break sale del switch; sin él, Java sigue ejecutando los casos de abajo."))),
        new Evaluacion("Ciclos", List.of(
            new Pregunta("¿Qué imprime for(int i=1; i<=3; i++) System.out.print(i)?", List.of("123", "012", "321", "1233"), 0, "i empieza en 1 y avanza hasta 3, incluido."),
            new Pregunta("¿Qué pasa con while(true) sin break?", List.of("Se ejecuta infinitamente", "Nunca se ejecuta", "Una sola vez", "Da error"), 0, "La condición siempre es verdadera, así que el ciclo nunca termina."),
            new Pregunta("¿Cómo se llama la variable que va sumando valores en cada vuelta?", List.of("Acumulador", "Constante", "Método", "Clase"), 0, "Un acumulador suma valores distintos en cada vuelta (suma += n)."),
            new Pregunta("¿Qué ciclo evalúa la condición al final?", List.of("while", "do-while", "for", "if"), 1, "do-while ejecuta primero y evalúa la condición después."),
            new Pregunta("¿Qué hace i++?", List.of("Resta 1", "Suma 1 a i", "Duplica i", "Reinicia i"), 1, "i++ aumenta el valor de i en 1."))),
        new Evaluacion("Arreglos, métodos y clases", List.of(
            new Pregunta("Si int[] a = {4,5,6}; ¿cuánto vale a[1]?", List.of("4", "5", "6", "1"), 1, "El índice 0 es el 4, el índice 1 es el 5."),
            new Pregunta("¿Cómo se obtiene el tamaño de un arreglo a?", List.of("a.length", "a.size()", "length(a)", "a.count"), 0, "En arreglos se usa la propiedad length, sin paréntesis."),
            new Pregunta("¿Qué indica void en un método?", List.of("No devuelve valor", "Devuelve un entero", "Es privado", "Es un ciclo"), 0, "void significa que el método no devuelve ningún valor."),
            new Pregunta("¿Qué palabra crea un objeto?", List.of("new", "create", "make", "this"), 0, "new reserva memoria y crea el objeto a partir de la clase."),
            new Pregunta("Los parámetros de un método son:", List.of("Datos que recibe", "Variables globales", "Comentarios", "Clases"), 0, "Los parámetros son los datos que el método recibe para trabajar."))),
        new Evaluacion("Programación orientada a objetos", List.of(
            new Pregunta("¿Qué es una clase en la POO?", List.of("Un molde que define atributos y métodos", "Un objeto ya creado", "Un tipo de ciclo", "Un error del programa"), 0, "La clase es el molde; con new se crean objetos a partir de ella."),
            new Pregunta("¿Cómo se crea un objeto de la clase Perro?", List.of("Perro p = Perro();", "Perro p = new Perro();", "new p = Perro;", "Perro p = class Perro();"), 1, "Se escribe la clase, el nombre del objeto y new con el constructor: Perro p = new Perro();"),
            new Pregunta("¿Para qué sirve un constructor?", List.of("Para destruir el objeto", "Para repetir instrucciones", "Para dar valores iniciales al objeto cuando se crea", "Para leer datos del teclado"), 2, "El constructor se ejecuta con new y deja el objeto con sus valores iniciales."),
            new Pregunta("¿A qué se refiere this dentro de un método?", List.of("Al objeto actual", "A la clase padre", "A una variable global", "Al programa principal"), 0, "this es el objeto que está ejecutando el método; sirve, por ejemplo, para escribir this.nombre = nombre;"),
            new Pregunta("¿Qué modificador oculta un atributo para usarlo solo con getters y setters?", List.of("public", "private", "static", "void"), 1, "private protege el atributo: solo se accede por los métodos de la clase (encapsulamiento)."),
            new Pregunta("¿Qué palabra permite que una clase herede de otra?", List.of("implements", "extends", "super", "import"), 1, "class Estudiante extends Persona hace que Estudiante reutilice lo de Persona."),
            new Pregunta("Si Perro extends Animal, ¿cómo se llama Animal?", List.of("Clase hija", "Clase padre (superclase)", "Objeto", "Método"), 1, "La clase de la que se hereda es la clase padre o superclase."),
            new Pregunta("Dos clases hijas redefinen hablar() con su propio comportamiento. ¿Qué pilar es?", List.of("Encapsulamiento", "Herencia", "Polimorfismo", "Constructor"), 2, "Polimorfismo: el mismo método responde distinto según el objeto (se redefine con @Override)."))));

    private static final List<String> NIVELES = List.of(
        "asd fgh jkl asd fgh jkl",
        "if else for while",
        "int x = 10;",
        "System.out.println(x);",
        "for (int i = 0; i < 5; i++) {",
        "String nombre = \"Ada\";",
        "if (edad >= 18) { return true; }",
        "public static void main(String[] args) {",
        "double promedio = suma / 3.0;",
        "while (contador <= 10) { contador++; }",
        "Scanner sc = new Scanner(System.in);",
        "int[] notas = new int[5];",
        "public int sumar(int a, int b) { return a + b; }",
        "switch (opcion) { case 1: break; default: break; }");

    private static final List<Ejercicio> EJERCICIOS = List.of(
        new Ejercicio("seleccion", "Selección", "Escribe una edad (0 a 120):",
            "if (edad >= 18) {\n  System.out.println(\"Mayor de edad\");\n} else {\n  System.out.println(\"Menor de edad\");\n}",
            0, 120, false),
        new Ejercicio("suma", "Suma (for)", "Escribe un número n (1 a 100):",
            "int suma = 0;\nfor (int i = 1; i <= n; i++) {\n  suma += i;\n}\nSystem.out.println(suma);",
            1, 100, false),
        new Ejercicio("validacion", "Validación", "Escribe una nota (0 a 10):",
            "if (nota < 0 || nota > 10) {\n  System.out.println(\"Fuera de rango\");\n} else if (nota >= 7) {\n  System.out.println(\"Aprobado\");\n} else {\n  System.out.println(\"Reprobado\");\n}",
            0, 10, false),
        new Ejercicio("parimpar", "Par o impar", "Escribe un número entero (0 a 1000):",
            "if (n % 2 == 0) {\n  System.out.println(\"Par\");\n} else {\n  System.out.println(\"Impar\");\n}",
            0, 1000, true),
        new Ejercicio("tabla", "Tabla", "Escribe un número (1 a 12):",
            "for (int i = 1; i <= 10; i++) {\n  System.out.println(n + \" x \" + i + \" = \" + (n * i));\n}",
            1, 12, true),
        new Ejercicio("factorial", "Factorial", "Escribe un número entero (0 a 12):",
            "int fact = 1;\nfor (int i = 2; i <= n; i++) {\n  fact *= i;\n}\nSystem.out.println(fact);",
            0, 12, true),
        new Ejercicio("regresiva", "Cuenta regresiva", "Escribe un número (1 a 20):",
            "while (n > 0) {\n  System.out.print(n + \" \");\n  n--;\n}\nSystem.out.println(\"¡Fin!\");",
            1, 20, true),
        new Ejercicio("poo", "Objetos (POO)", "Escribe el monto a depositar (0 a 1000):",
            "class Cuenta {\n  private int saldo = 100;\n  void depositar(int monto) {\n    if (monto > 0) {\n      saldo += monto;\n    }\n  }\n  int getSaldo() {\n    return saldo;\n  }\n}\n\nCuenta c = new Cuenta();\nc.depositar(monto);\nSystem.out.println(c.getSaldo());",
            0, 1000, true));

    /** Ejemplo resuelto de cada lección, en el mismo orden que TEMAS (RF-03). */
    private static final List<Ejemplo> EJEMPLOS = List.of(
        new Ejemplo("Calcular el promedio de tres notas: 8, 7 y 9.",
            List.of("Guardar cada nota en una variable double.", "Sumar las tres notas.", "Dividir entre 3.0 (con decimal, para no perder la parte decimal)."),
            "double a = 8, b = 7, c = 9;\ndouble promedio = (a + b + c) / 3.0;\nSystem.out.println(promedio);", "8.0"),
        new Ejemplo("Un estudiante tiene nota 8 y asistencia 70 %. Aprueba solo si nota >= 7 Y asistencia >= 75.",
            List.of("nota >= 7 es true.", "asistencia >= 75 es false.", "true && false da false: no aprueba."),
            "boolean aprueba = (nota >= 7) && (asistencia >= 75);\nSystem.out.println(aprueba);", "false"),
        new Ejemplo("Decir si un número es positivo, negativo o cero, con n = -4.",
            List.of("Si n > 0, es positivo.", "Si no, y n < 0, es negativo.", "En cualquier otro caso es cero."),
            "if (n > 0) {\n  System.out.println(\"Positivo\");\n} else if (n < 0) {\n  System.out.println(\"Negativo\");\n} else {\n  System.out.println(\"Cero\");\n}", "Negativo"),
        new Ejemplo("Mostrar 3, 2, 1 con un ciclo while.",
            List.of("Iniciar n = 3.", "Mientras n > 0: imprimir n y restarle 1.", "Cuando n llega a 0 la condición es falsa y el ciclo termina."),
            "int n = 3;\nwhile (n > 0) {\n  System.out.print(n + \" \");\n  n--;\n}", "3 2 1"),
        new Ejemplo("Mostrar los tres primeros resultados de la tabla del 2.",
            List.of("for empieza en i = 1.", "Se repite mientras i <= 3.", "En cada vuelta imprime 2 x i y suma 1 a i."),
            "for (int i = 1; i <= 3; i++) {\n  System.out.println(\"2 x \" + i + \" = \" + (2 * i));\n}", "2 x 1 = 2\n2 x 2 = 4\n2 x 3 = 6"),
        new Ejemplo("Sumar los elementos del arreglo {4, 5, 6} con un método.",
            List.of("El método recibe el arreglo como parámetro.", "Un for recorre desde el índice 0 hasta a.length - 1.", "return devuelve la suma acumulada."),
            "static int sumar(int[] a) {\n  int suma = 0;\n  for (int i = 0; i < a.length; i++) {\n    suma += a[i];\n  }\n  return suma;\n}\n// sumar(new int[]{4, 5, 6})", "15"),
        new Ejemplo("Leer la edad con Scanner y mostrarla (el usuario escribe 20).",
            List.of("Crear el Scanner sobre System.in.", "nextInt() lee un número entero.", "Concatenar el valor en el mensaje de salida."),
            "Scanner sc = new Scanner(System.in);\nSystem.out.print(\"Edad: \");\nint edad = sc.nextInt();\nSystem.out.println(\"Tienes \" + edad + \" años\");", "Edad: 20\nTienes 20 años"),
        new Ejemplo("Comparar los textos \"hola\" y \"HOLA\".",
            List.of("equals compara el contenido exacto: las mayúsculas importan.", "equalsIgnoreCase ignora mayúsculas y minúsculas.", "Nunca se compara texto con ==."),
            "String a = \"hola\", b = \"HOLA\";\nSystem.out.println(a.equals(b));\nSystem.out.println(a.equalsIgnoreCase(b));", "false\ntrue"),
        new Ejemplo("Contar y sumar los números del 1 al 4.",
            List.of("cont y suma empiezan en 0.", "En cada vuelta cont++ cuenta y suma += i acumula.", "Al final se muestran los dos valores."),
            "int cont = 0, suma = 0;\nfor (int i = 1; i <= 4; i++) {\n  cont++;\n  suma += i;\n}\nSystem.out.println(cont + \" números, suma \" + suma);", "4 números, suma 10"),
        new Ejemplo("Crear la clase Estudiante con un nombre y un método saludar.",
            List.of("La clase define el atributo nombre y el método saludar.", "new crea un objeto de esa clase.", "El objeto usa su atributo y llama a su método."),
            "class Estudiante {\n  String nombre;\n  void saludar() {\n    System.out.println(\"Hola, soy \" + nombre);\n  }\n}\n\nEstudiante e = new Estudiante();\ne.nombre = \"Ana\";\ne.saludar();", "Hola, soy Ana"),
        new Ejemplo("Problema: saber si una persona es mayor de edad.",
            List.of("Entrada: la edad (entero de 0 a 120).", "Proceso: comparar edad >= 18.", "Salida: el mensaje «Mayor de edad» o «Menor de edad»."),
            "ENTRADA: edad\nPROCESO: edad >= 18\nSALIDA: Mayor de edad / Menor de edad", "Con edad = 20 → Mayor de edad"),
        new Ejemplo("Pseudocódigo y diagrama de flujo para aprobar o reprobar según la nota.",
            List.of("Óvalo de inicio y paralelogramo para leer la nota.", "Rombo con la pregunta nota >= 7: el sí lleva a «Aprobado» y el no a «Reprobado».", "Óvalo de fin."),
            "INICIO\n  LEER nota\n  SI nota >= 7 ENTONCES\n    ESCRIBIR \"Aprobado\"\n  SINO\n    ESCRIBIR \"Reprobado\"\n  FIN SI\nFIN", "Con nota = 8 → Aprobado"),
        new Ejemplo("Programa mínimo en Java que muestra un saludo.",
            List.of("public class define la clase.", "main es el punto de entrada del programa.", "System.out.println muestra el resultado."),
            "public class Saludo {\n  public static void main(String[] args) {\n    String nombre = \"Ana\";\n    System.out.println(\"Hola, \" + nombre);\n  }\n}", "Hola, Ana"),
        new Ejemplo("Probar la práctica de nota (rango de 0 a 10) con tres tipos de caso.",
            List.of("Normal: nota = 8 → Aprobado.", "Límite: nota = 10 se acepta y nota = 7 es Aprobado.", "Inválido: nota = 11 o vacía → mensaje de error, sin que la página falle."),
            "if (nota < 0 || nota > 10) {\n  System.out.println(\"Fuera de rango\");\n}", "Con nota = 11 → Fuera de rango"),
        new Ejemplo("Crear la clase Persona con el atributo privado nombre y la clase Estudiante que hereda de ella.",
            List.of("Persona guarda el nombre como private y lo entrega con el método getNombre().", "El constructor recibe el nombre y lo guarda con this.nombre.", "Estudiante usa extends para heredar de Persona y super(nombre) para llamar al constructor del padre."),
            "class Persona {\n  private String nombre;\n  Persona(String nombre) {\n    this.nombre = nombre;\n  }\n  String getNombre() {\n    return nombre;\n  }\n}\n\nclass Estudiante extends Persona {\n  Estudiante(String nombre) {\n    super(nombre);\n  }\n  void estudiar() {\n    System.out.println(getNombre() + \" estudia POO\");\n  }\n}\n\nEstudiante e = new Estudiante(\"Ana\");\ne.estudiar();", "Ana estudia POO"));

    /** Práctica en Java relacionada con cada lección (índice dentro de EJERCICIOS). */
    private static final List<Integer> PRACTICA_DE_TEMA = List.of(3, 2, 0, 6, 4, 5, 0, 2, 1, 3, 0, 2, 3, 2, 7);

    /** Glosario del periodo académico (RF-06). */
    private static final List<Termino> GLOSARIO = List.of(
        new Termino("Acumulador", "Variable que suma valores distintos en cada vuelta de un ciclo (suma += n)."),
        new Termino("Algoritmo", "Secuencia ordenada y finita de pasos para resolver un problema."),
        new Termino("Arreglo", "Estructura que guarda varios valores del mismo tipo; su primer índice es 0."),
        new Termino("Caso límite", "Dato en el borde del rango aceptado, como el mínimo o el máximo permitido."),
        new Termino("Ciclo", "Estructura que repite un bloque de instrucciones: while, do-while y for."),
        new Termino("Clase", "Molde que define los atributos y métodos de un tipo de objeto."),
        new Termino("Condicional", "Estructura que elige qué instrucciones ejecutar según una condición: if, if/else y switch."),
        new Termino("Constructor", "Método especial que se ejecuta con new y da los valores iniciales al objeto; se llama igual que la clase."),
        new Termino("Contador", "Variable que aumenta de uno en uno en cada vuelta (cont++)."),
        new Termino("Diagrama de flujo", "Dibujo del algoritmo con símbolos: óvalo, paralelogramo, rectángulo y rombo."),
        new Termino("Encapsulamiento", "Pilar de la POO: los atributos se declaran private y solo se usan mediante métodos (getters y setters)."),
        new Termino("EPS", "Análisis de un problema en Entradas, Procesos y Salidas."),
        new Termino("Entrada", "Dato que el usuario entrega al programa para que lo procese."),
        new Termino("Herencia", "Pilar de la POO: una clase hija (extends) reutiliza los atributos y métodos de su clase padre."),
        new Termino("Método", "Bloque de instrucciones con nombre que se define una vez y se llama cuando se necesita."),
        new Termino("MVC", "Arquitectura que separa Modelo (datos), Vista (pantalla) y Controlador (rutas)."),
        new Termino("Objeto", "Copia de una clase creada con new, con sus propios valores en los atributos."),
        new Termino("Operador", "Símbolo que combina valores: aritméticos (+ - * / %), de comparación (== < >) y lógicos (&& || !)."),
        new Termino("Polimorfismo", "Pilar de la POO: el mismo método se comporta distinto según el objeto que lo ejecuta (@Override)."),
        new Termino("POO", "Programación orientada a objetos: forma de organizar el programa en clases y objetos."),
        new Termino("Prueba de escritorio", "Seguimiento manual del algoritmo, paso a paso, para comprobar su resultado."),
        new Termino("Pseudocódigo", "Algoritmo escrito en lenguaje casi natural, sin las reglas estrictas de un lenguaje de programación."),
        new Termino("Salida", "Resultado que el programa muestra después de procesar los datos."),
        new Termino("Scanner", "Clase de Java que lee datos escritos por el usuario, como nextInt() y nextLine()."),
        new Termino("String", "Tipo de dato de Java que guarda texto entre comillas dobles."),
        new Termino("Validación", "Comprobación de que un dato no esté vacío, sea del tipo esperado y esté dentro del rango."),
        new Termino("Variable", "Espacio con nombre y tipo donde el programa guarda un dato que puede cambiar."));

    /** Roles de la guía asignados a los integrantes (editar aquí si el equipo los reparte distinto). */
    private static final List<Miembro> MIEMBROS = List.of(
        new Miembro("KEVIN GARCÉS", "Coordinación e integración", "Organiza tareas, revisa hitos y mantiene una versión ejecutable."),
        new Miembro("ESTEBAN ZAMBRANO", "Análisis y contenido", "Cuida requisitos, material didáctico, algoritmos y claridad pedagógica."),
        new Miembro("CRISTIAN GÓMEZ", "Desarrollo Java/web", "Implementa controladores, servicios, modelos, vistas y estilos."),
        new Miembro("ERICK CORDONEZ", "Pruebas y documentación", "Diseña casos, registra defectos, verifica README y evidencias."));

    /** Créditos de recursos de terceros (RF-07). */
    private static final List<Credito> CREDITOS = List.of(
        new Credito("three.js r128", "Animación 3D de la portada", "MIT", "https://threejs.org"),
        new Credito("Spring Boot, Spring Web y Validation", "Framework del aplicativo", "Apache 2.0", "https://spring.io/projects/spring-boot"),
        new Credito("Thymeleaf", "Motor de plantillas de las vistas", "Apache 2.0", "https://www.thymeleaf.org"),
        new Credito("Spring Initializr", "Generador del proyecto Maven", "Apache 2.0", "https://start.spring.io"));

    public static final List<String> COLORES =
        List.of("#2cf5ff", "#ffb454", "#a78bfa", "#ff5ea8", "#2ed573", "#4d8dff");

    public static final List<String> INTEGRANTES =
        List.of("KEVIN GARCÉS", "ESTEBAN ZAMBRANO", "CRISTIAN GÓMEZ", "ERICK CORDONEZ");

    public List<Tema> temas() { return TEMAS; }
    public List<Evaluacion> evaluaciones() { return EVALUACIONES; }
    public List<String> niveles() { return NIVELES; }
    public List<Ejercicio> ejercicios() { return EJERCICIOS; }

    public Tema tema(int i) { return TEMAS.get(clamp(i, TEMAS.size())); }
    public Evaluacion evaluacion(int i) { return EVALUACIONES.get(clamp(i, EVALUACIONES.size())); }
    public Ejercicio ejercicio(int i) { return EJERCICIOS.get(clamp(i, EJERCICIOS.size())); }

    public List<Termino> glosario() { return GLOSARIO; }
    public List<Miembro> miembros() { return MIEMBROS; }
    public List<Credito> creditos() { return CREDITOS; }

    /** Ejemplo resuelto de la lección i. */
    public Ejemplo ejemplo(int i) { return EJEMPLOS.get(clamp(i, EJEMPLOS.size())); }

    /** Índice de la práctica en Java relacionada con la lección i. */
    public int practicaRelacionada(int i) { return PRACTICA_DE_TEMA.get(clamp(i, PRACTICA_DE_TEMA.size())); }

    public static int clamp(int i, int n) { return Math.max(0, Math.min(n - 1, i)); }

    /** Filtra temas por texto sin distinguir mayúsculas ni tildes (igual que el buscador original). */
    public List<Integer> buscarTemas(String texto) {
        String f = normalizar(texto == null ? "" : texto);
        java.util.ArrayList<Integer> r = new java.util.ArrayList<>();
        for (int i = 0; i < TEMAS.size(); i++) {
            Tema t = TEMAS.get(i);
            if (t.clave().contains(f)) r.add(i);
        }
        return r;
    }

    public static String normalizar(String s) {
        return Tema.normalizar(s);
    }
}
