/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Main;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import project2.Engine.Viewport;
import project2.Model.JsonProject;
import project2.Model.JsonScene;

/**
 *
 * @author chico
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        

        try
        {
            
            
            FileReader reader = new FileReader(new File("data.json"));
            Gson gson = new Gson();
            JsonProject project = (JsonProject)gson.fromJson(reader, JsonProject.class);
            
            System.out.println(project.getScenes().get(1).getSceneName());
            String filename = project.getScenes().get(1).getSceneName();
            reader = new FileReader(new File(filename + ".json"));
            
            JsonScene scene = (JsonScene)gson.fromJson(reader, JsonScene.class);
            
            project.getScenes().get(1).setComposite(scene.getComposite());
            System.out.println(project.getScenes().get(1).getComposite().getsImages().size());
            
            System.out.println(project.getScenes().get(1).getComposite().getLayers().size());
            System.out.println(project.getScenes().get(1).getComposite().getsImages().get(190).getImageName());
            System.out.println(project.getScenes().get(1).getComposite().getsImages().get(190).getX());
            System.out.println(project.getScenes().get(1).getComposite().getsImages().get(190).getzIndex());
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        



//        Clip Sonido = AudioSystem.getClip();
//        Sonido.open(AudioSystem.getAudioInputStream(new File("SpaceShooterSound.wav")));
//        Sonido.start();


//        Stage stage = new Stage();
//        GameThread game = new MiJuego(stage);
//        game.run();

//        JsonReader.readJson();
        
        
    }
    
}
