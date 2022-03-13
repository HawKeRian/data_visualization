class Scrollbar {
  int swidth, sheight;    // width and height of bar
  float xpos, ypos;       // x and y position of bar
  float spos, newspos;    // x position of slider
  float sposMin, sposMax; // max and min values of slider
  int loose;              // how loose/heavy
  boolean over;           // is the mouse over the slider?
  boolean locked;
  float ratio;

  Scrollbar (float xp, float yp, int sw, int sh) {
    swidth = sw;
    sheight = sh;
    int widthtoheight = sw - sh;
    ratio = (float)sw / (float)widthtoheight;
    xpos = xp;
    ypos = yp-sheight/2;
    spos = xpos;
    newspos = spos;
    sposMin = xpos;
    sposMax = xpos + swidth - sheight;
  }

  void update() {
    if(showInteractButton){
      if (overEvent()) {
        over = true;
      } else {
        over = false;
      }
      if (mousePressed && over) {
        locked = true;
      }
      if (!mousePressed) {
        locked = false;
      }
      if (locked) {
        newspos = constrain(mouseX-sheight/2, sposMin, sposMax);
      }
      if (abs(newspos - spos) > 1) {
        spos = spos + (newspos-spos);
      }
    }
  }

  float constrain(float val, float minv, float maxv) {
    return min(max(val, minv), maxv);
  }

  boolean overEvent() {
    if (mouseX > xpos && mouseX < xpos+swidth &&
       mouseY > ypos && mouseY < ypos+sheight) {
      return true;
    } else {
      return false;
    }
  }

  void display() {
    if(times.minute > 11){
      spos = xpos + (times.minute-12)*17.5;
    }else{
      spos = xpos + times.minute*17.5;
    }
    if(showInteractButton){
      pushMatrix();
      for(int i=0; i<12; i++){
        stroke(0,100);
        line(30+i*17.5,ypos-7.5,30+i*17.5,ypos+7.5);
      }
      
      noStroke();
      fill(204);
      rectMode(CENTER);
      rect(swidth/2+20, ypos, swidth, sheight/2);
      if (over || locked) {
        fill(0, 0, 0);
      } else {
        fill(75, 75, 75);
      }
      rect(spos, ypos, sheight, sheight);
      popMatrix();
    }
  }

  float getPos() {
    // Convert spos to be values between
    // 0 and the total width of the scrollbar
    return spos * ratio;
  }
  
  void checkHourSlider(){
    for(int i=0; i<12; i++){
      if(spos-sheight/2 < 30+i*17.5 && spos+sheight/2 >30+i*17.5){
      }
    }
  }
}
