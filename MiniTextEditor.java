import java.io.*;
import javax.swing.*; //JFrame
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MiniTextEditor{
    public static void main(String []args){
        /*TextEditor te=*/new TextEditor();
    }
}

class TextEditor implements ActionListener{
    //Properties
    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit;
    JMenuItem newFile, openFile, saveFile, cut, copy, paste, selectAll, close;
    JTextArea textArea;
    //Constructor 
    TextEditor(){
        frame=new JFrame();
        frame.setResizable(false);
        menuBar=new JMenuBar();
        textArea=new JTextArea();
        /*
        1st create menuItems(cut,copy,...) then add these to menu(file, edit), then add these menus to menuBar after which attach 
        menuBar to Jframe.
        */
        file=new JMenu("File");
        edit=new JMenu("Edit");
        
        newFile=new JMenuItem("New Window");
        openFile=new JMenuItem("Open File");
        saveFile=new JMenuItem("Save File");
        cut=new JMenuItem("Cut");
        paste=new JMenuItem("Paste");
        copy=new JMenuItem("Copy");
        selectAll=new JMenuItem("Select All");
        close=new JMenuItem("Close");
        /*
        After creating menu items and before adding them to menus, we need to link them to action ActionListener
        Only by linking them, action listner will keep an eye on those menu items for any actionEvent(Pressing button, selectionoption, check/uncheck of box, ....).
        */
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        copy.addActionListener(this);
        cut.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        close.addActionListener(this);
        
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(close);
        
        menuBar.add(file);
        menuBar.add(edit);
        
        frame.setJMenuBar(menuBar); //attaching menuBar to frame. IDK why it must be done before seting bounds for frame
        /*
         * Since we need scrolling option for our text area part, we will not add text area directly to our frame.
         * 1st - Add pannel to frame
         * 2nd - Add scrollpane to pannel(it is like a container similar to frame)
         * 3rd - Give borders to our text area
         * 4th - Add text area to scrollpane.
         */
        //frame.add(textArea); //adding text area for frame
        JPanel panel=new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(new BorderLayout(0,0));
        panel.add(textArea, BorderLayout.CENTER);
        JScrollPane scrollPane=new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);
        frame.add(panel);
        
        frame.setBounds(100,100,400,400);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setTitle("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(actionEvent.getSource()==cut){
            textArea.cut();
        }
        if(actionEvent.getSource()==copy){
            textArea.copy();
        }
        if(actionEvent.getSource()==paste){
            textArea.paste();
        }
        if(actionEvent.getSource()==selectAll){
            textArea.selectAll();
        }
        if(actionEvent.getSource()==close){
            System.exit(0);//status 0 means we have close the appication
        }
        if(actionEvent.getSource()==openFile){
            //1st step is to open file chooser
            JFileChooser fileChooser=new JFileChooser("C:");
            //in flie chooser, one can select open button or cancle button
            int optionChoosen=fileChooser.showOpenDialog(null);
            if(optionChoosen==JFileChooser.APPROVE_OPTION){ //if open button is choosed
                File optedFile=fileChooser.getSelectedFile();
                String pathOfOptedFile=optedFile.getPath();
                try{
                    FileReader fileReader=new FileReader(pathOfOptedFile);
                    BufferedReader br=new BufferedReader(fileReader);
                    String currLine="";
                    String outputString="";
                    while((currLine=br.readLine())!=null){
                        outputString+=currLine+"\n"; //reading contents of choosen file
                    }
                    br.close();
                    textArea.setText(outputString); //copying contents into out file into textArea
                }
                catch(FileNotFoundException fnfe){
                    fnfe.printStackTrace();
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        if(actionEvent.getSource()==saveFile){
            JFileChooser fileChooser =new JFileChooser("C:");
            int optionChoosen=fileChooser.showSaveDialog(null);
            if(optionChoosen==JFileChooser.APPROVE_OPTION){
                File file=new File(fileChooser.getSelectedFile().getAbsolutePath()+".txt");
                try{
                    FileWriter fw=new FileWriter(file);
                    BufferedWriter bw=new BufferedWriter(fw);
                    textArea.write(bw);
                    bw.close();
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        if(actionEvent.getSource()==newFile){
            /*TextEditor newTE=*/new TextEditor();
        }
    }
}