package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class exercicio1 extends SimpleApplication {

    public static void main(String[] args) {
        exercicio1 app = new exercicio1();
        app.start();
    }

    @Override
    public void simpleInitApp() { DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        
        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja.setName("NINJA");
        ninja.scale(0.03f);
        ninja.rotate(0,FastMath.PI,0);
        rootNode.attachChild(ninja);
        
        Spatial Oto = assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        Oto.setLocalTranslation(5f,3f,2f);
        Oto.scale(0.5f);
        Oto.rotate(0,FastMath.PI,0);
        rootNode.attachChild(Oto);
        
        Spatial elephant = assetManager.loadModel("Models/Elephant/Elephant.mesh.xml");
        elephant.setLocalTranslation(-6f,0f,2f);
        elephant.scale(0.05f);
        elephant.rotate(0,FastMath.PI,0);
        rootNode.attachChild(elephant);
        
        
        Spatial ferrari = assetManager.loadModel("Models/Sinbad/Sinbad.mesh.xml");
        ferrari.setLocalTranslation(-9f,0f,2f);
        ferrari.scale(0.5f);
        ferrari.rotate(0,FastMath.PI,0);
        rootNode.attachChild(ferrari);
        
        
        Spatial tree = assetManager.loadModel("Models/Tree/Tree.mesh.xml");
        tree.setLocalTranslation(9f,0f,2f);
        tree.scale(0.5f);
        tree.rotate(0,FastMath.PI,0);
        rootNode.attachChild(tree);
        
        
        ArrayList<Spatial> ninjas = new ArrayList<Spatial>();
        int j = 0 ;
        
            for (;j<100;j++){
            int b = j/10;
                
                Spatial nin = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
                System.out.println(b);
                nin.scale(0.02f);
                nin.setLocalTranslation((float)(j/10),-3f,(float)(j%10));
                nin.rotate(0,FastMath.PI,0);
                nin.setName("ninja"+b+j);
                ninjas.add(nin);
            }
        
        
        for(Spatial s : ninjas)
        {
            rootNode.attachChild(s);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
//TODO: add update code
        for (int i = 0; i < 5; i++) {
            rootNode.getChildren().get(i).rotate(0f,tpf,0f);
        }

        int i=0;
        int j=0;
        for (;i<100;i++){
            j= i/10;
            Spatial s = rootNode.getChild("ninja"+j+i);
            s.move(0f,0f,tpf);
            
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
