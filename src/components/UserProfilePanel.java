package components;

/**
 *
 * @authors
 * name: DAVID ESCOBAR RUIZ
 * email: DAVIDESCOBARRUIZ30@GMAIL.COM
 *
 * name: ANDERSON LONDOÑO AVENDAÑO
 * email: AVENDANOANDERSON032@GMAIL.COM
 *
 */

import Controllers.ActualUser;
import Controllers.PostController;
import Controllers.ProfileController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.List;
import java.util.function.Consumer;

public class UserProfilePanel extends javax.swing.JFrame {
    private JFrame frame5;

    private Color PRIMARY_COLOR = new Color(0, 132, 137);
    private Color DELETE_COLOR = new Color(211, 47, 47);
    private Color BACKGROUND_COLOR = new Color(247, 247, 247);
    private Color TEXT_COLOR = new Color(34, 34, 34);
    private Font TITLE_FONT = new Font("Helvetica", Font.BOLD, 28);
    private Font SUBTITLE_FONT = new Font("Helvetica", Font.BOLD, 18);
    private Font REGULAR_FONT = new Font("Helvetica", Font.PLAIN, 14);

    //traer los hoteles mas recientes
    List<String[]> resultados ;

    private DefaultTableModel tableModel;

    public UserProfilePanel(String userName, String email) {
        setLayout(new BorderLayout(0, 0));
        setBackground(BACKGROUND_COLOR);
        setSize(1920, 1080);

        // Icon
        try {
            Image icono = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
            setIconImage(icono);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

        // Panel principal con margen
        JPanel mainPanel = new JPanel(new BorderLayout(30, 30));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setSize(1920,1080);

        // Panel superior
        JPanel headerPanel = createHeaderPanel(userName, email);

        // Panel de estadísticas
        JPanel statsPanel = createStatsPanel();

        // Panel de propiedades
        JPanel propertiesPanel = createPropertiesPanel();

        // Organizar paneles
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(propertiesPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel(String userName, String email) {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 10));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(createRoundedBorder());

        // Panel de información del usuario
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        userInfoPanel.setBackground(Color.WHITE);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel(userName);
        nameLabel.setFont(TITLE_FONT);
        nameLabel.setForeground(TEXT_COLOR);

        JLabel emailLabel = new JLabel(email);
        emailLabel.setFont(REGULAR_FONT);
        emailLabel.setForeground(Color.GRAY);

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(emailLabel);

        headerPanel.add(userInfoPanel, BorderLayout.CENTER);

        // Botón de editar perfil
        JButton editButton = createStyledButton("Editar Perfil", PRIMARY_COLOR);
        editButton.setPreferredSize(new Dimension(150, 40));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(editButton);

        // Botón de Buscar hotel
        JButton buscarButton = createStyledButton("Buscar hoteles", PRIMARY_COLOR);
        buscarButton.setPreferredSize(new Dimension(150, 40));
        JPanel buttonBuscarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonBuscarPanel.setBackground(Color.WHITE);
        buttonBuscarPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(buscarButton);


        // accion a realizar cuando se de click en el boton de buscar hoteles
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search search = new Search();
                search.setVisible(true);
                search.getAllHotels();
            }
        });

        headerPanel.add(buttonPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        statsPanel.setBackground(BACKGROUND_COLOR);

        // Crear tarjetas de estadísticas
        statsPanel.add(createStatCard("Propiedades Publicadas", "8"));
        statsPanel.add(createStatCard("Reservas Totales", "24"));
        statsPanel.add(createStatCard("Valoración Promedio", "4.95"));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new GridLayout(2, 1, 20, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(createRoundedBorder());
        card.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(TITLE_FONT);
        valueLabel.setForeground(PRIMARY_COLOR);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(REGULAR_FONT);
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(valueLabel);
        card.add(titleLabel);

        return card;
    }

    private JPanel createPropertiesPanel() {
        JPanel propertiesPanel = new JPanel(new BorderLayout(0, 20));
        propertiesPanel.setBackground(BACKGROUND_COLOR);

        // Título y botón de agregar
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Mis Propiedades");
        titleLabel.setFont(SUBTITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        // Add Button
        JButton addButton = createStyledButton("Agregar Propiedad", PRIMARY_COLOR);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreatePost createPost = new CreatePost();
                createPost.setVisible(true);
            }
        });
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        // Tabla de propiedades con botones de acción
        String[] columns = {"Propiedad", "Ubicación","Descripcion", "Precio/noche", "Estado", "Acciones"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo la columna de acciones es editable
            }
        };


        // Agregar datos de ejemplo



        ProfileController pfc = new ProfileController();
        resultados = pfc.hotelesRecientes(ActualUser.idUser);

        // Limpiar el panel antes de mostrar nuevos resultados

        // si la lista de resultados de búsqueda está vacía, se mostrará el siguiente mensaje
        if (resultados == null || resultados.isEmpty()) {

            // Crear un JLabel para el mensaje de "Sin resultados"

            JLabel sinResultadosLabel = new JLabel("Sin Publicaciones :(");


        } else {
            for (String[] post2 : resultados) {
                addPropertyRow(post2[1]+"",post2[4]+"",post2[4]+"", post2[3]+" COP", "Disponible");

                // addPropertyRow("Apartamento en el Centro", "Bogotá, Colombia", "$120.000 COP", "Ocupado");
            }


    }



        JTable propertiesTable = new JTable(tableModel);
        propertiesTable.setRowHeight(50);
        propertiesTable.setFont(REGULAR_FONT);
        propertiesTable.getTableHeader().setFont(REGULAR_FONT);
        propertiesTable.getTableHeader().setBackground(Color.WHITE);
        propertiesTable.setGridColor(new Color(230, 230, 230));

        // Configurar renderer para la columna de acciones
        propertiesTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        propertiesTable.getColumnModel().getColumn(5).setCellEditor(
                new ButtonEditor(new JCheckBox(), this::deleteProperty));

        // Ajustar anchos de columnas
        propertiesTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        propertiesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        propertiesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        propertiesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        propertiesTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(propertiesTable);
        scrollPane.setBorder(createRoundedBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        propertiesPanel.add(headerPanel, BorderLayout.NORTH);
        propertiesPanel.add(scrollPane, BorderLayout.CENTER);

        return propertiesPanel;
    }

    private void addPropertyRow(String property, String location,String description, String price, String status) {
        tableModel.addRow(new Object[]{property, location,description, price,status});
    }



    private void deleteProperty(int row) {
        if (row >= 0) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro que deseas eliminar esta propiedad?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                tableModel.removeRow(row);
            }
        }
    }

    // Renderer personalizado para los botones
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(REGULAR_FONT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Eliminar");
            setBackground(DELETE_COLOR);
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }

    // Editor personalizado para los botones
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private Consumer<Integer> clickAction;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, Consumer<Integer> clickAction) {
            super(checkBox);
            this.clickAction = clickAction;
            button = new JButton();
            button.setOpaque(true);
            button.setFont(REGULAR_FONT);
            button.setBackground(DELETE_COLOR);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            currentRow = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                clickAction.accept(currentRow);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private Border createRoundedBorder() {
        return BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true)
        );
    }
}