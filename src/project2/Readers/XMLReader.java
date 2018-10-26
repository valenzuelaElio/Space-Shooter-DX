/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Readers;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import project2.Engine.Viewport;
import project2.Proyecto1.Animation;
import project2.Proyecto1.Background;
import project2.Proyecto1.Enemy;
import project2.Proyecto1.Frame;
import project2.Proyecto1.Imagen;
import project2.Proyecto1.Layer;
import project2.Proyecto1.Location;
import project2.Proyecto1.Player;
import project2.Proyecto1.Utils.AnimationType;
import project2.Proyecto1.Utils.KeyUtils;
import project2.Proyecto1.Utils.ModeType;
import project2.Stage.Stage;
import project2.Stage.StageElement;

/**
 *
 * @author chico
 */
public class XMLReader {
    static Element root;
    
    static public void readXML(Stage stage){
        
        
        try{
            File inputFile = new File("XMLTest.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(inputFile);
            document.getDocumentElement().normalize();
            
            root = document.getDocumentElement();
            
            loadStageDimensions( stage, root );
            loadStageViewport( stage, root, "Ventana" );
            loadImagens( stage, root, "Imagen" );
            loadAnimation( stage, root, "Animacion");
            loadLayers(stage, root, "Capa");
            loadPlayer(stage, root, "Jugador");
            loadEnemies(stage, root, "Enemigo");
            
            //loadTriggers(stage, root, "Activador");
            
            loadLocations(stage, root, "Ubicacion");
            
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("No se pudo leer el archivo");
        }
        
        
    }
    
    public static Boolean booleanAssitant(String attribute){
        if(attribute.equals("si") || attribute.equals("Si") ){
            return true;
        }else{
            return false;
        }
    }
    
    private static void loadStageDimensions(Stage stage, Element element){
        int width = Integer.parseInt(element.getAttribute("ancho"));
        int height = Integer.parseInt(element.getAttribute("alto"));
        stage.setStageDimension(width, height);
    }

    private static void loadStageViewport(Stage stage, Element root, String tag) {
        NodeList nodeList = root.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        Element element = (Element)node;
        stage.setViewport( new Viewport(
                    Float.parseFloat(element.getAttribute("x")),
                    Float.parseFloat(element.getAttribute("y")),
                    Float.parseFloat(element.getAttribute("ancho")),
                    Float.parseFloat(element.getAttribute("alto")) ) );
    }

    private static void loadImagens(Stage stage, Element root, String tag) {
        NodeList nodeList = root.getElementsByTagName(tag);
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            Imagen imagen = new Imagen(
                            element.getAttribute("id"),
                            element.getAttribute("ruta"), 
                            Integer.parseInt(element.getAttribute("ancho")),
                            Integer.parseInt(element.getAttribute("alto")) ) ;
            stage.addImagen(imagen);
        }
    }

    private static void loadAnimation(Stage stage, Element root, String tag) {
        NodeList nodeList = root.getElementsByTagName(tag);
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            Animation animation = new Animation(element.getAttribute("id"));
            LoadFrame(element, animation, "Fotograma");
            stage.addAnimation(animation);
        }
    }
    
    private static void LoadFrame(Element root, Animation animation, String tag){
        NodeList nodeList = root.getElementsByTagName(tag);
        for(int j = 0; j < nodeList.getLength(); j++){
            Node node = nodeList.item(j);
            Element element = (Element)node;
            Frame frame = new Frame( 
                    element.getAttribute("id-imagen"),
                    Long.parseLong(element.getAttribute("tiempo")) );
            animation.addFrame(frame);
        }
    }

    private static void loadLayers(Stage stage, Element root, String tag) {
        NodeList nodeList = root.getElementsByTagName(tag);
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            Layer layer = new Layer( XMLReader.booleanAssitant(element.getAttribute("principal")),
                    Float.parseFloat(element.getAttribute("velocidad")),
                    Integer.parseInt(element.getAttribute("z")));
            LoadBackground("Fondo", layer, element);
            stage.addLayer(layer);
        }
    }

    private static void LoadBackground(String tag, Layer layer, Element root) {
        boolean repeatX; boolean repeatY;  
        NodeList nodeList = root.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        Element element = (Element)node;
        if(element != null){
            repeatX = booleanAssitant(element.getAttribute("repeticion-x"));
            repeatY = booleanAssitant(element.getAttribute("repeticion-y"));
            layer.setBackground(new Background(element.getAttribute("id-imagen"),repeatX, repeatY )); 
        }
    }
    
    private static void loadPlayer(Stage stage, Element root, String tag){
        NodeList nodeList = root.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        Element element = (Element)node;
        Player player = new Player(tag);
        loadPlayerAnimations(stage, element, player);
        stage.setPlayer(player);
        stage.addStageElement(player);
    }
    
    private static void loadPlayerAnimations(Stage stage, Element root, Player player){
        ModeType[] modes = ModeType.values();
        AnimationType[] animations = AnimationType.values();
        
        ModeType mType = null;
        AnimationType aType = null;
        //System.out.println("animations length: " +  (animations.length - 1));
        //System.out.println("modes lenght: " + (modes.length - 1));
        
        for(int i = 0; i < modes.length; i++){
            switch(i){
                case 0: mType = ModeType.NORMAL; break;
                case 1: mType = ModeType.FANTASMA; break; 
                case 2: mType = ModeType.INVENCIBLE; break;
            }
            //System.out.println("count : " + i);
            for(int j = 0; j < animations.length ;j++){
                switch(j){
                    case 0: aType = AnimationType.QUIETO; break;
                    case 1: aType = AnimationType.AVANZAR; break;
                    case 2: aType = AnimationType.RETROCEDER; break;
                    case 3: aType = AnimationType.SALTO; break;
                    case 4: aType = AnimationType.DISPARO; break;
                }
                String key = KeyUtils.createKey(mType, aType);
                
                NodeList nodeList = root.getElementsByTagName(key);
                //System.out.println("nodeList length : " + nodeList.getLength());
                if(nodeList.getLength() != 0){
                    Node node = nodeList.item(0);    
                    Element element = (Element)node;
                    String id = element.getAttribute("id-animacion");
                    Animation animation = stage.getAnimationByid(id);
                    player.addAnimation(key, animation);

                }
            }
            
        }
    }
    
    private static void loadEnemies(Stage stage, Element root, String tag){
        NodeList nodeList = root.getElementsByTagName(tag);
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            
            String id = element.getAttribute("id");
            Animation right = getAnimationToEnemy(stage , element , "Derecha");
            Animation left  = getAnimationToEnemy(stage , element , "Izquierda");
            Enemy enemy = new Enemy(id , right , left);
            stage.addEnemy(enemy);
            stage.addStageElement(enemy);
            
        }
        
    }
    
    private static Animation getAnimationToEnemy(Stage stage, Element root, String tag){
        NodeList nodeList = root.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        Element element = (Element)node;
        Animation animation = stage.getAnimationByid(element.getAttribute("id-animacion"));
        return animation;
        
    }
    
    private static void loadLocations(Stage stage, Element root, String tag){
        NodeList nodeList = root.getElementsByTagName(tag);
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = (Element)node;
            
            StageElement stageElement = stage.getStageElementById(element.getAttribute("id-personaje"));
            
            int x = Integer.parseInt(element.getAttribute("x"));
            int y = Integer.parseInt(element.getAttribute("y"));
            Location location = new Location(x,y);
            
            stageElement.setLocation(location);
            
            
            
            
        }
    } 
    
}
