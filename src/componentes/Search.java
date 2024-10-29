package componentes;

import BD.Querys;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Search extends javax.swing.JFrame {

    private JLabel filtrosText;
    private JLabel ubiText;
    private JTextField ubiField;
    private JTextField textField1;
    private JButton buscarButton;
    private JLabel huespedesText;
    private JButton reservarButton;
    private JSpinner huespedes;
    private JPanel resultadosPanel;
    private JPanel mainPanel;
    private JFrame frame3;

    // buscar hoteles por ubicacion como parametro
    List<String[]> resultadosFiltro;

    //traer los hoteles mas recientes
    List<String[]> resultados ;

    //constructor de la clase

    public Search() {
        // Eliminar o comentar la línea de setupUI que causa el error.
        // this.$$$setupUI$$$();
        frame3 = new JFrame();
        this.setTitle("Búsqueda de Alojamientos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setLocationRelativeTo((Component) null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Criterios de búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        searchPanel.add(new JLabel("Ubicación:"), gbc);
        this.ubiField = new JTextField(20);
        gbc.gridx = 1;
        searchPanel.add(this.ubiField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        searchPanel.add(new JLabel("Huéspedes:"), gbc);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        this.huespedes = new JSpinner(spinnerModel);
        gbc.gridx = 1;
        searchPanel.add(this.huespedes, gbc);
        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBackground(new Color(0, 122, 255));
        buscarButton.setForeground(Color.WHITE);
        buscarButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(buscarButton, gbc);
        this.resultadosPanel = new JPanel();
        this.resultadosPanel.setLayout(new BoxLayout(this.resultadosPanel, BoxLayout.Y_AXIS));
        this.resultadosPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));
        JScrollPane scrollPane = new JScrollPane(this.resultadosPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(mainPanel);

        // icono
        try {
            Image icono = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
            setIconImage(icono);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

        // accion que se va a ejecutar a la hora de presionar el boton de busqueda
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //esta es la instancia de las querys de la BD
                Querys bd = new Querys();
                resultadosFiltro = bd.filtrarUbicacion(ubiField.getText());
                resultados = bd.hotelesRecientes();

                // metodo que al ejecutarse tomara los datos obtenidos de la BD y los mostrará en un label
                // si la lista esta vacia mostrará un mensaje de vacio
                mostrarResultadoFiltro();
            }
        });
        ImageIcon img = new ImageIcon("src/resources/logo.png");
        frame3.setIconImage(img.getImage());
    }

    // metodo para filtrar las busqueda de hoteles


    public void mostrarResultadoFiltro() {
        // Limpiar los resultados previos
        this.resultadosPanel.removeAll();

        if (resultadosFiltro == null || resultadosFiltro.isEmpty()) {
            // Mensaje de "Sin resultados"
            JLabel sinResultadosLabel = new JLabel("Sin resultados de búsqueda :(");
            sinResultadosLabel.setFont(new Font("Arial", Font.BOLD, 14));
            sinResultadosLabel.setForeground(Color.RED);
            sinResultadosLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.resultadosPanel.add(sinResultadosLabel);
        } else {
            for (String[] post : resultadosFiltro) {
                JPanel resultadoPanel = new JPanel(new BorderLayout(5, 5));
                resultadoPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        new EmptyBorder(5, 5, 5, 5)
                ));
                resultadoPanel.setMaximumSize(new Dimension(2200, 100)); // Limitar altura para evitar estirado

                // Título, descripción, precio
                JLabel nombreHotel = new JLabel("Título: " + post[1]);
                nombreHotel.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel descripcion = new JLabel("<html>Descripción: " + post[2] + "</html>");
                JLabel precio = new JLabel("Precio: " + post[3]);

                resultadoPanel.add(nombreHotel, BorderLayout.NORTH);
                resultadoPanel.add(descripcion, BorderLayout.CENTER);
                resultadoPanel.add(precio, BorderLayout.SOUTH);

                // Botón compactado
                JButton reservarButton = new JButton("Reservar");
                reservarButton.setPreferredSize(new Dimension(90, 25));
                reservarButton.setBackground(new Color(0, 122, 255));
                reservarButton.setForeground(Color.WHITE);
                reservarButton.addActionListener(e -> {
                    PropiedadFormulario pf = new PropiedadFormulario();
                    pf.setVisible(true);
                });

                // Añadir el botón en la parte derecha
                resultadoPanel.add(reservarButton, BorderLayout.EAST);

                // Añadir el panel al resultadosPanel
                this.resultadosPanel.add(resultadoPanel);
            }
        }

        // Refrescar el panel
        this.resultadosPanel.revalidate();
        this.resultadosPanel.repaint();
    }


    //mostrarHoteles
    public void mostrarHoteles() {
    Querys bd = new Querys();
    resultados = bd.hotelesRecientes();

        // Limpiar el panel antes de mostrar nuevos resultados
        resultadosPanel.removeAll();

        // si la lista de resultados de búsqueda está vacía, se mostrará el siguiente mensaje
        if (resultados == null || resultados.isEmpty()) {

            // Crear un JLabel para el mensaje de "Sin resultados"
            JLabel sinResultadosLabel = new JLabel("Sin resultados de búsqueda :(");
            sinResultadosLabel.setFont(new Font("Arial", Font.BOLD, 14));
            sinResultadosLabel.setForeground(Color.RED);
            sinResultadosLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Añadir el JLabel al resultadosPanel
            this.resultadosPanel.add(sinResultadosLabel);

        } else {
            for (String[] post2 : resultados) {

                // Crear panel para cada resultado
                JPanel resultadosPanel1 = new JPanel(new BorderLayout(5, 5));
                resultadosPanel1.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        new EmptyBorder(5, 5, 5, 5)
                ));
                resultadosPanel1.setMaximumSize(new Dimension(2200, 100));

                // Etiquetas para título, descripción y precio
                JLabel nombreHotel1 = new JLabel("Título: " + post2[1]);
                nombreHotel1.setFont(new Font("Arial", Font.BOLD, 16));
                JLabel descripcion1 = new JLabel("<html>Descripción: " + post2[2] + "</html>");
                JLabel precio1 = new JLabel("Precio: " + post2[3]);

                // Añadir los componentes al panel
                resultadosPanel1.add(nombreHotel1, BorderLayout.NORTH);
                resultadosPanel1.add(descripcion1, BorderLayout.CENTER);
                resultadosPanel1.add(precio1, BorderLayout.SOUTH);

                // Botón de reservar

                JButton reservarButton1 = new JButton("Reservar");
                reservarButton1.setPreferredSize(new Dimension(90, 25));
                reservarButton1.setBackground(new Color(0, 122, 255));
                reservarButton1.setForeground(Color.WHITE);


                // accion que se realiza al presionar el boton de reservas
                reservarButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PropiedadFormulario pf = new PropiedadFormulario();
                        pf.setVisible(true);
                    }
                });

                resultadosPanel1.add(reservarButton1, BorderLayout.EAST);

                // Añadir el panel de resultados al resultadosPanel
                this.resultadosPanel.add(resultadosPanel1);
            }
        }

        // Refrescar el panel para asegurarse de que los cambios se muestran
        resultadosPanel.revalidate();
        resultadosPanel.repaint();
    }




}
