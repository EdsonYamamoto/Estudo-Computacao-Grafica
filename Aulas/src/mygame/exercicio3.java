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


/*

Criar um projeto que execute as seguintes ações:

OK - Criar um chão/piso para os ninjas usando um cubo. Aplicar uma textura ao cubo.

Ao teclar G ou o Click do Mouse- > Criar um novo ninja em -X para +X. Ao criar um novo ninja esse começa a mover de -X para + X. Se o ninja chegar em ponto específico qualquer da cena, ele deverá ser removido. Marcar esse ponto na cena com um Cubo.

Ao teclar P -> Para todas as animações da aplicação.

Ao teclar N -> Remove todos os ninjas da cena.

Ao teclas H ->  Remove o ninja mais velho criado da cena.

Ao teclar J  -> Dobra o tamanho do ninha mais novo criado na cena.

*/
public class exercicio3 extends SimpleApplication implements AnimEventListener  {
  private AnimChannel channel;
  private AnimControl control;
  private AnimChannel channel2;
  private AnimControl control2;
  Node player1;
  Node player2;
  int vida1 = 10;
  int vida2 = 10;
  

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
        
        control = player1.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Walk");
        
        System.out.println(player2.getControl(AnimControl.class).getAnimationNames());
        
        control2 = player2.getControl(AnimControl.class);
        control2.addListener(this);
        channel2 = control2.createChannel();
        channel2.setAnim("Walk");
        
        
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Walk") && !keyPressed) {
                if (!channel.getAnimationName().equals("Walk")) {
                    channel.setAnim("matar", 0.50f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            }
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                
                if (name.equals("Pause")) {
                    pause = !pause;
                }
                if (name.equals("matarKey")) {
                    matar = !matar;
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
        inputManager.addMapping("matarKey", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "matar");
    }
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
        if (animName.equals("Walk")) {
            channel.setAnim("Attack2", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);

            channel2.setAnim("Attack3", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
        }
        
        if (animName.equals("Attack2")) {
            
            channel.setAnim("Attack2", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
            vida2-=1;
        }
        if (animName.equals("Attack2")) {
            channel2.setAnim("Attack3", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(3f);
            vida1-=1;
        }
        if (vida1==0) {
            channel.setAnim("Death1", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
        if (vida2==0) {
            channel2.setAnim("Death1", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
        }
        if (animName.equals("Death1") && vida1==0) {
            channel2.setAnim("Climb", 0.50f);
            channel2.setLoopMode(LoopMode.DontLoop);
            channel2.setSpeed(1f);
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
