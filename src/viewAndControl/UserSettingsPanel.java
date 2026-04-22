package viewAndControl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import data.UserDatabase;
import model.User;

public class UserSettingsPanel extends JPanel {
    private final MainFrame mainFrame;
    private final User currentUser;
    private final UserDatabase userDatabase;
    private JButton uploadPictureButton;
    private JLabel picturePreview;

    public UserSettingsPanel(User currentUser, MainFrame mainFrame, UserDatabase userDatabase) {
        this.mainFrame = mainFrame;
        this.currentUser=currentUser;
        this.userDatabase=userDatabase;

        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);

        //Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(GUIColors.DARK);
        headerPanel.setPreferredSize(new Dimension(50, 80));
        headerPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(GUIColors.LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        //Username display
        RoundedPanel userCard = new RoundedPanel(10, GUIColors.CREAM);
        userCard.setLayout(new BorderLayout());
        userCard.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel userLabel = new JLabel("Logged in as: " + currentUser.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIColors.DARK);
        userCard.add(userLabel, BorderLayout.WEST);

        headerPanel.add(userCard, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground((GUIColors.MID));
        contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        //make profile section on display
        JPanel profileSection=makeProfilePictureSection();
        contentPanel.add(profileSection, BorderLayout.NORTH);

        //display ManageUserButton only if user is model.Admin
        RoundedButton manageUsersButton = new RoundedButton("Manage Users", 150, 50);
        manageUsersButton.addActionListener(event ->
                mainFrame.navigateManageUsers()
        );

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRight.setBackground(GUIColors.MID);
        //bottomRight.add(logoutButton);

        //display manageUsersButton
        if (currentUser.getIsAdmin()) {
            bottomRight.add(manageUsersButton);
        }

        contentPanel.add(bottomRight, BorderLayout.SOUTH);

        this.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel makeProfilePictureSection()
    {
        JPanel picturePanel=new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        picturePanel.setBackground(GUIColors.MID);

        //picture preview
        picturePreview=new JLabel("No Picture");
        picturePreview.setPreferredSize(new Dimension(120,120));
        picturePreview.setBorder(BorderFactory.createLineBorder(GUIColors.DARK,2));
        picturePreview.setHorizontalAlignment(SwingConstants.CENTER);
        picturePreview.setOpaque(true);
        picturePreview.setBackground(GUIColors.LIGHT);

        //make picture upload button
        uploadPictureButton = new JButton("Upload Picture");
        uploadPictureButton.addActionListener(event ->
                handlePictureUpload());

        picturePanel.add(picturePreview);
        picturePanel.add(uploadPictureButton);

        loadCurrentPicture();
        return picturePanel;
    }

    private void handlePictureUpload() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files (*.jpg, *.png, *.pdf)", "jpg", "jpeg", "png", "pdf");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();

            try {
                //create pictures directory
                File picturesDirectory = new File("profile_pictures");
                if (!picturesDirectory.exists()) {
                    picturesDirectory.mkdir();
                }
                //generate filename from username
                String extension = getFileExtension(selected);
                String newFileName = currentUser.getUsername() + extension;
                File destinationFile = new File(picturesDirectory, newFileName);
                //copy file
                Files.copy(selected.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                //update user object
                currentUser.setProfilePicturePath(destinationFile.getPath());

                //save to database
                userDatabase.saveUsers();

                //update picture
                loadCurrentPicture();
                mainFrame.refreshSidePanel();

                JOptionPane.showMessageDialog(this,
                        "Profile picture uploaded successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error uploading picture: " + ex.getMessage(),
                        "Upload Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private String getFileExtension(File file)
    {
        String fileName=file.getName();
        int lastDot=fileName.lastIndexOf('.');
        if (lastDot>0&&lastDot<fileName.length()-1)
        {
            return fileName.substring(lastDot); //get file extension type
        }
        return ".jpg"; //default extension
    }

    private void loadCurrentPicture()
    {
        String picturePath=currentUser.getProfilePicturePath();

        //check if user has picture
        if (picturePath !=null && !picturePath.isEmpty())
        {
            File pictureFile=new File(picturePath);
            //check if file exists on disk
            if (pictureFile.exists())
            {
                try
                {
                    //load image
                    ImageIcon icon = new ImageIcon(picturePath);
                    Image image=icon.getImage();

                    //scale to fit preview
                    Image scaledImage=image.getScaledInstance(120,120,Image.SCALE_SMOOTH);
                    picturePreview.setIcon(new ImageIcon(scaledImage));
                    picturePreview.setText("");
                    return;
                }
                catch(Exception e)
                {
                    System.err.println("Error loading picture: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        // Show placeholder if no picture or error occurred
        picturePreview.setIcon(null);
        picturePreview.setText("No Picture");
    }
}

