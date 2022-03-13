class TripRun{
  float x,y;
  float x1,y1;
  int index;
  float[] trackX,trackY;
  float[] trailX, trailY;
  float speedX,speedY;
  float mapWidth, mapHeight;
  int time;
  float oPointX, dPointX, oPointY, dPointY;
  TripRun(String time_){
    speedX = 0;
    speedY = 0;
    index = 0;
    trackX = new float[20];
    trackY = new float[20];
    trailX = new float[200];
    trailY = new float[200];
    time = int(time_);
    
  }
  void initMap(){
    if(mapScreenWidth/mapScreenHeight > 1.5){
      mapWidth = mapScreenWidth*0.543077;
    }else{
      mapWidth = mapScreenWidth;
    }
    mapHeight = mapScreenHeight;
  }
  
  void initTrip(String[] s){
    if(index<tripRunWaypoint.length-3){
      x = mapWidth*(float(s[index+1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
      y = mapHeight - mapHeight*(float(s[index])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
      x1 = mapWidth*(float(s[index+3])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
      y1 = mapHeight - mapHeight*(float(s[index+2])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    }
    oPointX = mapWidth*(float(s[1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    oPointY = mapHeight - mapHeight*(float(s[0])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    dPointX = mapWidth*(float(s[s.length-1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    dPointY = mapHeight - mapHeight*(float(s[s.length-2])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    
  }
  
  void display(){
    float rad = 4;
    float freq = 30;
    rotateX(240*TWO_PI/height);
    trackX[trackX.length-1] = -(mapWidth/2-x);
    trackY[trackY.length-1] = -(height/2-y);
    for(int i=0; i<trackX.length-1; i++){
      trackX[i] = trackX[i+1];
      trackY[i] = trackY[i+1];
    }
    
    trailX[trailX.length-1] = -(mapWidth/2-x);
    trailY[trailY.length-1] = -(height/2-y);
    for(int i=0; i<trailX.length-1; i++){
      trailX[i] = trailX[i+1];
      trailY[i] = trailY[i+1];
    }
    if(x == dPointX && y == dPointY){
      freq += 20;
    }
    pushMatrix();
      translate(trackX[trackX.length-1],trackY[trackY.length-1],5);
      noStroke();
      fill(255,0,0,150);
      sphere(3);
    popMatrix();
    
    translate(0,0,5);
    rotateX((10+TWO_PI)/height);
    for(int i=1; i<trackX.length; i++){
      fill(statistics[times.start_date].lineColor[times.start_date]);
      noStroke();
      ellipse(trackX[i],trackY[i],rad/2,rad/2);
    }
    for(int i=1; i<trailX.length; i++){
        fill(255,255,0,freq);
        noStroke();
        ellipse(trailX[i],trailY[i],rad/2,rad/2);
    }
    
    pushMatrix();
      translate(-(mapWidth/2-dPointX),-(height/2-dPointY));
      noStroke();
      fill(255,150);
      sphere(3);
    popMatrix();
  }

  void update(){
    x = x+speedX;
    y = y+speedY;
  }
  
  void directionTrip(){
    directionX();
    display();
    directionY();
    if(speedX==0 && speedY==0){
      index+=2;
    }
    if(index>tripRunWaypoint.length-3){
      index =0;
    }
  }
  
  void directionX(){
    if(!timePauseButton){
      if(abs(x1-x)>0.7 && speedY==0){
        if(x<x1){
          speedX += 1;
        }else if(x>x1){
          speedX -= 1;
        }
      }else{
        speedX = 0;
        x = x1;
      }
    }
  }
  
  void directionY(){
    if(!timePauseButton){
      if(abs(y1-y)>0.7 && speedX==0){
        if(y<y1){
          speedY += 1;
        }else if(y>y1){
          speedY -= 1;
        }
      }else{
        speedY = 0;
        y = y1;
      }
    }
  }
  
}
