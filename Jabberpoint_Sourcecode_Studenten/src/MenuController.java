import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

    protected static final String ABOUT = "About";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String HELP = "Help";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PAGENR = "Page number?";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";
    protected static final String VIEW = "View";
    protected static final String TESTFILE = "../testPresentation.xml";
    protected static final String SAVEFILE = "savedPresentation.xml";
    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";
    private static final long serialVersionUID = 227L;
    private Frame parent; //The frame, only used as parent for the Dialogs
    private Presentation presentation; //Commands are given to the presentation

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                Accessor xmlAccessor = new XMLAccessor();
                try {
                    xmlAccessor.loadFile(presentation, TESTFILE);
                    presentation.setSlideNumber(0);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc,
                            LOADERR, JOptionPane.ERROR_MESSAGE);
                }
                parent.repaint();
            }
        });
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                parent.repaint();
            }
        });
        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Accessor xmlAccessor = new XMLAccessor();
                try {
                    xmlAccessor.saveFile(presentation, SAVEFILE);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc,
                            SAVEERR, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.exit(0);
            }
        });
        add(fileMenu);
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.nextSlide();
            }
        });
        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.prevSlide();
            }
        });
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
                Integer slideNr = presentation.getCurrentSlideNumber() + 1;
                try {
                    int pageNumber = Integer.parseInt(pageNumberStr);
                    if (pageNumber <= 0) {
                        System.out.println("Slide range is out of bounds. Number of slides: " + slideNr + " of " + presentation.getSize());
                    } else if (pageNumber > presentation.getSize()) {
                        System.out.println("Slide range is out of bounds. Number of slides: " + slideNr + " of " + presentation.getSize());
                    } else {
                        presentation.setSlideNumber(pageNumber - 1);
                    }
                } catch (Exception e) {
                    System.out.println("No input was detected");
                }
            }
        });
        add(viewMenu);
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(parent,
                        "JabberPoint is a primitive slide-show program in Java(tm). It\n" +
                                "is freely copyable as long as you keep this notice and\n" +
                                "the splash screen intact.\n" +
                                "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
                                "Adapted by Gert Florijn (version 1.1) and " +
                                "Sylvia Stuurman (version 1.2 and higher) for the Open" +
                                "University of the Netherlands, 2002 -- now.\n" +
                                "Author's version available from http://www.darwinsys.com/",
                        "About JabberPoint",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
    }

    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
