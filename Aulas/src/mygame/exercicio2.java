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


import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import java.util.List;


/*

Criar um projeto que execute as seguintes ações:

OK - Criar um chão/piso para os ninjas usando um cubo. Aplicar uma textura ao cubo.

Ao teclar G ou o Click do Mouse- > Criar um novo ninja em -X para +X. Ao criar um novo ninja esse começa a mover de -X para + X. Se o ninja chegar em ponto específico qualquer da cena, ele deverá ser removido. Marcar esse ponto na cena com um Cubo.

Ao teclar P -> Para todas as animações da aplicação.

Ao teclar N -> Remove todos os ninjas da cena.

Ao teclas H ->  Remove o ninja mais velho criado da cena.

Ao teclar J  -> Dobra o tamanho do ninha mais novo criado na cena.

*/
public class exercicio2 extends SimpleApplication {

    public static void main(String[] args) {
        exercicio2 app = new exercicio2();
        app.start();
    }
    protected Geometry player;
    private boolean isRunning = true;
    private boolean criarObjeto = false;
    private boolean pause = false;

    @Override
    public void simpleInitApp() { DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
           
        
        Box box = new Box(1, 1, 5);
        
        Geometry geom = new Geometry("Box", box);
        geom.scale(1f, 0.3f, 1f);

        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap",assetManager.loadTexture("Textures/download.jpg"));
                
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        
        initKeys(); // load my custom keybinding
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        if(!pause){
            List<Spatial> lista = rootNode.getChildren();
            for(Spatial s : lista)
                if(s.getName()=="ninja")
                    s.move(0f,0f,tpf);
            
        }
    }
    
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("Criar",  new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Remover",  new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("RemoverMaisVelho",  new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("DobraTamanho",  new KeyTrigger(KeyInput.KEY_J));
        
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                          new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        // Add the names to the action listener.
        inputManager.addListener(analogListener, "Left", "Right", "Rotate","Criar","Pause", "Remover","RemoverMaisVelho","DobraTamanho");
    }
        
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;
            }
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                
                if (name.equals("Criar")) {
                    Spatial nin = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
                    nin.scale(0.02f);
                    nin.setLocalTranslation(-3f, 0f, 0f);
                    nin.rotate(0,FastMath.PI,0);
                    nin.setName("ninja");
                    rootNode.attachChild(nin);
                }
                if (name.equals("Pause")) {
                    pause = !pause;
                }
                if (name.equals("Remover")) {
                    
                    List<Spatial> lista = rootNode.getChildren();
                    for(Spatial s : lista)
                        if(s.getName()=="ninja"&&lista.size()>1)
                            lista.remove(s);

                }
                
                if (name.equals("RemoverMaisVelho")) {
                    
                    List<Spatial> lista = rootNode.getChildren();
                    
                    if (lista.size()>1){
                        lista.remove(1);
                    }
                }
                
                if (name.equals("DobraTamanho")) {
                    
                    List<Spatial> lista = rootNode.getChildren();
                    if (lista.size()>1){
                        lista.get(lista.size()-1).scale(2f);
                    }
                }
            } else {
                System.out.println("Press P to unpause.");
            }
        }
    };

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
