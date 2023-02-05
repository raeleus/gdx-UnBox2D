package dev.lyze.gdxUnBox2d;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import lombok.Getter;

public abstract class Behaviour {
    @Getter private final GameObject gameObject;

    boolean started;

    public Behaviour(GameObject gameObject) {
        this.gameObject = gameObject;

        gameObject.getUnBox().addBehaviour(this);
    }

    /**
     * This function is always called right after adding a behaviour to a game object in the same frame even when the game object is disabled.
     */
    public abstract void awake();

    /**
     * This function is called before the first frame update when the script is enabled.
     */
    public abstract void start();

    /**
     * This method runs at a fixed delta time unrelated to framerate, therefore it can be executed multiple times a frame if needed.
     * All physics calculations and other behaviour update methods occur immediately after fixedUpdate.
     */
    public abstract void fixedUpdate();

    /**
     * Update is called once a frame.
     * @param delta The delta time from last to current frame. {@link Graphics#getDeltaTime()}
     */
    public abstract void update(float delta);

    /**
     * This method is called once per frame after {@link Behaviour#update(float)} is finished.
     * For example, you can use this to adjust the cameras position after a players position changed.
     * @param delta The delta time from last to current frame. {@link Graphics#getDeltaTime()}
     */
    public abstract void lateUpdate(float delta);

    public abstract boolean onCollisionPreSolve(GameObject b, Contact contact, Manifold oldManifold);

    public abstract boolean onCollisionPostSolve(GameObject b, Contact contact, ContactImpulse impulse);

    /**
     * This method gets called when a Box2D collision happens with this object.
     * @param other The other object this object collides with.
     * @param contact Infos about the collision.
     */
    public abstract void onCollisionEnter(GameObject other, Contact contact);

    /**
     * This method gets continuously called once per fixed update when a collision still occurs.
     * @param other The other object this object collides with.
     */
    public abstract void onCollisionStay(GameObject other);

    /**
     * This method gets called when a Box2D collision stops with this object.
     * @param other The other object this object stopped colliding with.
     * @param contact Infos about the collision.
     */
    public abstract void onCollisionExit(GameObject other, Contact contact);

    /**
     * A method which draws the behaviour onto the screen once a frame.
     * @param batch The batch which is used to draw the behaviour.
     */
    public abstract void render(Batch batch);

    /**
     * A method which uses a {@link ShapeRenderer} to draw the bavhiour to the screen once a frame.
     * Therefore, most commonly used to draw debug infos.
     * @param render The shape renderer which is used to draw the bavhiour.
     */
    public abstract void debugRender(ShapeRenderer render);

    /**
     * When a game object gets enabled with {@link GameObject#setEnabled(boolean)} this method gets immediately called.
     */
    public abstract void onEnable();

    /**
     * This method gets called in three ways:
     * <ul>
     *     <li>When a game object gets disabled with {@link GameObject#setEnabled(boolean)} this method gets immediately called.</li>
     *     <li>When a game object gets destroyed and the game object is enabled.</li>
     *     <li>When a game object is enabled and this behaviour got removed from that game object.</li>
     * </ul>
     */
    public abstract void onDisable();

    /**
     * This function is called when the behaviour gets cleaned up in {@link UnBox#postRender()}.
     * For example if you {@link UnBox#destroy(GameObject)} the game object or the behaviour itself.
     */
    public abstract void onDestroy();
}