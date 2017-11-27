package seanjkim_klu1;
import robocode.*;
import java.awt.Color;
import robocode.util.Utils;




/**
 * MoltenCore - a robot by Sean Kim, Kevin Lu
 */
public class MoltenCore extends Robot
{
    public void run() 
    {

        setColors(Color.red,Color.red,Color.red); // we like red
        // Robot main loop
        while(true) 
        {
            double height = getBattleFieldHeight();
            double width = getBattleFieldWidth();
            double xCord = getX();
            double yCord = getY();
            double buffer = 75;
            double leftCollisionWall = buffer;
            double rightCollisionWall = width-buffer;
            double upperCollisionWall = height-buffer;
            double lowerCollisionWall = buffer;
            while (xCord <= leftCollisionWall || xCord >= rightCollisionWall || yCord >= upperCollisionWall || yCord <= lowerCollisionWall)
            {
                avoidWall();
                xCord = getX();
                yCord = getY();
            }
            turnGunRight(20);
        }
    }
    public void onScannedRobot(ScannedRobotEvent e) 
    {

        double distance = e.getDistance();
        double enemyDegree = e.getBearing();
        double myDegree = getRadarHeading();
        boolean firstQuad = false;
        boolean secondQuad = false;
        boolean thirdQuad = false;
        boolean fourthQuad = false;
        boolean eFirstQuad = false;
        boolean eSecondQuad = false;
        boolean eThirdQuad = false;
        boolean eFourthQuad = false;
        boolean dodge = false;
        
        if (myDegree > 0 && myDegree <90)
            firstQuad = true;
        if (myDegree > 90 && myDegree <180)
            secondQuad = true;
        if (myDegree > 180 && myDegree <270)
            thirdQuad = true;
        if (myDegree > 270 && myDegree <360)
            fourthQuad = true;
            
        if (enemyDegree > 0 && enemyDegree <90)
            eFirstQuad = true;
        if (enemyDegree > 90 && enemyDegree <180)
            eSecondQuad = true;
        if (enemyDegree > 180 && enemyDegree <270)
            eThirdQuad = true;
        if (enemyDegree > 270 && enemyDegree <360)
            eFourthQuad = true;
            
        if (firstQuad == true && eThirdQuad == true)
            dodge = true;
        if (secondQuad == true && eFourthQuad == true)
            dodge = true;
        if (thirdQuad == true && eFirstQuad == true)
            dodge = true;
        if (fourthQuad == true && eSecondQuad == true)
            dodge = true;
           
        double xCord;
        double yCord;
        double height;
        double width;
        
        if (dodge == true)
        {          
            xCord = getX();
            yCord = getY();
            height = getBattleFieldHeight();
            width = getBattleFieldWidth();
            if (xCord > width/2 && yCord > height/2)
            {
                turnLeft(270 - myDegree);
                ahead(100);
            }
            if (xCord < width/2 && yCord > height/2)
            {
                turnRight(180 - myDegree);
                ahead(100);
            }
            if (xCord < width/2 && yCord < height/2)
            {
                turnLeft(90 - myDegree);
                ahead(100);
            }
            if (xCord > width/2 && yCord < height/2)
            {
                turnRight(360 - myDegree);
                ahead(100);
            } 
              
        }

        double gunSwing = Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getGunHeading());                                          
        if (dodge != true)
        {
                turnGunRight(gunSwing);
                if (distance > 200)
                 {
                    fire(1);
                 }
                if (distance > 100 && distance < 200)
                {
                    fire(2);
                }
                else
                {
                    fire(3);
                }

                    scan();        
        }
            
    }
        
      

     
    public void onHitByBullet(HitByBulletEvent e) 
    {
        back(100);
    }
    
    public void avoidWall()
    {
            setAdjustGunForRobotTurn(true);
        turnGunRight(360);
            double height = getBattleFieldHeight();
            double width = getBattleFieldWidth();
            int move = 90;
            double xCord = getX();
            double yCord = getY();
            double buffer = 100;
            double leftCollisionWall = buffer;
            double rightCollisionWall = width-buffer;
            double upperCollisionWall = height-buffer;
            double lowerCollisionWall = buffer;
            double bodyDegrees = getHeading();
            
            if(xCord >= rightCollisionWall && yCord >= upperCollisionWall)
            {
                bodyDegrees = getHeading();
                stop();
                resume();
                
                if(bodyDegrees >= 0 && bodyDegrees <= 90)
                    {
                    turnRight(180);
                    ahead(move);
                    }
                if(bodyDegrees >= 90 && bodyDegrees <= 180)
                    {
                    turnRight(90);
                    ahead(move);
                    }
                if(bodyDegrees >= 270 && bodyDegrees <= 360)
                    {
                    turnLeft(90);
                    ahead(move);
                    }
               xCord = getX();
               yCord = getY();
            }
            if(xCord <= leftCollisionWall && yCord >= upperCollisionWall)
            {
                bodyDegrees = getHeading();
                stop();
                resume();
                
                if(bodyDegrees >= 270 && bodyDegrees <= 360)
                    {
                    turnRight(180);
                    ahead(move);
                    }
                if(bodyDegrees >= 0 && bodyDegrees <= 90)
                    {
                    turnRight(90);
                    ahead(move);
                    }
                if(bodyDegrees >= 180 && bodyDegrees <= 270)
                    {
                    turnLeft(90);
                    ahead(move);
                    }
               xCord = getX();
               yCord = getY();
            }
           if(xCord <= leftCollisionWall && yCord <= lowerCollisionWall)
            {
                 bodyDegrees = getHeading();
                 stop();
                 resume();
               
                if(bodyDegrees >= 270 && bodyDegrees <= 360)
                    {
                    turnRight(90);
                    ahead(move);
                    }
                if(bodyDegrees >= 90 && bodyDegrees <= 180)
                    {
                    turnLeft(90);
                    ahead(move);
                    }
                if(bodyDegrees >= 180 && bodyDegrees <= 270)
                    {
                    turnRight(180);
                    ahead(move);
                    }
               xCord = getX();
               yCord = getY();
               }
           if(xCord >= rightCollisionWall && yCord <= lowerCollisionWall)
               {
                bodyDegrees = getHeading();
                stop();
                resume();
                
                if(bodyDegrees >= 90 && bodyDegrees <=180)
                    {
                    turnRight(180);
                    ahead(move);
                    }
                if(bodyDegrees >= 180 && bodyDegrees <= 270)
                    {
                    turnRight(90);
                    ahead(move);
                    }
                if(bodyDegrees >= 0 && bodyDegrees <= 90)
                    {
                    turnLeft(90);
                    ahead(move);
                    }
               xCord = getX();
               yCord = getY();  
               }
           if(xCord <= leftCollisionWall)
           {
               bodyDegrees = getHeading();
               stop();
               resume();
               
              if(bodyDegrees >= 180 && bodyDegrees <= 270)
              {
              turnLeft(90);
              ahead(move);
              }
              if(bodyDegrees >= 270 && bodyDegrees <= 360)
              {
              turnRight(90);
              ahead(move);
              } 
              xCord = getX();
              yCord = getY();
            }
            if(xCord >= rightCollisionWall)
            {
                bodyDegrees = getHeading();
                stop();
                resume();
                
                if(bodyDegrees >= 0 && bodyDegrees <= 90)
                {
                turnLeft(90);
                ahead(move);
                }
                if(bodyDegrees >= 90 && bodyDegrees <= 180)
                {
                turnRight(90);
                ahead(move);
                }
               xCord = getX();
               yCord = getY();
            }
            if(yCord >= upperCollisionWall)
            {
                bodyDegrees = getHeading();
                stop();
                resume();
                
                if(bodyDegrees >= 0 && bodyDegrees <= 90)
                {
                turnRight(90);
                ahead(move);
                }
                if(bodyDegrees >= 270 && bodyDegrees <= 360)
                {
                turnLeft(90);
                ahead(move);
                }
                turnRight(180);
                ahead(move);
               xCord = getX();
               yCord = getY();
            }
            if(yCord <= lowerCollisionWall)
            {
                bodyDegrees = getHeading();
                stop();
                resume();
                
                if(bodyDegrees >= 180 && bodyDegrees <= 270)
                {
                turnRight(90);
                ahead(move);
                }
                if(bodyDegrees >= 90 && bodyDegrees <= 180)
                {
                turnLeft(90);
                ahead(move);
                }
            }
    }

}
