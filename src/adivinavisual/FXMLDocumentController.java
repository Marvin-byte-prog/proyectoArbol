package adivinavisual;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javax.swing.JFrame;


public class FXMLDocumentController implements Initializable {

    static ArbolB.Nodo nodo = new ArbolB.Nodo<>();
    ArbolB.Nodo al = new ArbolB.Nodo<>();

    @FXML
    private void Adivina(ActionEvent event) {
        ArbolB.Nodo nodoAnterior = null;
        ArbolB.Nodo nodoNuevo = null;
        File a = new File("Animales.bin");
        if (a.exists()) {
            nodoNuevo = Deserializar();
        } else {
            nodoNuevo = new ArbolB.Nodo<>("El animal es doméstico?");
            //izq = si
            //der = no
            nodoNuevo.raiz = nodoNuevo;
            nodoNuevo.izq = new ArbolB.Nodo<>("Perro");
            nodoNuevo.der = new ArbolB.Nodo<>("Tigre");
            Persitencia(nodoNuevo);
        }

        String responde = null;
        ArbolB.Nodo reco;
        String ds;
        reco = nodoNuevo.raiz;
        try {
            while (reco != null) { //recorre el arbol
                if (nodoNuevo.validaEmpty(reco)) {//inicio del juego
                    nodoAnterior = reco;
                    if (reco.info.equals("El animal es doméstico?")) {
                        TextInputDialog dialog = new TextInputDialog("");
                        dialog.setTitle("Adivinador de Animales");
                        dialog.setHeaderText("Ingresa si o no");
                        dialog.setContentText(reco.info + " ");
                       
                        Optional<String> result = dialog.showAndWait();
                        responde = result.get();
                    } else {
                        TextInputDialog dialog1 = new TextInputDialog("");
                        dialog1.setTitle("Adivinador de Animales");
                        dialog1.setHeaderText("Ingresa si o no");
                        dialog1.setContentText("El animal que pensaste " + reco.info + "? ");
                        
                        Optional<String> result1 = dialog1.showAndWait();
                        responde = result1.get();
                    }
                    if (responde.equalsIgnoreCase("si")) {
                        reco = reco.izq;
                    }
                    else {
                        reco = reco.der;
                    }
                } 
                else {
                     TextInputDialog dialog3 = new TextInputDialog("");
                    dialog3.setTitle("Adivinador de Animales");
                    dialog3.setHeaderText("Ingresa si o no");
                    dialog3.setContentText("El animal que piensa es un(a)" + reco.info + "? ");
                    
                    Optional<String> result3 = dialog3.showAndWait();
                    String numero = result3.get();
                    if (numero.equalsIgnoreCase("si")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Gané");
                        alert.setHeaderText(null);
                        alert.setContentText("¡Soy el mejor, era un(a) "+ reco.info + "!");
                        alert.showAndWait();

                        TextInputDialog dialog4 = new TextInputDialog("");
                        dialog4.setTitle("Adivinador de Animales");
                        dialog4.setHeaderText("Ingresa si o no ó ingrese 'a' para visualizar el arbol");
                        dialog4.setContentText("Deseas seguir jugando?");
                        
                        Optional<String> result4 = dialog4.showAndWait();
                        String respuesta4 = result4.get();
                        if (respuesta4.equalsIgnoreCase("si")) {
                            reco = nodoNuevo;
                        } else if (respuesta4.equalsIgnoreCase("a")) {
                            setNodo(nodoNuevo);
                            DibujaArbol();

                            reco = null;
                            
                        } else {
                            
                            reco = null;
                        }
                        
                    } else 
                    {//agregamos el nuevo animal al arbol
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Perdí");
                        alert.setHeaderText(null);
                        alert.setContentText("¡No lo pude adivinar!");
                        alert.showAndWait();

                        TextInputDialog dialog5 = new TextInputDialog("");
                        dialog5.setTitle("Adivinador de Animales");
                        dialog5.setContentText("Escriba el nombre del animal que estas pensando ");
                        
                        Optional<String> result5 = dialog5.showAndWait();
                        String res = result5.get();

                        TextInputDialog dialog6 = new TextInputDialog("");
                        dialog6.setTitle("Adivinador de Animales");
                        dialog6.setHeaderText("Ingrese si o no");
                        dialog6.setContentText("Escriba una afirmación que sea verdadera para un(a) " + res
                                + " pero que sea falso para un(a) " + reco.info + " ");
                        
                        Optional<String> result6 = dialog6.showAndWait();
                        String respuesta2 = result6.get();
                        ArbolB.Nodo temp = reco;

                        ArbolB.Nodo nuevo = new ArbolB.Nodo<>(respuesta2);

                        if (responde.equalsIgnoreCase("si")) {
                            nodoAnterior.izq = nuevo;
                        } else {
                            nodoAnterior.der = nuevo;
                        }
                        nuevo.der = temp;
                        nuevo.izq = new ArbolB.Nodo<>(res);
                        TextInputDialog dialog7 = new TextInputDialog("");
                        dialog7.setTitle("Adivinador de Animales");
                        dialog7.setHeaderText("Ingresa si o no ó ingrese 'a' para visualizar el arbol");
                        dialog7.setContentText("Deseas seguir jugando?");
                        
                        Optional<String> result7 = dialog7.showAndWait();
                        String respuesta3 = result7.get();
                        if (respuesta3.equalsIgnoreCase("si")) {
                            reco = nodoNuevo;
                        } else if (respuesta3.equalsIgnoreCase("a")) {
                            setNodo(nodoNuevo);
                            DibujaArbol();
                            
                            reco = null;
                        } else {
                            reco = null;
                        }
                    }
                }//fin if principal
            }
            setNodo(nodoNuevo);
        } catch (Exception e) {
            System.out.println(e);
        }
        Persitencia(nodoNuevo);
    }

    /*metodo que nos permite almacenar el contenido de los nodos del arbol
    de esta manera conseguimos la permanencia de los datos
    */
    private static ArbolB.Nodo Persitencia(ArbolB.Nodo nodonuevo) {
        try {
            final FileOutputStream fo = new FileOutputStream("Animales.bin");
            final ObjectOutputStream oos = new ObjectOutputStream(fo);
            oos.writeObject(nodonuevo);
            oos.flush();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /* Abre el archivo .bin y carga el arbol previamente almacenado
        
    */
    private static ArbolB.Nodo Deserializar() {
        ArbolB.Nodo nodoNuevo = null;
        try {
            final FileInputStream fis = new FileInputStream("Animales.bin");
            final ObjectInputStream ois = new ObjectInputStream(fis);
            final Object deserializedObject = ois.readObject();
            if (deserializedObject instanceof ArbolB.Nodo) {
                nodoNuevo = (ArbolB.Nodo) deserializedObject;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nodoNuevo;
    }

    public static void setNodo(ArbolB.Nodo nodo) {
        FXMLDocumentController.nodo = nodo;
    }

    public static ArbolB.Nodo getNodo() {
        return nodo;
    }

    @FXML
    private void DibujaArbol() {
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screen.width / 2 - 1280 / 2;
        int y = screen.height / 2 - 720 / 2;

        JFrame Arbol = new JFrame("Arbol");

        Dibujar dibujar = new Dibujar(getNodo());

        Arbol.add(dibujar);
        Arbol.setSize(960, 500);
        Arbol.setLocation(x, y);
        Arbol.setVisible(true);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
