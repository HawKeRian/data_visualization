class Interact{
  float mapLeft = width/50; //200
  float mapRight = 250;
  float mapTop = height/7.65; //125.4901
  float mapBottom = height/3; //320
  float oPointX, oPointY;
  float dPointX, dPointY;
  float minimapWidth, minimapHeight;
  
  Interact(){
    if(mapScreenWidth/mapScreenHeight > 1.5){
      mapRight = 250;
    }
    minimapWidth = (mapRight*0.95)+20;
    minimapHeight = mapTop*1.5;
  }
  void interactionInit(){
    fill(255,80);
    noStroke();
    rectMode(LEFT);
    rect(10,height/8.35,mapRight,height/1.35);
  }
  
  void miniMap(){
    
    pushMatrix();
      if(mapScreenWidth/mapScreenHeight < 1.5){
        beginShape();
        texture(backgroundMap);
        vertex(mapLeft,mapTop,0,0);
        vertex(mapRight*0.95,mapTop,851,0);
        vertex(mapRight*0.95,mapBottom,851,1058);
        vertex(mapLeft,mapBottom,0,1058);
        endShape();
      }else{
        mapRight = 250;
        mapLeft = 20;
        mapTop = 125.4901;
        mapBottom = 320;
        beginShape();
        texture(backgroundMap);
        vertex(mapLeft,mapTop,0,0);
        vertex(mapRight*0.95,mapTop,851,0);
        vertex(mapRight*0.95,mapBottom,851,1058);
        vertex(mapLeft,mapBottom,0,1058);
        endShape();
      }
      
      strokeWeight(3);
      stroke(255);
      noFill();
      rectMode(CORNER);
      rect(mapLeft, mapTop, mapRight*0.875, mapBottom-mapTop);
    popMatrix();
  }
  
  void originDisplay(String[] s){
    oPointX = minimapWidth*(float(s[1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    oPointY = mapBottom-minimapHeight*(float(s[0])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    
    if(pickupButton){
      pushMatrix();
        noStroke();
        fill(255,0,0,150);
        ellipseMode(CENTER);
        ellipse(oPointX,oPointY,5,5);
      popMatrix();
    }
  }
  
  void destinationDisplay(String[] s){
    dPointX = minimapWidth*(float(s[s.length-1])-mapGeoLeft)/(mapGeoRight-mapGeoLeft);
    dPointY = mapBottom-minimapHeight*(float(s[s.length-2])-mapGeoBottom)/(mapGeoTop-mapGeoBottom);
    
    if(dropoffButton){
      pushMatrix();
        noStroke();
        fill(255,150);
        ellipseMode(CENTER);
        ellipse(dPointX,dPointY,5,5);
      popMatrix();
    }
  }
  
  void presentDate(){
    if(showInteractButton){
      for(int i=0; i<selectDateColor.length; i++){
        if(mapScreenWidth/mapScreenHeight < 1.5){
          if((mouseX > 27.5+(i*30) && mouseX < 52.5+(i*30)) && (mouseY > (height/2.2)-12.5) && mouseY < (height/2.2)+12.5){
            selectDateColor[i] = color(255,0,0,150);
            if(mousePressed && mouseButton == LEFT){
              selectDateButton[i] = true;
              selectDateColor[i] = color(255);
              times.start_date = i;
              times.date =15+i;
              times.continueDate();
            }
          }else{
            if(times.start_date == i){
              selectDateColor[i] = color(255);
            }else{
              selectDateColor[i] = color(175,150);
            }
          }
        }else{
          if((mouseX > 27.5+(i*30) && mouseX < 52.5+(i*30)) && (mouseY > (436.363636)-12.5) && mouseY < (436.363636)+12.5){
            selectDateColor[i] = color(255,0,0,150);
            if(mousePressed && mouseButton == LEFT){
              selectDateButton[i] = true;
              selectDateColor[i] = color(255);
              times.start_date = i;
              times.date =15+i;
              times.continueDate();
            }
          }else{
            if(times.start_date == i){
              selectDateColor[i] = color(255);
            }else{
              selectDateColor[i] = color(175,150);
            }
          }
        }
      }
    }
  }
  
}
