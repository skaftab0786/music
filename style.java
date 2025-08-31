public class MusicPlayer extends JFrame {
    private JButton playButton, pauseButton, loadButton, toggleThemeButton;
    private JLabel songLabel;
    private JPanel panel;
    private boolean isDarkMode = false;
    private Player player;
    private Thread playThread;
    private File currentFile;

    public MusicPlayer() {
        setTitle("Java Music Player");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(Color.WHITE);

        songLabel = new JLabel("🎵 No song loaded", SwingConstants.CENTER);
        panel.add(songLabel);

        loadButton = new JButton("📁 Load MP3");
        playButton = new JButton("▶️ Play");
        pauseButton = new JButton("⏸️ Pause");
        toggleThemeButton = new JButton("🌗 Toggle Theme");

        panel.add(loadButton);
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(toggleThemeButton);

        add(panel);

        loadButton.addActionListener(e -> loadSong());
        playButton.addActionListener(e -> playSong());
        pauseButton.addActionListener(e -> stopSong());
        toggleThemeButton.addActionListener(e -> toggleTheme());

        setVisible(true);
    }

    private void loadSong() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            songLabel.setText("🎵 " + currentFile.getName());
        }
    }

    private void playSong() {
        if (currentFile == null) return;
        stopSong(); // Stop previous playback
        playThread = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(currentFile);
                player = new Player(fis);
                player.play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        playThread.start();
    }

    private void stopSong() {
        if (player != null) {
            player.close();
        }
        if (playThread != null) {
            playThread.interrupt();
        }
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        panel.setBackground(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
        songLabel.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicPlayer::new);
    }
}
