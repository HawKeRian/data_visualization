class Graph{
  int time;
  int position;
  int timeCounter;
  int barHeight;
  int[] data;
  color graphColor1, graphColor2;
  
  Graph(int time_, String[] data_, int position_){
    time = time_;
    position = position_;
    timeCounter = 0;
    barHeight =0;
    data = int(data_);
    graphColor1 = color(150,0);
    graphColor2 = color(150,0);
  }
  
  void graphSetup(){
    ////background
    fill(255,80);
    noStroke();
    rectMode(LEFT);
    rect(10,height/1.02,width-10,height/1.322);
    
    fill(graphColor1);
    rectMode(CORNERS);
    rect(150,height/1.0422,450,height/1.25);
    
    if(!expandGraphButton){
      stroke(0);
      strokeWeight(3);
      line(150,height/1.0422,450,height/1.0422);
      line(150,height/1.0422,150,height/1.25);
    }
    
    
    if(times.minute >= 12){
      noStroke();
      fill(graphColor2);
      rectMode(CORNERS);
      rect(470,height/1.0422,770,height/1.25);
      
      if(!expandGraphButton){
        stroke(0);
        strokeWeight(3);
        line(470,height/1.0422,770,height/1.0422);
        line(470,height/1.0422,470,height/1.25);
      }
      
    }
    
  }
  void graphCounter(){
      timeCounter=0;
      if(times.minute == position){
        for(int j = 0; j < data.length; j++){
          if(int(data[j]) ==position){
            timeCounter++;
          }
        }
      }   
      barHeight = timeCounter;
  }

}
