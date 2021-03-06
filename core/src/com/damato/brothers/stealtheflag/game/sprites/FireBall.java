package com.damato.brothers.stealtheflag.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.damato.brothers.stealtheflag.game.GameMain;

public class FireBall extends Sprite {
    public World world;
    public Body b2body;

    private float stateTimer;
    private float lifeShot;
    private float timeShot;
    private boolean destroy;
    private boolean setToDestroy;
    private boolean isHit;
    private boolean shot;
    private Player player;

    public FireBall(Player player){
    	this.player = player;
        world = player.getPlayerWorld();
        stateTimer = 0;
        lifeShot = player.getArm().timeLife;  //lifeShot = 0.5f;
        timeShot = 0;
        destroy = false;
        setToDestroy = false;
        //isHit e shot talvez não sejam utilizados (BR-F)
        isHit = false;
        shot = false;

        float velocityBall = 10*lifeShot;
        if (player.getDirectionR()){
            defineFireBall(((player.getX() +player.getWidth()*1.2f))*GameMain.PPM,
                    (player.getY()+player.getHeight()*0.7f)*GameMain.PPM, player.getArm().dimension);
            b2body.applyLinearImpulse(new Vector2(player.getArm().speed,0),b2body.getWorldCenter(),false);
        }else{
            defineFireBall(((player.getX()-player.getWidth()*0.2f))*GameMain.PPM,
                    (player.getY()+player.getHeight()*0.7f)*GameMain.PPM, player.getArm().dimension);
            b2body.applyLinearImpulse(new Vector2(-player.getArm().speed,0),b2body.getWorldCenter(),false);
        }

        setBounds(0,0,8/ GameMain.PPM, 8/GameMain.PPM);
    }
    public void update(float dt){
        timeShot +=dt;
        setPosition(b2body.getPosition().x- getWidth() / 2,b2body.getPosition().y- getHeight() / 2);
        getRegion(dt);
        if((timeShot >= lifeShot || setToDestroy) && !destroy) {
            world.destroyBody(b2body);
            destroy = true;
        }
    }
    public void dispose(){

    }

    public void defineFireBall(float x, float y, int dimension){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/ GameMain.PPM,y/ GameMain.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fixdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(dimension / 2 / GameMain.PPM, dimension / 2 / GameMain.PPM);
        fixdef.filter.categoryBits = GameMain.FIREBALL_BIT;
        fixdef.filter.maskBits = GameMain.GROUND_BIT | GameMain.PLAYER_BIT | GameMain.WALL_BIT;
        fixdef.shape = shape;
        fixdef.isSensor = true;
        b2body.setGravityScale(0);
        b2body.createFixture(fixdef).setUserData(this);

    }
    public TextureRegion getRegion(float dt){
        //used state here
        //invertendo textura
       /* if (b2body.getLinearVelocity().x < 0 && !region.isFlipX()){
            region.flip(true,false);
        }else if (region.isFlipX()){
            region.flip(true,false);
        }*/
        /*se statetimet = current timer e for igual a previousState
        faça stateTimer + dt, caso não todos ficam iguais a zero*/
        return null;
    }

    public boolean isDestroy(){
        return destroy;
    }
    public float getStateTimer(){
        return  stateTimer;
    }
    public void setToDestroyed(boolean destroyed){
       setToDestroy = destroyed;
    }
    
    public Player getPlayer() {
		return player;
	}
}
