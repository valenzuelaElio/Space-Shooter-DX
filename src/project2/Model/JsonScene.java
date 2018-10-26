/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Model;

/**
 *
 * @author chico
 */
public class JsonScene {
    
    private String sceneName;
    Object physicsPropertiesVO;
    private JsonComposite composite;

    public JsonScene() {
        
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public Object getPhysicsPropertiesVO() {
        return physicsPropertiesVO;
    }

    public void setPhysicsPropertiesVO(Object physicsPropertiesVO) {
        this.physicsPropertiesVO = physicsPropertiesVO;
    }

    public JsonComposite getComposite() {
        return composite;
    }

    public void setComposite(JsonComposite composite) {
        this.composite = composite;
    }
    
    
    
}
