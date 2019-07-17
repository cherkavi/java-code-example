/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazip.ZipManager;
/**
 *
 * @author cherkashinv
 */
public class JFrameMain extends JFrame{
    /** путь к файлу для архивирования */
    private JTextField field_path_to_file;
    /** путь к файлу для разархивирования */
    private JTextField field_path_to_zip;
    /** путь к каталогу, в который будут разархивированы файлы*/
    private JTextField field_path_to_directory;
    
    private void debug(String information){
        System.out.print(this.getClass().getName());
        System.out.print(" DEBUG ");
        System.out.println(information);
    }
    private void error(String information){
        System.out.print(this.getClass().getName());
        System.out.print(" ERROR ");
        System.out.println(information);
    }
    
    public JFrameMain(){
        super("ZIP example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initComponents();
        this.setSize(400,340);
        this.setVisible(true);
    }
    
    private void initComponents(){
        // create component's
        this.field_path_to_file=new JTextField();
        JButton field_open_path_to_file=new JButton("...");
        JButton field_path_to_file_process=new JButton("selected file to ZIP");
        
        this.field_path_to_zip=new JTextField();
        JButton field_open_path_to_zip=new JButton("...");
        JButton field_path_to_zip_process=new JButton("selected file UNZIP");
        this.field_path_to_directory=new JTextField();
        JButton field_open_path_to_directory=new JButton("...");

        
        // add listener's
        field_open_path_to_directory.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_click_archiv_directory();
            }
        });
        
        field_open_path_to_file.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_click_dialog_file();
            }
        });
        field_path_to_file_process.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_click_file_to_zip();
            }
        });
        
        field_open_path_to_zip.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_click_dialog_zip();
            }
        });
        
        field_path_to_zip_process.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                on_click_unzip_file();
            }
        });
        // placing component's
        JPanel panel_file=new JPanel();
        panel_file.setLayout(new BorderLayout());
        panel_file.setBorder(javax.swing.BorderFactory.createTitledBorder("Файл для архивирования"));
        panel_file.add(field_path_to_file,BorderLayout.CENTER);
        panel_file.add(field_open_path_to_file,BorderLayout.EAST);
        panel_file.add(field_path_to_file_process,BorderLayout.SOUTH);
        
        JPanel panel_zip=new JPanel();
        panel_zip.setLayout(new BorderLayout());
        panel_zip.setBorder(javax.swing.BorderFactory.createTitledBorder("Файл для разархивирования"));
        
        JPanel panel_zip_path=new JPanel();
        panel_zip_path.setBorder(javax.swing.BorderFactory.createTitledBorder("путь к Zip файлу"));
        panel_zip_path.setLayout(new BorderLayout());
        panel_zip_path.add(field_path_to_zip,BorderLayout.CENTER);
        panel_zip_path.add(field_open_path_to_zip,BorderLayout.EAST);
        JPanel panel_directory_path=new JPanel();
        panel_directory_path.setBorder(javax.swing.BorderFactory.createTitledBorder("путь к директории, в которую будут выгружены файлы"));
        panel_directory_path.setLayout(new BorderLayout());
        panel_directory_path.add(field_path_to_directory,BorderLayout.CENTER);
        panel_directory_path.add(field_open_path_to_directory,BorderLayout.EAST);

        panel_zip.add(panel_zip_path,BorderLayout.NORTH);
        panel_zip.add(panel_directory_path,BorderLayout.CENTER);
        panel_zip.add(field_path_to_zip_process,BorderLayout.SOUTH);

        JPanel panel_main=new JPanel(new GridLayout(2,1));
        panel_main.add(panel_file);
        panel_main.add(panel_zip);
        this.getContentPane().add(panel_main);
    }
    
    private void on_click_archiv_directory(){
        JFileChooser dialog=new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(dialog.showDialog(this, "File for ZIP")==JFileChooser.APPROVE_OPTION){
            this.field_path_to_directory.setText(dialog.getSelectedFile().getAbsolutePath());
        }
    }
    
    
    private void on_click_dialog_file(){
        JFileChooser file_chooser=new JFileChooser();
        if(file_chooser.showDialog(this, "File for ZIP")==JFileChooser.APPROVE_OPTION){
            this.field_path_to_file.setText(file_chooser.getSelectedFile().getAbsolutePath());
        }
    }
    
    private void on_click_dialog_zip(){
        JFileChooser file_chooser=new JFileChooser();
        file_chooser.setFileFilter(new FileNameExtensionFilter("Zip", "zip","Zip","ZIP"));
        if(file_chooser.showDialog(this, "ZIP for unzip")==JFileChooser.APPROVE_OPTION){
            this.field_path_to_zip.setText(file_chooser.getSelectedFile().getAbsolutePath());
        }
    }
    
    private void on_click_file_to_zip(){
        debug("file to zip");
        try{
            File file_source=new File(this.field_path_to_file.getText());
            String path_to_zip=file_source.getParent()+file_source.getName()+".zip";
            debug("Source:"+file_source.getAbsolutePath()+"             Desination:"+path_to_zip);
            ZipManager zip=new ZipManager();
            if(file_source.exists()){
                if(zip.fileToArchiv(file_source.getAbsolutePath(), path_to_zip)==true){
                    JOptionPane.showMessageDialog(this,"OK");
                }else{
                    JOptionPane.showMessageDialog(this, "Error");
                }
            }else{
                JOptionPane.showMessageDialog(this, "File not exists ");
            }
        }catch(Exception ex){
            error("on_click_file_to_zip Exception:"+ex.getMessage());
        }
    }
    private void on_click_unzip_file(){
        debug("unzip file");
        try{
            String path_to_file_zip=this.field_path_to_zip.getText();
            String path_to_unzip_directory=(this.field_path_to_directory.getText().trim().endsWith(System.getProperty("file.separator")))?this.field_path_to_directory.getText().trim():this.field_path_to_directory.getText().trim()+System.getProperty("file.separator");
            ZipManager zip=new ZipManager();
            if(zip.fileFromArchiv(path_to_file_zip, path_to_unzip_directory)){
                JOptionPane.showMessageDialog(this, "OK");
            }else{
                JOptionPane.showMessageDialog(this, "UnZip Error");
            }
        }catch(Exception ex){
            error("unzip Exception:"+ex.getMessage());
        }
    }
}
