package componentes;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.text.NumberFormat;
import java.util.Locale;

public class PropiedadFormulario extends javax.swing.JFrame {

    private JFrame frame4;
    private JPanel mainPanel, imagePanel, infoPanel, bookingPanel;
    private JLabel titleLabel, locationLabel, priceLabel, ratingLabel;
    private JButton reserveButton, favoriteButton, shareButton;
    private Color mainColor = new Color(14, 98, 81);
    private Font titleFont = new Font("Arial", Font.BOLD, 26);
    private Font subtitleFont = new Font("Arial", Font.BOLD, 18);
    private Font normalFont = new Font("Arial", Font.PLAIN, 14);

    public PropiedadFormulario() {
        frame4 = new JFrame();


        setTitle("Detalles del Alojamiento");

        setSize(1920, 1080);

        // icono
        try{
            Image icono = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
            setIconImage(icono);
        }catch (Exception e){
            System.out.println("Error"+ e);
        }


        // Panel principal con scroll
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Header
        JPanel headerPanel = createHeaderPanel();

        // Panel de imágenes
        imagePanel = createImageGalleryPanel();

        // Panel de información principal
        infoPanel = createInfoPanel();

        // Panel de reserva (flotante a la derecha)
        bookingPanel = createBookingPanel();

        // Scroll para el contenido principal
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(imagePanel, BorderLayout.NORTH);

        // Panel para info y booking
        JPanel infoBookingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        infoBookingPanel.add(infoPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        infoBookingPanel.add(bookingPanel, gbc);

        contentPanel.add(infoBookingPanel, BorderLayout.CENTER);

        // Agregar todo al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Scroll pane principal
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Título principal
        titleLabel = new JLabel("Casa de Playa con Vista al Mar");
        titleLabel.setFont(titleFont);

        // Panel superior con rating y botones
        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(Color.WHITE);

        // Rating y ubicación
        JPanel leftInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftInfo.setBackground(Color.WHITE);
        ratingLabel = new JLabel("★ 4.95 · 123 reseñas");
        locationLabel = new JLabel(" · Cancún, Quintana Roo, México");
        leftInfo.add(ratingLabel);
        leftInfo.add(locationLabel);

        // Botones de compartir y guardar
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtons.setBackground(Color.WHITE);
        shareButton = createIconButton("Compartir");
        favoriteButton = createIconButton("♥");
        rightButtons.add(shareButton);
        rightButtons.add(favoriteButton);

        topHeader.add(leftInfo, BorderLayout.WEST);
        topHeader.add(rightButtons, BorderLayout.EAST);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(topHeader, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createImageGalleryPanel() {
        JPanel gallery = new JPanel(new GridBagLayout());
        gallery.setBackground(Color.WHITE);

        // Crear un borde redondeado
        int radius = 12;
        gallery.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Simular 5 imágenes con paneles coloreados
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Imagen principal grande
        JPanel mainImage = createImagePanel(400, Color.LIGHT_GRAY, radius);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gallery.add(mainImage, gbc);

        // Imágenes pequeñas a la derecha
        for (int i = 0; i < 4; i++) {
            JPanel smallImage = createImagePanel(195, new Color(220, 220, 220), radius);
            gbc.gridx = 1 + (i % 2);
            gbc.gridy = i / 2;
            gbc.gridheight = 1;
            gbc.weightx = 0.25;
            gbc.weighty = 0.5;
            gallery.add(smallImage, gbc);
        }

        return gallery;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        // Información del anfitrión
        JPanel hostInfo = new JPanel(new BorderLayout());
        hostInfo.setBackground(Color.WHITE);
        JLabel hostLabel = new JLabel("Anfitrión: María");
        hostLabel.setFont(subtitleFont);
        hostInfo.add(hostLabel, BorderLayout.WEST);

        // Características principales
        JPanel features = new JPanel(new FlowLayout(FlowLayout.LEFT));
        features.setBackground(Color.WHITE);
        features.add(new JLabel("4 huéspedes · "));
        features.add(new JLabel("2 habitaciones · "));
        features.add(new JLabel("2 camas · "));
        features.add(new JLabel("2 baños"));

        // Servicios destacados
        JPanel highlights = createHighlightsPanel();

        // Descripción
        JTextArea description = new JTextArea(
                "Disfruta de esta hermosa casa de playa con impresionantes vistas al mar. " +
                        "Perfecta para unas vacaciones relajantes con acceso directo a la playa. " +
                        "La casa cuenta con todas las comodidades modernas y está decorada con un " +
                        "estilo contemporáneo que complementa su entorno natural."
        );
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setBackground(Color.WHITE);
        description.setFont(normalFont);

        // Agregar todos los elementos
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(hostInfo);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(features);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(highlights);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(description);

        return infoPanel;
    }

    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));
        bookingPanel.setBackground(Color.WHITE);
        bookingPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(221, 221, 221), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Precio
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.setBackground(Color.WHITE);
        JLabel price = new JLabel("$150.000 COP");
        price.setFont(titleFont);
        JLabel perNight = new JLabel(" noche");
        pricePanel.add(price);
        pricePanel.add(perNight);

        // Fechas
        JPanel datesPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        datesPanel.setBackground(Color.WHITE);

        JTextField checkIn = createDateField("Llegada"); // HACER FUNCIONAL LA OPCION DE FECHA


        JTextField checkOut = createDateField("Salida"); // HACER FUNCIONAL LA OPCION DE FECHA
        datesPanel.add(checkIn);
        datesPanel.add(checkOut);

        // Huéspedes
        JTextField guests = createTextField("Huéspedes");

        // Botón de reserva
        reserveButton = new JButton("Reservar");
        reserveButton.setForeground(Color.white);
        reserveButton.setBackground(mainColor);
        reserveButton.setFont(new Font("Arial", Font.BOLD, 16));
        reserveButton.setPreferredSize(new Dimension(200, 50));

        // Total
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(Color.WHITE);
        totalPanel.add(new JLabel("No se hará ningún cargo por ahora"), BorderLayout.CENTER);


        // Agregar todo al panel


        bookingPanel.add(pricePanel);
        bookingPanel.add(Box.createVerticalStrut(20));
        bookingPanel.add(datesPanel);
        bookingPanel.add(Box.createVerticalStrut(10));
        bookingPanel.add(guests);
        bookingPanel.add(Box.createVerticalStrut(20));
        bookingPanel.add(reserveButton);
        bookingPanel.add(Box.createVerticalStrut(20));
        bookingPanel.add(totalPanel);

        return bookingPanel;
    }

    private JPanel createHighlightsPanel() {
        JPanel highlights = new JPanel(new GridLayout(0, 2, 20, 10));
        highlights.setBackground(Color.WHITE);

        addHighlight(highlights, "🏊‍♂", "Piscina");
        addHighlight(highlights, "🌊", "Vista al mar");
        addHighlight(highlights, "📶", "Wifi de alta velocidad");
        addHighlight(highlights, "❄", "Aire acondicionado");
        addHighlight(highlights, "🚗", "Estacionamiento gratuito");
        addHighlight(highlights, "🔒", "Entrada independiente");

        return highlights;
    }

    private void addHighlight(JPanel panel, String emoji, String text) {
        JPanel highlightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        highlightPanel.setBackground(Color.WHITE);
        highlightPanel.add(new JLabel(emoji));
        highlightPanel.add(new JLabel(text));
        panel.add(highlightPanel);
    }

    private JButton createIconButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private JTextField createDateField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setPreferredSize(new Dimension(200, 40));
        return field;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setPreferredSize(new Dimension(200, 40));
        return field;
    }

    private JPanel createImagePanel(int size, Color color, int radius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                }
                g.setColor(color);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            }
        };
        panel.setPreferredSize(new Dimension(size, size));
        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            PropiedadFormulario frame = new PropiedadFormulario();
            frame.setVisible(true);
        });
    }
}