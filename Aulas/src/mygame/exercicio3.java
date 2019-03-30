package mygame;

import com.jme3.animation.AnimEventListener;
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


import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import java.util.List;
import com.jme3.scene.Node;


public class exercicio3 extends SimpleApplication implements AnimEventListener  {
  private AnimChannel channel1;
  private AnimControl control1;
  private AnimChannel channel2;
  private AnimControl control2;
  Node player1;
  Node player2;
  int vida1 = 3;
  int vida2 = 2;
  boolean player1Vivo = true;
  boolean player2Vivo = true;
  boolean endgame = false;
  

    public static void main(String[] args) {
        exercicio3 app = new exercicio3();
        app.start();
    }
    
    private boolean isRunning = true;
    private boolean matar = false;
    private boolean pause = false;

    @Override
    public void simpleInitApp() { 
        
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        initKeys();
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);
        
        player1 = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        player1.setLocalScale(0.03f);
        player1.setLocalTranslation(2f, 0f, 0f);
        player1.rotate(0,FastMath.PI/2,0);
        player1.setName("ninja1");
        
        
        player2 = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        player2.setLocalScale(0.03f);
        player2.setLocalTranslation(-2f, 0f, 0f);
        player2.rotate(0,-FastMath.PI/2,0);
        player1.setName("ninja2");
        
        rootNode.attachChild(player1);
        rootNode.attachChild(player2);
        
        control1 = player1.getControl(AnimControl.class);
        control1.addListener(this);
        channel1 = control1.createChannel();
        channel1.setAnim("Walk");
        
        System.out.println(player2.getControl(AnimControl.class).getAnimationNames());
        
        control2 = player2.getControl(AnimControl.class);
        control2.addListener(this);
        channel2 = control2.createChannel();
        channel2.setAnim("Walk");
        
        initKeys();
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                if (name.equals("Reiniciar")){
            channel1.setAnim("Walk", 0.50f);
            channel1.setLoopMode(LoopMode.DontLoop);
            channel1.setSpeed(1f);

            channel2.setAnim("Walk", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
  vida1 = 3;
  vida2 = 2;
  player1Vivo = true;
  player2Vivo = true;
  endgame = false;
                }
                if (name.equals("Criar")) {
                    Spatial nin = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
                    nin.scale(0.02f);
                    nin.setLocalTranslation(-3f, 0f, 0f);
                    nin.rotate(0,FastMath.PI,0);
                    nin.setName("ninja");
                    rootNode.attachChild(nin);
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

    /** Custom Keybinding: Map named actions to inputs. */
    private void initKeys() {
        inputManager.addMapping("Criar",  new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Reiniciar", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(analogListener,"Criar", "Reiniciar");
    }
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
        if (animName.equals("Walk")) {
            channel1.setAnim("Attack2", 0.50f);
            channel1.setLoopMode(LoopMode.DontLoop);
            channel1.setSpeed(1f);

            channel2.setAnim("Attack3", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
        }
        
        if (animName.equals("Attack2")) {
            
            channel1.setAnim("Attack2", 0.50f);
            channel1.setLoopMode(LoopMode.DontLoop);
            channel1.setSpeed(1f);
            vida2-=1;
        }
        if (animName.equals("Attack2")) {
            channel2.setAnim("Attack3", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(3f);
            vida1-=1;
        }
        if (vida1==0 && player1Vivo) {
            channel1.setAnim("Death1", 0.50f);
            channel1.setLoopMode(LoopMode.DontLoop);
            channel1.setSpeed(1f);
            player1Vivo=!player1Vivo;
        }
        if (vida2==0 && player2Vivo) {
            channel2.setAnim("Death1", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
            player2Vivo=!player2Vivo;
        }
        if (vida1!=0 && !player2Vivo) {
            channel1.setAnim("HighJump", 0.50f);
            channel1.setLoopMode(LoopMode.DontLoop);
            channel1.setSpeed(1f);
            endgame = true;
        }
        if (vida2!=0 && !player1Vivo) {
            channel2.setAnim("Jump", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
            endgame = true;
        }
        
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        //nao usado
        /*
        if (animName.equals("Attack2")) {
            channel.setAnim("Attack2", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
        */
    }
}
