package components;

import Controllers.ActualUser;
import Controllers.SearchController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Search extends javax.swing.JFrame {

    private JLabel filtrosText;
    private JLabel ubiText;
    private JTextField ubiField;
    private JTextField textField1;
    private JButton searchButton, profilePic;
    private JLabel guestsText;
    private JButton reserveButton;
    private JSpinner guests;
    private JPanel resultsPanel;
    private JPanel mainPanel;
    private JFrame frame3;

    // buscar hoteles por ubicacion como parametro
    List<String[]> resultsFilter;

    //traer los hoteles mas recientes
    List<String[]> results;

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

        // Ubicacion
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        searchPanel.add(new JLabel("Ubicación:"), gbc);

        this.ubiField = new JTextField(20);
        gbc.gridx = 1;
        searchPanel.add(this.ubiField, gbc);

        // User Profile Button
        profilePic = new JButton();
        try {
            // Establece un tamaño preferido para el botón antes de cargar la imagen
            profilePic.setPreferredSize(new Dimension(40, 42));

            // Carga la imagen original
            Image originalProfile = ImageIO.read(getClass().getResource("/resources/profile.png"));

            // Escala la imagen para que se ajuste exactamente al tamaño del botón
            Image scaledProfile = originalProfile.getScaledInstance(
                    profilePic.getPreferredSize().width,
                    profilePic.getPreferredSize().height,
                    Image.SCALE_SMOOTH
            );

            // Establece el icono escalado
            profilePic.setIcon(new ImageIcon(scaledProfile));

            // Configura el botón para que no tenga borde ni fondo
            profilePic.setBorderPainted(false);
            profilePic.setContentAreaFilled(false);

            // Agrega el botón al panel de búsqueda
            searchPanel.add(profilePic);
        } catch (IOException e) {
            e.printStackTrace(); // Cambia throw new RuntimeException(e) por un manejo de error más suave
        }


        // abrir el perfil luego de dar click en el boton
        profilePic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // metodo para establecer la ventana de perfil

                    UserProfilePanel up = new UserProfilePanel(ActualUser.userName, ActualUser.email);
                    up.setVisible(true);
                    up.setSize(1920, 1080);

                    // cerrar la ventana actual
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        searchPanel.add(new JLabel("Huéspedes:"), gbc);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        this.guests = new JSpinner(spinnerModel);
        gbc.gridx = 1;
        searchPanel.add(this.guests, gbc);
        JButton searchButton = new JButton("Buscar");
        searchButton.setBackground(new Color(0, 122, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(searchButton, gbc);
        this.resultsPanel = new JPanel();
        this.resultsPanel.setLayout(new BoxLayout(this.resultsPanel, BoxLayout.Y_AXIS));
        this.resultsPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));
        JScrollPane scrollPane = new JScrollPane(this.resultsPanel);
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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //esta es la instancia del controlador del search

                SearchController sc = new SearchController();
                resultsFilter = sc.ubicationFilter(ubiField.getText());
                results = sc.recentHotels();

                // metodo que al ejecutarse tomara los datos obtenidos de la BD y los mostrará en un label
                // si la lista esta vacia mostrará un mensaje de vacio
                getResultsFilter();
            }
        });
        ImageIcon img = new ImageIcon("src/resources/logo.png");
        frame3.setIconImage(img.getImage());
    }

    // metodo para filtrar las busqueda de hoteles


    public void getResultsFilter() {
        // Limpiar los resultados previos
        this.resultsPanel.removeAll();

        if (resultsFilter == null || resultsFilter.isEmpty()) {
            // Mensaje de "Sin resultados"
            JLabel withoutResultsLabel = new JLabel("Sin resultados de búsqueda :(");
            withoutResultsLabel.setFont(new Font("Arial", Font.BOLD, 14));
            withoutResultsLabel.setForeground(Color.RED);
            withoutResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.resultsPanel.add(withoutResultsLabel);
        } else {
            for (String[] post : resultsFilter) {
                JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
                resultPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        new EmptyBorder(5, 5, 5, 5)
                ));
                resultPanel.setMaximumSize(new Dimension(2200, 100)); // Limitar altura para evitar estirado

                // Título, descripción, precio
                JLabel hotelName = new JLabel("Título: " + post[1]);
                hotelName.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel description = new JLabel("<html>Descripción: " + post[2] + "</html>");
                JLabel price = new JLabel("Precio: " + post[3]);

                resultPanel.add(hotelName, BorderLayout.NORTH);
                resultPanel.add(description, BorderLayout.CENTER);
                resultPanel.add(price, BorderLayout.SOUTH);

                // Botón compactado
                JButton reserveButton = new JButton("Reservar");
                reserveButton.setPreferredSize(new Dimension(90, 25));
                reserveButton.setBackground(new Color(0, 122, 255));
                reserveButton.setForeground(Color.WHITE);
                reserveButton.addActionListener(e -> {
                    propertiesForm pf = new propertiesForm();
                    pf.setVisible(true);
                });

                // Añadir el botón en la parte derecha
                resultPanel.add(reserveButton, BorderLayout.EAST);

                // Añadir el panel al resultadosPanel
                this.resultsPanel.add(resultPanel);
            }
        }

        // Refrescar el panel
        this.resultsPanel.revalidate();
        this.resultsPanel.repaint();
    }


    //mostrarHoteles
    public void getAllHotels() {
    // instancia controlador del search
    SearchController sc = new SearchController();
    results = sc.recentHotels();

        // Limpiar el panel antes de mostrar nuevos resultados
        resultsPanel.removeAll();

        // si la lista de resultados de búsqueda está vacía, se mostrará el siguiente mensaje
        if (results == null || results.isEmpty()) {

            // Crear un JLabel para el mensaje de "Sin resultados"
            JLabel withoutResults = new JLabel("Sin resultados de búsqueda :(");
            withoutResults.setFont(new Font("Arial", Font.BOLD, 14));
            withoutResults.setForeground(Color.RED);
            withoutResults.setHorizontalAlignment(SwingConstants.CENTER);

            // Añadir el JLabel al resultadosPanel
            this.resultsPanel.add(withoutResults);

        } else {
            for (String[] post2 : results) {

                // Crear panel para cada resultado
                JPanel resultsPanel1 = new JPanel(new BorderLayout(5, 5));
                resultsPanel1.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        new EmptyBorder(5, 5, 5, 5)
                ));
                resultsPanel1.setMaximumSize(new Dimension(2200, 100));

                // Etiquetas para título, descripción y precio
                JLabel hotel1Name = new JLabel("Título: " + post2[1]);
                hotel1Name.setFont(new Font("Arial", Font.BOLD, 16));
                JLabel description1 = new JLabel("<html>Descripción: " + post2[2] + "</html>");
                JLabel price1 = new JLabel("Precio: " + post2[3]);

                // Añadir los componentes al panel
                resultsPanel1.add(hotel1Name, BorderLayout.NORTH);
                resultsPanel1.add(description1, BorderLayout.CENTER);
                resultsPanel1.add(price1, BorderLayout.SOUTH);

                // Botón de reservar

                JButton reserveButton1 = new JButton("Reservar");
                reserveButton1.setPreferredSize(new Dimension(90, 25));
                reserveButton1.setBackground(new Color(0, 122, 255));
                reserveButton1.setForeground(Color.WHITE);


                // accion que se realiza al presionar el boton de reservas
                reserveButton1.addActionListener(new ActionListener() {

                    // mostrar el formulario para la publicacion

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        propertiesForm pf = new propertiesForm();
                        pf.setVisible(true);
                    }
                });

                resultsPanel1.add(reserveButton1, BorderLayout.EAST);

                // Añadir el panel de resultados al resultadosPanel
                this.resultsPanel.add(resultsPanel1);
            }
        }

        // Refrescar el panel para asegurarse de que los cambios se muestran
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }




}
