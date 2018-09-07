package com.game.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractGameObject extends SimpleAbstractGameObject
{
    // Non-Box2D Physics
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;

    public Vector2 acceleration;
    public Rectangle bounds;

    // Box2D Physics
    public Body body;

    // Animation
    public float stateTime;
    public Animation animation;

    public AbstractGameObject()
    {
	velocity = new Vector2();
	terminalVelocity = new Vector2(1, 1);
	friction = new Vector2();
	acceleration = new Vector2();
	bounds = new Rectangle();
    }

    protected void updateMotionX(float deltaTime)
    {
	if (velocity.x != 0)
	{
	    if (velocity.x > 0)
	    {
		velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
	    }
	    else
	    {
		velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
	    }
	}

	velocity.x += acceleration.x * deltaTime;
	velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
    }

    protected void updateMotionY(float deltaTime)
    {
	if (velocity.y != 0)
	{
	    if (velocity.y > 0)
	    {
		velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
	    }
	    else
	    {
		velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
	    }
	}

	velocity.y += acceleration.y * deltaTime;
	velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
    }

    // TODO: Should this method be made final so we can't override it?
    public void update(float deltaTime)
    {
	stateTime += deltaTime;
	if (body == null)
	{
	    updateMotionX(deltaTime);
	    updateMotionY(deltaTime);

	    m_position.x += velocity.x * deltaTime;
	    m_position.y += velocity.y * deltaTime;
	}
	else
	{
	    m_position.set(body.getPosition());
	    rotation = body.getAngle() * MathUtils.radiansToDegrees;
	}
    }

    public void setAnimation(Animation anim)
    {
	animation = anim;
	stateTime = 0;
    }
}
